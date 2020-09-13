package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import com.github.hervian.reflection.Delegate;
import com.github.hervian.reflection.Event;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;

import java.util.UUID;

public class ClientState {
    private String pseudo;
    private Status status;

    private final Delegate.With1ParamAndVoid<String> pseudoChangeDelegate;
    private final Delegate.With1ParamAndVoid<Status> statusChangeDelegate;
    private final Event.With1ParamAndVoid<String> pseudoChangeEvent;
    private final Event.With1ParamAndVoid<Status> statusChangeEvent;

    //region : Initialization

    /**
     * Constructor.
     */
    public ClientState() {
        pseudo = UUID.randomUUID().toString();
        status = Status.NO_RISK;

        pseudoChangeDelegate = new Delegate.With1ParamAndVoid<>();
        statusChangeDelegate = new Delegate.With1ParamAndVoid<>();
        pseudoChangeEvent = new Event.With1ParamAndVoid<>(pseudoChangeDelegate);
        statusChangeEvent = new Event.With1ParamAndVoid<>(statusChangeDelegate);
    }

    /**
     * Copy constructor.
     *
     * @param source The source to copy.
     */
    public ClientState(final ClientState source) {
        pseudo = source.pseudo;
        status = source.status;

        pseudoChangeDelegate = new Delegate.With1ParamAndVoid<>();
        statusChangeDelegate = new Delegate.With1ParamAndVoid<>();
        pseudoChangeEvent = new Event.With1ParamAndVoid<>(pseudoChangeDelegate);
        statusChangeEvent = new Event.With1ParamAndVoid<>(statusChangeDelegate);
    }
    //endregion : Initialization

    //region : Getters & Setters

    /**
     * @return `this pseudo`.
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return `this status`.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return `this pseudo change event`.
     */
    public Event.With1ParamAndVoid<String> onPseudoChange() {
        return pseudoChangeEvent;
    }

    /**
     * @return `this status change event`.
     */
    public Event.With1ParamAndVoid<Status> onStatusChange() {
        return statusChangeEvent;
    }

    /**
     * Set `this pseudo` with `pseudo`.
     *
     * @param pseudo The pseudo to set.
     */
    public void setPseudo(final String pseudo) {
        this.pseudo = pseudo;
        pseudoChangeDelegate.invokeAndAggregateExceptions(pseudo);
    }

    /**
     * Set `this status` with `status`.
     *
     * @param status The status to set.
     */
    public void setStatus(final Status status) {
        this.status = status;
        statusChangeDelegate.invokeAndAggregateExceptions(status);
    }
    //endregion : Getters & Setters
}
