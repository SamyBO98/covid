package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.CautionLevel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;

import java.util.HashMap;

public class ServerModel {
    //region : Very bad messaging simulator -> #VBMS
    //To replace by a messaging system.
    private final HashMap<Integer, ClientModel> clientsMessaging;

    /**
     * Connect to client.
     *
     * @param connectionId The connection id of the client.
     * @param client       The very bad messaging simulator.
     */
    public void connect(final int connectionId, final ClientModel client) {
        clientsMessaging.put(connectionId, client);
    }
    //endregion : Very bad messaging simulator

    private final HashMap<Integer, ClientManager> clients;
    private CautionLevel cautionLevel;

    //region : Initialization

    /**
     * Constructor.
     */
    public ServerModel() { //#VBMS content
        clientsMessaging = new HashMap<>();
        clients = new HashMap<>();
        cautionLevel = CautionLevel.BASIC;
    }
    //endregion : Initialization

    //region : Getters & Setters

    /**
     * Set `this caution level` with `caution level`.
     *
     * @param cautionLevel The caution level to set.
     */
    public void setCautionLevel(final CautionLevel cautionLevel) {
        this.cautionLevel = cautionLevel;

        for (var client : clients.values()) {
            client.setCautionLevel(cautionLevel);
        }
    }
    //endregion : Getters & Setters

    //region : Messaging handler (#VBMS content)

    /**
     * Handler of `register state message`.
     *
     * @param connectionId The connection id of the sender client.
     * @param state        The state of sender client to register.
     */
    public void handleRegisterState(final int connectionId, final ClientState state) {
        var client = new ClientManager(state, cautionLevel);
        clients.put(connectionId, client);

        client.onStateChange().add(this::handleStateChange);
    }

    /**
     * Handler of `update id message`.
     *
     * @param connectionId The connection id of the sender client.
     * @param id           The new id of the sender client.
     */
    public void handleUpdateId(final int connectionId, final String id) {
        clients.get(connectionId).setId(id);
    }

    /**
     * Handler of `declare infected message`.
     *
     * @param connectionId The connection id of the sender client.
     */
    public void handleDeclareInfected(final int connectionId) {
        clients.get(connectionId).setStatus(Status.INFECTED);
    }

    /**
     * Handler of `declare contact message`.
     *
     * @param connectionId The connection id of the sender client.
     * @param id           The id of the client in contact with the sender client.
     * @param contactValue The number of times they contacted.
     */
    public void handleDeclareContact(final int connectionId, final String id,
                                     final int contactValue) {
        var client0 = clients.get(connectionId);
        var client1 = getClient(id);

        client0.addContact(client1.getState(), contactValue, true);
        client1.addContact(client0.getState(), contactValue, false);
    }
    //endregion

    //region : Messaging sender (#VBMS content)

    /**
     * Send a `declare risky message` to client.
     *
     * @param connectionId The connection id of the client to send.
     */
    private void declareRisky(final int connectionId) {
        clientsMessaging.get(connectionId).handleDeclareRisky();
    }

    /**
     * Send a `declare no risk message` to client.
     *
     * @param connectionId The connection id of the client to send.
     */
    private void declareNoRisk(final int connectionId) {
        clientsMessaging.get(connectionId).handleDeclareNoRisk();
    }
    //endregion : Messaging sender

    //region : Event handler

    /**
     * Handler of `clients state change`.
     * Declare the change of status to the client.
     *
     * @param id The id of the client who changed.
     */
    private void handleStateChange(final String id) {
        var client = getClient(id);
        var connectionId = getConnectionId(client);

        switch (client.getState().getStatus()) {
            case NO_RISK:
                declareNoRisk(connectionId);
                return;
            case RISKY:
                declareRisky(connectionId);
                return;
            default:
                throw new IllegalArgumentException();
        }
    }
    //endregion : Event handler

    //region : Sub-methods

    /**
     * @param id The id of client to search for.
     * @return the client with `id`.
     */
    private ClientManager getClient(final String id) {
        for (var client : clients.values()) {
            if (client.getState().getId().equals(id)) {
                return client;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * @param client The client with the connection id to search for.
     * @return the `client connection id`.
     */
    private int getConnectionId(final ClientManager client) {
        for (var connectionId : clients.keySet()) {
            if (clients.get(connectionId) == client) {
                return connectionId;
            }
        }
        throw new IllegalArgumentException();
    }
    //endregion : Sub-methods
}
