package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import fr.univ_lyon1.info.m1.stopcovid_simulator.util.Destroyable;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.CautionLevel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.events.Event;

import java.util.HashMap;

public class ClientManager implements Destroyable {
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
            clientInContact.onStatusChange().remove(this::handleContactStatusChange);
        }
        state.onStatusChange().remove(this::handleStatusChange);
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
    public Event.With1Params<Status> onStatusChange() {
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
     */
    public void addContact(final ClientState clientInContact, final int contactValue) {
        if (contacts.containsKey(clientInContact)) {
            var contact = contacts.get(clientInContact);
            contact.addContact(contactValue);
        } else {
            var contact = new Contact(() -> finishContactLife(clientInContact));
            contact.addContact(contactValue);
            clientInContact.onStatusChange().add(this::handleContactStatusChange);
            contacts.put(clientInContact, contact);
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

    /**
     * Handler of `contacts status change`.
     * Simple redirection to update status method.
     *
     * @param discard Discarded parameter.
     */
    private void handleContactStatusChange(final Status discard) {
        updateStatus();
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
     */
    private void finishContactLife(final ClientState clientInContact) {
        try {
            Thread.sleep(SimulatedTime.FORTNIGHTLY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        clientInContact.onStatusChange().remove(this::handleContactStatusChange);
        contacts.get(clientInContact).destroy();
        contacts.remove(clientInContact);
        updateStatus();
    }
    //endregion : Coroutine
}
