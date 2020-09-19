package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import com.github.hervian.reflection.Event;
import com.github.hervian.reflection.Fun;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.Destroyable;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.CautionLevel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;

import java.util.HashMap;

public class ClientManager implements Destroyable {
    private static class Contact implements Destroyable {
        private int contactSent;
        private int contactReceived;
        private Runnable contactLifeTimerHandler;
        private Thread contactLifeTimer;

        private final Fun.With1ParamAndVoid<Status> statusChangeHandler;

        /**
         * Constructor.
         *
         * @param statusChangeHandler The status change handler of the client in contact.
         */
        Contact(final Fun.With1ParamAndVoid<Status> statusChangeHandler) {
            contactSent = 0;
            contactReceived = 0;

            this.statusChangeHandler = statusChangeHandler;
        }

        /**
         * Destructor.
         */
        @Override
        public void destroy() {
            if (contactLifeTimer != null) {
                contactLifeTimer.interrupt();
            }
        }

        /**
         * @return the total contacts value.
         */
        public int getValue() {
            return contactSent + contactReceived;
        }

        public void load(final Runnable contactLifeTimerHandler) {
            this.contactLifeTimerHandler = contactLifeTimerHandler;
            contactLifeTimer = new Thread(contactLifeTimerHandler);
            contactLifeTimer.start();
        }

        public void reload() {
            if (contactLifeTimer != null) {
                contactLifeTimer.interrupt();
            }
            contactLifeTimer = new Thread(contactLifeTimerHandler);
            contactLifeTimer.start();
        }
    }

    private final ClientState state;
    private CautionLevel cautionLevel;
    private final HashMap<ClientState, Contact> contacts;
    private Thread infectionLifeTimer;

    //region : Initialization

    /**
     * Constructor.
     *
     * @param state        The state of the client to manage.
     * @param cautionLevel The caution level of management.
     */
    public ClientManager(final ClientState state, final CautionLevel cautionLevel) {
        this.state = new ClientState(state);
        this.cautionLevel = cautionLevel;
        contacts = new HashMap<>();

        state.onStatusChange().add(this::handleStatusChange);
    }
    //endregion : Initialization

    //region : Ending

    /**
     * Destructor.
     */
    @Override
    public void destroy() {
        if (infectionLifeTimer != null) {
            infectionLifeTimer.interrupt();
        }
        for (var contactPair : contacts.entrySet()) {
            var clientInContact = contactPair.getKey();
            var contact = contactPair.getValue();

            contact.destroy();
            clientInContact.onStatusChange().remove(contact.statusChangeHandler);
        }
    }
    //endregion : Ending

    //region : Getters & Setters

    /**
     * @return the `state` of the client to manage.
     */
    public ClientState getState() {
        return state;
    }


    /**
     * @return `this state status change event`.
     */
    public Event.With1ParamAndVoid<Status> onStatusChange() {
        return state.onStatusChange();
    }

    /**
     * Set the `state id` with `id`.
     *
     * @param id The id to set.
     */
    public void setId(final String id) {
        state.setId(id);
    }

    /**
     * Set the `state status` with `status`.
     *
     * @param status The status to set.
     */
    public void setStatus(final Status status) {
        state.setStatus(status);
    }

    /**
     * Set `this caution level` with `caution level`.
     *
     * @param cautionLevel The caution level to set.
     */
    public void setCautionLevel(final CautionLevel cautionLevel) {
        this.cautionLevel = cautionLevel;
        updateStatus();
    }
    //endregion : Getters & Setters

    //region : Action

    /**
     * Add a `client in contact` to the client to manage.
     *
     * @param clientInContact The state of the client in contact.
     * @param contactValue    The number of times they contacted.
     * @param isSent          The client to manage is the one who sent the contact.
     */
    public void addContact(final ClientState clientInContact, final int contactValue,
                           final boolean isSent) {
        if (contacts.containsKey(clientInContact)) {
            var contact = contacts.get(clientInContact);
            if (isSent) {
                contact.contactSent = contactValue;
            } else {
                contact.contactReceived = contactValue;
            }
            contact.reload();
        } else {
            var contact = new Contact(discard -> updateStatus());
            if (isSent) {
                contact.contactSent = contactValue;
            } else {
                contact.contactReceived = contactValue;
            }
            clientInContact.onStatusChange().add(contact.statusChangeHandler);
            contacts.put(clientInContact, contact);
            contact.load(() -> finishContactLife(clientInContact, contact));
        }

        updateStatus();
    }

    //region : Action.sub-methods

    /**
     * Updates the `state status` according to those of the `contacts`.
     */
    private void updateStatus() {
        var status = state.getStatus();
        if (status == Status.INFECTED) {
            return;
        }

        var newStatus = evaluateStatus();
        if (status != newStatus) {
            state.setStatus(newStatus);
        }
    }

    /**
     * @return the evaluated status according to those of the `contacts`.
     */
    private Status evaluateStatus() {
        var totalRiskIndicator = 0f;
        for (var clientInContact : contacts.keySet()) {
            var riskIndicator = clientInContact.getStatus().getRisk();
            var contactValue = contacts.get(clientInContact).getValue();
            totalRiskIndicator += riskIndicator * contactValue;
        }

        if (totalRiskIndicator < cautionLevel.getTolerance()) {
            return Status.NO_RISK;
        } else {
            return Status.RISKY;
        }
    }
    //endregion : Action.sub-methods
    //endregion : Action

    //region : Event handler

    /**
     * Handler of `state status change`.
     * Starts the infection lifetime coroutine (in case of infection).
     *
     * @param status The changed status.
     */
    private void handleStatusChange(final Status status) {
        if (status == Status.INFECTED) {
            infectionLifeTimer = new Thread(this::finishInfectionLife);
            infectionLifeTimer.start();
        }
    }
    //endregion : Event handler

    //region : Coroutine

    /**
     * Infection lifetime coroutine.
     */
    private void finishInfectionLife() {
        try {
            Thread.sleep(SimulatedTime.FORTNIGHTLY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        state.setStatus(Status.NO_RISK);
    }

    /**
     * Contact lifetime coroutine.
     *
     * @param clientInContact The client in contact to remove.
     * @param contact         The contact to remove.
     */
    private void finishContactLife(final ClientState clientInContact, final Contact contact) {
        try {
            Thread.sleep(SimulatedTime.FORTNIGHTLY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        clientInContact.onStatusChange().remove(contact.statusChangeHandler);
        contacts.remove(clientInContact, contact);
        updateStatus();
    }
    //endregion : Coroutine
}
