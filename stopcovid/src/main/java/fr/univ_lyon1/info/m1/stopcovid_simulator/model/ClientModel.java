package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import com.github.hervian.reflection.Event;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;

import java.util.ArrayList;
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
    private final ArrayList<String> tempContactPseudos;

    //region : Initialization

    /**
     * Constructor.
     */
    public ClientModel() {
        state = new ClientState();
        tempContactPseudos = new ArrayList<>();
    }
    //endregion : Initialization

    //region : Getters & Setters

    /**
     * @return the `state pseudo`.
     */
    public String getPseudo() {
        return state.getPseudo();
    }

    /**
     * @return the `state status`.
     */
    public Status getStatus() {
        return state.getStatus();
    }

    /**
     * @return the `state pseudo change`.
     */
    public Event.With1ParamAndVoid<String> onPseudoChange() {
        return state.onPseudoChange();
    }

    /**
     * @return the `state status change`.
     */
    public Event.With1ParamAndVoid<Status> onStatusChange() {
        return state.onStatusChange();
    }
    //endregion : Getters & Setters

    //region : Action

    /**
     * Meet the client with `pseudo`.
     *
     * @param pseudo The pseudo of the client to met.
     */
    public void meet(final String pseudo) {
        if (tempContactPseudos.contains(pseudo)) {
            declareContact(pseudo);
        } else {
            tempContactPseudos.add(pseudo);
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
     * Send a `update pseudo message` to server.
     */
    private void updatePseudo() {
        state.setPseudo(UUID.randomUUID().toString());
        serverMessaging.handleUpdatePseudo(connectionId, state.getPseudo());
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
     * @param pseudo The pseudo of the client in contact.
     */
    private void declareContact(final String pseudo) {
        serverMessaging.handleDeclareContact(connectionId, pseudo);
    }
    //endregion : Messaging sender
}
