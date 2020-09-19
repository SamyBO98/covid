package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import fr.univ_lyon1.info.m1.stopcovid_simulator.util.Destroyable;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.SendingStrategy;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.events.Event;

import java.util.HashMap;
import java.util.UUID;

public class ClientModel implements Destroyable {
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

    /**
     * Disconnect to the server.
     */
    public void disconnect() {
        serverMessaging.handleDisconnect(connectionId);
        serverMessaging = null;
    }
    //endregion

    private final ClientState state;
    private SendingStrategy sendingStrategy;
    private final HashMap<String, Integer> tempContacts;
    private final Thread idUpdater;

    //region : Initialization

    /**
     * Constructor.
     */
    public ClientModel() {
        state = new ClientState();
        tempContacts = new HashMap<>();
        sendingStrategy = SendingStrategy.REPEATED;

        idUpdater = new Thread(this::renewIdLoopCor);
        idUpdater.start();
    }
    //endregion : Initialization

    //region : Ending

    /**
     * Destructor.
     */
    @Override
    public void destroy() {
        disconnect();
        idUpdater.interrupt();
    }
    //endregion : Ending

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
    public Event.With1Params<String> onIdChange() {
        return state.onIdChange();
    }

    /**
     * @return the `state status change`.
     */
    public Event.With1Params<Status> onStatusChange() {
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
        if (serverMessaging == null) {
            return;
        }
        serverMessaging.handleRegisterState(connectionId, state);
    }

    /**
     * Send a `update id message` to server.
     */
    private void updateId() {
        state.setId(UUID.randomUUID().toString());
        if (serverMessaging == null) {
            return;
        }
        serverMessaging.handleUpdateId(connectionId, state.getId());
    }

    /**
     * Send a `declare infected message` to server.
     */
    public void declareInfected() {
        state.setStatus(Status.INFECTED);
        if (serverMessaging == null) {
            return;
        }
        serverMessaging.handleDeclareInfected(connectionId);
    }

    /**
     * Send a `declare contact message` to server.
     *
     * @param id           The id of the client in contact.
     * @param contactValue The number of times they contacted.
     */
    private void declareContact(final String id, final int contactValue) {
        if (serverMessaging == null) {
            return;
        }
        serverMessaging.handleDeclareContact(connectionId, id, contactValue);
    }
    //endregion : Messaging sender

    //region : Coroutine

    /**
     * Id renewal coroutine.
     */
    private void renewIdLoopCor() {
        while (true) {
            try {
                Thread.sleep(SimulatedTime.UPDATE_ID_DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            updateId();
        }
    }
    //endregion : Coroutine
}
