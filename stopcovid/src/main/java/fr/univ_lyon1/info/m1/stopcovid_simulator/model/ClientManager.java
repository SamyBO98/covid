package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import com.github.hervian.reflection.Event;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.CautionLevel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;

import java.util.HashMap;

public class ClientManager {
    private class Contact {
        private int contactSent;
        private int contactReceived;

        /**
         * Constructor.
         */
        Contact() {
            contactSent = 0;
            contactReceived = 0;
        }

        /**
         * @return the total contacts value.
         */
        public int getValue() {
            return contactSent + contactReceived;
        }
    }

    private final ClientState state;
    private CautionLevel cautionLevel;
    private final HashMap<ClientState, Contact> contacts;

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
    }
    //endregion : Initialization

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
            if (isSent) {
                contacts.get(clientInContact).contactSent = contactValue;
            } else {
                contacts.get(clientInContact).contactReceived = contactValue;
            }
        } else {
            var contact = new Contact();
            if (isSent) {
                contact.contactSent = contactValue;
            } else {
                contact.contactReceived = contactValue;
            }
            contacts.put(clientInContact, contact);
            clientInContact.onStatusChange().add(this::handleContactStatusChange);
        }

        updateStatus();
    }

    //region : Action.sub-methods

    /**
     * Updates the `state status` according to those of the `contacts`.
     */
    private void updateStatus() {
        if (state.getStatus() == Status.INFECTED) {
            return;
        }

        var newStatus = evaluateStatus();
        if (state.getStatus() != newStatus) {
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
     * Simple redirection to `updateStatus()`.
     *
     * @param discard unused parameter.
     */
    private void handleContactStatusChange(final Status discard) {
        updateStatus();
    }
    //endregion : Event handler
}
