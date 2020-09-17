package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.ClientModel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.Destroyable;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable, Destroyable {
    @FXML
    private Node root;
    @FXML
    private Label idLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button declareInfectedButton;

    private final ClientModel client;

    //region : Initialization

    /**
     * Constructor.
     *
     * @param client The client to control.
     */
    public ClientController(final ClientModel client) {
        this.client = client;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        client.onIdChange().add(this::handleUpdateId);
        client.onStatusChange().add(this::handleUpdateStatus);

        idLabel.setText(String.format("Id : %s", client.getId()));
        statusLabel.setText(String.format("Status : %s", client.getStatus()));

        declareInfectedButton.setOnAction(this::handleDeclareInfected);
    }
    //endregion : Initialization

    //region : Ending

    /**
     * Destructor.
     */
    @Override
    public void destroy() {
        var stage = (Stage) root.getScene().getWindow();
        stage.close();
    }
    //endregion : Ending

    //region : FX handler

    /**
     * Handler of `declare infected button`.
     * Declare infected the `client`.
     *
     * @param event JavaFX event.
     */
    private void handleDeclareInfected(final ActionEvent event) {
        client.declareInfected();
    }
    //endregion : FX handler

    //region : Event handler

    /**
     * Handler of `client id change`.
     * Updates the `id label` with the `id`.
     *
     * @param id The new id of `client`.
     */
    private void handleUpdateId(final String id) {
        Platform.runLater(() -> idLabel.setText(String.format("Id : %s", id)));
    }

    /**
     * Handler of `client status change`.
     * Updates the `status label` with the `status`.
     *
     * @param status The new status of `client`.
     */
    private void handleUpdateStatus(final Status status) {
        Platform.runLater(() -> statusLabel.setText(String.format("Status : %s", status)));
    }
    //endregion : Event handler
}
