package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import com.github.hervian.reflection.Delegate;
import com.github.hervian.reflection.Event;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;

import java.util.UUID;

public class ClientState {
    private String id;
    private Status status;

    private final Delegate.With1ParamAndVoid<String> idChangeDelegate;
    private final Delegate.With1ParamAndVoid<Status> statusChangeDelegate;
    private final Event.With1ParamAndVoid<String> idChangeEvent;
    private final Event.With1ParamAndVoid<Status> statusChangeEvent;

    //region : Initialization

    /**
     * Constructor.
     */
    public ClientState() {
        id = UUID.randomUUID().toString();
        status = Status.NO_RISK;

        idChangeDelegate = new Delegate.With1ParamAndVoid<>();
        statusChangeDelegate = new Delegate.With1ParamAndVoid<>();
        idChangeEvent = new Event.With1ParamAndVoid<>(idChangeDelegate);
        statusChangeEvent = new Event.With1ParamAndVoid<>(statusChangeDelegate);
    }

    /**
     * Copy constructor.
     *
     * @param source The source to copy.
     */
    public ClientState(final ClientState source) {
        id = source.id;
        status = source.status;

        idChangeDelegate = new Delegate.With1ParamAndVoid<>();
        statusChangeDelegate = new Delegate.With1ParamAndVoid<>();
        idChangeEvent = new Event.With1ParamAndVoid<>(idChangeDelegate);
        statusChangeEvent = new Event.With1ParamAndVoid<>(statusChangeDelegate);
    }
    //endregion : Initialization

    //region : Getters & Setters

    /**
     * @return `this id`.
     */
    public String getId() {
        return id;
    }

    /**
     * @return `this status`.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return `this id change event`.
     */
    public Event.With1ParamAndVoid<String> onIdChange() {
        return idChangeEvent;
    }

    /**
     * @return `this status change event`.
     */
    public Event.With1ParamAndVoid<Status> onStatusChange() {
        return statusChangeEvent;
    }

    /**
     * Set `this id` with `id`.
     *
     * @param id The id to set.
     */
    public void setId(final String id) {
        this.id = id;
        idChangeDelegate.invokeAndAggregateExceptions(id);
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
