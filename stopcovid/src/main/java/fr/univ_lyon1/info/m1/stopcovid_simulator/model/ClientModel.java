package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import com.github.hervian.reflection.Event;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.SendingStrategy;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;

import java.util.HashMap;
import java.util.UUID;

public class ClientModel {
    //region : Very bad messaging simulator -> #VBMS
    //To replace by a messaging system.
    private int connectionId;
    private ServerModel serverMessaging;

    /**
     * Connect to the server.
     *
     * @param connectionId    The unique connection id assigned.
     * @param serverMessaging The very bad messaging simulator.
     */
    public void connect(final int connectionId, final ServerModel serverMessaging) {
        this.connectionId = connectionId;
        this.serverMessaging = serverMessaging;

        handleConnect();
    }
    //endregion

    private final ClientState state;
    private final HashMap<String, Integer> tempContacts;
    private SendingStrategy sendingStrategy;

    //region : Initialization

    /**
     * Constructor.
     */
    public ClientModel() {
        state = new ClientState();
        tempContacts = new HashMap<>();
        sendingStrategy = SendingStrategy.REPEATED;
    }
    //endregion : Initialization

    //region : Getters & Setters

    /**
     * @return the `state id`.
     */
    public String getId() {
        return state.getId();
    }


    /**
     * @return the `state status`.
     */
    public Status getStatus() {
        return state.getStatus();
    }

    /**
     * @return the `state id change`.
     */
    public Event.With1ParamAndVoid<String> onIdChange() {
        return state.onIdChange();
    }

    /**
     * @return the `state status change`.
     */
    public Event.With1ParamAndVoid<Status> onStatusChange() {
        return state.onStatusChange();
    }

    /**
     * Set `this sending strategy` with `sending strategy`.
     *
     * @param sendingStrategy The sending strategy to set.
     */
    public void setSendingStrategy(final SendingStrategy sendingStrategy) {
        this.sendingStrategy = sendingStrategy;
    }
    //endregion : Getters & Setters

    //region : Action

    /**
     * Meet the client with `id`.
     *
     * @param id The id of the client to met.
     */
    public void meet(final String id) {
        if (tempContacts.containsKey(id)) {
            tempContacts.replace(id, tempContacts.get(id) + 1);
        } else {
            tempContacts.put(id, 1);
        }

        var contactValue = tempContacts.get(id);
        if (contactValue >= sendingStrategy.getSendingThreshold()) {
            declareContact(id, contactValue);
        }
    }
    //endregion : Action

    //region : Messaging handler (#VBMS content)

    /**
     * Handler of connection to server.
     */
    private void handleConnect() {
        registerState();
    }

    /**
     * Handler of `declare risky message`.
     */
    public void handleDeclareRisky() {
        state.setStatus(Status.RISKY);
    }

    /**
     * Handler of `declare no risk message`.
     */
    public void handleDeclareNoRisk() {
        state.setStatus(Status.NO_RISK);
    }
    //endregion : Messaging handler

    //region : Messaging sender (#VBMS content)

    /**
     * Send a `register state message` to server.
     */
    private void registerState() {
        serverMessaging.handleRegisterState(connectionId, state);
    }

    /**
     * Send a `update id message` to server.
     */
    private void updateId() {
        state.setId(UUID.randomUUID().toString());
        serverMessaging.handleUpdateId(connectionId, state.getId());
    }

    /**
     * Send a `declare infected message` to server.
     */
    public void declareInfected() {
        state.setStatus(Status.INFECTED);
        serverMessaging.handleDeclareInfected(connectionId);
    }

    /**
     * Send a `declare contact message` to server.
     *
     * @param id           The id of the client in contact.
     * @param contactValue The number of times they contacted.
     */
    private void declareContact(final String id, final int contactValue) {
        serverMessaging.handleDeclareContact(connectionId, id, contactValue);
    }
    //endregion : Messaging sender
}
