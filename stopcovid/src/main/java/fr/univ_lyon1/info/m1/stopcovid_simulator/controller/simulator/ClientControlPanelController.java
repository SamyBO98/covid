package fr.univ_lyon1.info.m1.stopcovid_simulator.controller.simulator;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.ClientController;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.ClientModel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientControlPanelController implements Initializable {
    @FXML
    private Label pseudoLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button meetButton;
    @FXML
    private ComboBox<String> meetComboBox;
    @FXML
    private Button openClientAppButton;
    @FXML
    private Button removeClientButton;

    private final ClientModel client;

    //region : Initialization

    /**
     * Constructor.
     *
     * @param client The client to control.
     */
    public ClientControlPanelController(final ClientModel client) {
        this.client = client;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        client.onPseudoChange().add(this::handleUpdatePseudo);
        client.onStatusChange().add(this::handleUpdateStatus);

        meetButton.setOnAction(this::handleMeet);
        openClientAppButton.setOnAction(this::handleOpenClientApp);

        handleUpdatePseudo(client.getPseudo());
        handleUpdateStatus(client.getStatus());
    }
    //endregion : Initialization

    //region : Getters & Setters

    /**
     * @return the `client pseudo`.
     */
    public String getPseudo() {
        return client.getPseudo();
    }
    //endregion : Getters & Setters

    //region : Action

    /**
     * Renews the client pseudos that can be met (= `meet item`).
     *
     * @param meetItems List of new `meet items`.
     */
    public void renewsMeetItems(final List<String> meetItems) {
        meetComboBox.getItems().clear();
        for (var meetItem : meetItems) {
            if (!meetItem.equals(getPseudo())) {
                meetComboBox.getItems().add(meetItem);
            }
        }
    }
    //endregion : Action

    //region : FX handler

    /**
     * Handler of `meet button`.
     * Meet the `client` and the client with the pseudo selected in the `meet combo box`.
     *
     * @param event JavaFX event.
     */
    private void handleMeet(final ActionEvent event) {
        var pseudo = meetComboBox.getValue();
        if (pseudo != null) {
            client.meet(pseudo);
        }
    }

    /**
     * Handler of `open client app button`.
     * Open the `client` application.
     *
     * @param event JavaFX event.
     */
    private void handleOpenClientApp(final ActionEvent event) {
        var clientController = new ClientController(client);

        var clientFXMLLoader = new FXMLLoader(getClass().getResource("/view/ClientView.fxml"));
        clientFXMLLoader.setController(clientController);

        try {
            var clientScene = new Scene(clientFXMLLoader.load());

            var clientStage = new Stage();
            clientStage.setTitle("Stop COVID - Client");
            clientStage.setScene(clientScene);
            clientStage.setOnHidden(e -> openClientAppButton.setDisable(false));
            clientStage.show();

            openClientAppButton.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
