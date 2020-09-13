package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.ClientModel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private Label pseudoLabel;
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
        client.onPseudoChange().add(this::handleUpdatePseudo);
        client.onStatusChange().add(this::handleUpdateStatus);

        pseudoLabel.setText(String.format("Pseudo : %s", client.getPseudo()));
        statusLabel.setText(String.format("Status : %s", client.getStatus()));

        declareInfectedButton.setOnAction(this::handleDeclareInfected);
    }
    //endregion : Initialization

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
     * Handler of `client pseudo change`.
     * Updates the `pseudo label` with the `pseudo`.
     *
     * @param pseudo The new pseudo of `client`.
     */
    private void handleUpdatePseudo(final String pseudo) {
        pseudoLabel.setText(String.format("Pseudo : %s", pseudo));
    }

    /**
     * Handler of `client status change`.
     * Updates the `status label` with the `status`.
     *
     * @param status The new status of `client`.
     */
    private void handleUpdateStatus(final Status status) {
        statusLabel.setText(String.format("Status : %s", status));
    }
    //endregion : Event handler
}
