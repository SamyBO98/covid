package fr.univ_lyon1.info.m1.stopcovid_simulator.controller.simulator;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.ClientModel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.ServerModel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.Destroyable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SimulatorController implements Initializable, Destroyable {
    //region : Very bad messaging simulator -> #VBMS
    //To replace by a messaging system.
    private ServerModel serverMessaging;

    private int incrementalId;
    //endregion : Very bad messaging simulator

    @FXML
    private Button serverLauncherButton;
    @FXML
    private Pane serverControlPanelContainer;
    @FXML
    private Button addClientButton;
    @FXML
    private Pane clientControlPanelsContainer;

    private final List<ClientControlPanelController> clientControlPanelControllers;
    private ServerControlPanelController serverControlPanelController;

    //region : Initialization

    /**
     * Constructor.
     */
    public SimulatorController() { //#VBMS content
        clientControlPanelControllers = new ArrayList<>();

        incrementalId = 0;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        setStartServerButton();
        addClientButton.setOnAction(this::handleAddClient);
    }

    //region : Initialization.sub-methods

    /**
     * Initializes the `server launcher button` to start `server`.
     */
    private void setStartServerButton() {
        serverLauncherButton.getStyleClass().remove("btn-danger");
        serverLauncherButton.getStyleClass().add("btn-success");

        serverLauncherButton.setText("Start !");

        serverLauncherButton.setOnAction(this::handleStartServer);
    }

    /**
     * Initializes the `server launcher button` to stop `server`.
     * Stop `server` doesn't work, so the `server launcher button` is disabled instead.
     */
    private void setStopServerButton() {
        serverLauncherButton.getStyleClass().remove("btn-success");
        serverLauncherButton.getStyleClass().add("btn-danger");

        serverLauncherButton.setText("Stop !");

        serverLauncherButton.setOnAction(this::handleStopServer);
    }
    //endregion : Initialization.sub-methods
    //endregion : Initialization

    //region : Ending

    /**
     * Destructor.
     */
    @Override
    public void destroy() {
        stopServer();
    }
    //endregion : Ending

    //region : FX handler

    /**
     * Handler of `server launcher button`.
     * Start `server`.
     *
     * @param event JavaFX event.
     */
    private void handleStartServer(final ActionEvent event) { //#VBMS content
        var server = new ServerModel();
        serverMessaging = server;

        setStopServerButton();
        addClientButton.setDisable(false);

        createServerControlPanel(server);
    }

    /**
     * Handler of `server launcher button`.
     * Stop `server` and remove connected clients.
     *
     * @param event JavaFX event.
     */
    private void handleStopServer(final ActionEvent event) { //#VBMS content
        stopServer();
        setStartServerButton();
        addClientButton.setDisable(true);
    }

    /**
     * Stop `server` and remove connected clients.
     */
    private void stopServer() {
        for (var clientControlPanelController : clientControlPanelControllers) {
            removeClient(clientControlPanelController);
        }
        clientControlPanelControllers.clear();

        serverControlPanelContainer.getChildren().remove(serverControlPanelController.getRoot());

        serverMessaging = null;
    }

    /**
     * Handler of `add client button`.
     * Add client.
     *
     * @param event JavaFX event.
     */
    private void handleAddClient(final ActionEvent event) { //#VBMS content
        var clientConnectionId = ++incrementalId;
        var client = new ClientModel();

        createClientControlPanel(client);
        client.onIdChange().add(this::handleSomeIdChange);
        renewsMeetItems();

        client.connect(clientConnectionId, serverMessaging);
        serverMessaging.connect(clientConnectionId, client);
    }

    //region : FX handler.sub-methods

    /**
     * Create a `server` control panel.
     *
     * @param server The server to control.
     */
    private void createServerControlPanel(final ServerModel server) {
        var serverControlPanelController = new ServerControlPanelController(server);

        var serverControlPanelFXMLLoader = new FXMLLoader(
                getClass().getResource("/view/simulator/ServerControlPanelView.fxml")
        );
        serverControlPanelFXMLLoader.setController(serverControlPanelController);

        try {
            serverControlPanelContainer.getChildren().add(serverControlPanelFXMLLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.serverControlPanelController = serverControlPanelController;
    }

    /**
     * Create a `client` control panel.
     *
     * @param client The client to control.
     */
    private void createClientControlPanel(final ClientModel client) {
        var clientControlPanelController = new ClientControlPanelController(client);

        var clientControlPanelFXMLLoader = new FXMLLoader(
                getClass().getResource("/view/simulator/ClientControlPanelView.fxml")
        );
        clientControlPanelFXMLLoader.setController(clientControlPanelController);

        try {
            clientControlPanelsContainer.getChildren().add(clientControlPanelFXMLLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientControlPanelController.setOnRemoveClientAction(e -> {
            removeClient(clientControlPanelController);
            clientControlPanelControllers.remove(clientControlPanelController);
        });
        clientControlPanelControllers.add(clientControlPanelController);
    }

    /**
     * Remove client.
     *
     * @param clientControlPanelController The `client control panel controller`
     *                                     of the client to remove.
     */
    private void removeClient(final ClientControlPanelController clientControlPanelController) {
        clientControlPanelsContainer.getChildren().remove(
                clientControlPanelController.getRoot()
        );
        clientControlPanelController.destroy();
    }
    //endregion : FX handler.sub-methods
    //endregion : FX handler

    //region : Event handler

    /**
     * Handle of clients id change.
     * Redirection to renews meet items method in the FX Thread.
     *
     * @param discard Discarded paramater.
     */
    private void handleSomeIdChange(final String discard) {
        Platform.runLater(this::renewsMeetItems);
    }
    //endregion : Event handler

    /**
     * Renews the list of client ids that can be met for each `client control panel controller`.
     */
    private void renewsMeetItems() {
        var meetItems = new ArrayList<String>();
        for (var clientControlPanelController : clientControlPanelControllers) {
            meetItems.add(clientControlPanelController.getId());
        }

        for (var clientControlPanelController : clientControlPanelControllers) {
            clientControlPanelController.renewsMeetItems(meetItems);
        }
    }
}
