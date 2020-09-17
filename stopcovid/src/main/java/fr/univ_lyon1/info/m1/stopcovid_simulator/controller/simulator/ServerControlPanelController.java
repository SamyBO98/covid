package fr.univ_lyon1.info.m1.stopcovid_simulator.controller.simulator;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.ServerController;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.ServerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerControlPanelController implements Initializable {
    @FXML
    private Button openServerAppButton;

    private final ServerModel server;

    //region : Initialization

    /**
     * Constructor.
     *
     * @param server The server to control.
     */
    public ServerControlPanelController(final ServerModel server) {
        this.server = server;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        openServerAppButton.setOnAction(this::handleOpenServerApp);
    }
    //endregion : Initialization

    //region : Getters & Setters

    /**
     * @return the root node.
     */
    public Node getRoot() {
        return openServerAppButton;
    }
    //endregion : Getters & Setters

    //region : FX handler

    /**
     * Handler of `open server app button`.
     * Open the `server` application.
     *
     * @param event JavaFX event.
     */
    private void handleOpenServerApp(final ActionEvent event) {
        var serverController = new ServerController(server);

        var serverFXMLLoader = new FXMLLoader(getClass().getResource("/view/ServerView.fxml"));
        serverFXMLLoader.setController(serverController);

        try {
            var serverScene = new Scene(serverFXMLLoader.load());

            var serverStage = new Stage();
            serverStage.setTitle("Stop COVID - Server");
            serverStage.setScene(serverScene);
            serverStage.setOnHidden(e -> openServerAppButton.setDisable(false));
            serverStage.show();

            openServerAppButton.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion : FX handler
}
