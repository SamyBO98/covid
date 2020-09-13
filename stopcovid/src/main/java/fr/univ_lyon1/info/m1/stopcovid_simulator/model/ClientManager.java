package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import com.github.hervian.reflection.Delegate;
import com.github.hervian.reflection.Event;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.CautionLevel;

import java.util.ArrayList;

public class ClientManager {
    private final ClientState state;
    private CautionLevel cautionLevel;
    private final ArrayList<ClientState> contacts;

    private final Delegate.With1ParamAndVoid<String> stateChangeDelegate;
    private final Event.With1ParamAndVoid<String> stateChangeEvent;

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
        contacts = new ArrayList<>();

        stateChangeDelegate = new Delegate.With1ParamAndVoid<>();
        stateChangeEvent = new Event.With1ParamAndVoid<>(stateChangeDelegate);
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
     * @return `this state change event`.
     */
    public Event.With1ParamAndVoid<String> onStateChange() {
        return stateChangeEvent;
    }

    /**
     * Set the `state pseudo` with `pseudo`.
     *
     * @param pseudo The pseudo to set.
     */
    public void setPseudo(final String pseudo) {
        state.setPseudo(pseudo);
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
     * Add a `contact` to the client to manage.
     *
     * @param contact The state of the client in contact.
     */
    public void addContact(final ClientState contact) {
        contacts.add(contact);
        updateStatus();
        contact.onStatusChange().add(this::handleContactStatusChange);
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
            stateChangeDelegate.invokeAndAggregateExceptions(state.getPseudo());
        }
    }

    /**
     * @return the evaluated status according to those of the `contacts`.
     */
    private Status evaluateStatus() {
        var riskIndicator = 0f;
        for (var contact : contacts) {
            riskIndicator += contact.getStatus().getRisk();
        }

        if (riskIndicator < cautionLevel.getTolerance()) {
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
