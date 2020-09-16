package fr.univ_lyon1.info.m1.stopcovid_simulator.controller.simulator;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.ClientController;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.ClientModel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.SendingStrategy;
import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.Status;
import javafx.application.Platform;
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
    private Label idLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private ComboBox<String> meetComboBox;
    @FXML
    private Button meetButton;
    @FXML
    private ComboBox<SendingStrategy> sendingStrategyComboBox;
    @FXML
    private Button sendingStrategyButton;
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
        client.onIdChange().add(this::handleUpdateId);
        client.onStatusChange().add(this::handleUpdateStatus);

        idLabel.setText(String.format("Id : %s", client.getId()));
        statusLabel.setText(String.format("Status : %s", client.getStatus()));

        meetButton.setOnAction(this::handleMeet);

        sendingStrategyComboBox.getItems().add(SendingStrategy.ALL);
        sendingStrategyComboBox.getItems().add(SendingStrategy.REPEATED);
        sendingStrategyComboBox.getItems().add(SendingStrategy.FREQUENT);

        sendingStrategyButton.setOnAction(this::handleChangeSendingStrategy);

        openClientAppButton.setOnAction(this::handleOpenClientApp);
    }
    //endregion : Initialization

    //region : Getters & Setters

    /**
     * @return the `client id`.
     */
    public String getId() {
        return client.getId();
    }
    //endregion : Getters & Setters

    //region : Action

    /**
     * Renews the client ids that can be met (= `meet item`).
     *
     * @param meetItems List of new `meet items`.
     */
    public void renewsMeetItems(final List<String> meetItems) {
        meetComboBox.getItems().clear();
        for (var meetItem : meetItems) {
            if (!meetItem.equals(getId())) {
                meetComboBox.getItems().add(meetItem);
            }
        }
    }
    //endregion : Action

    //region : FX handler

    /**
     * Handler of `sending strategy button`.
     * Change the `sending strategy` with the one selected in the `sending strategy combo box`.
     *
     * @param event JavaFX event.
     */
    private void handleChangeSendingStrategy(final ActionEvent event) {
        var sendingStrategy = sendingStrategyComboBox.getValue();
        if (sendingStrategy != null) {
            client.setSendingStrategy(sendingStrategy);
        }
    }

    /**
     * Handler of `meet button`.
     * Meet the `client` and the client with the id selected in the `meet combo box`.
     *
     * @param event JavaFX event.
     */
    private void handleMeet(final ActionEvent event) {
        var id = meetComboBox.getValue();
        if (id != null) {
            client.meet(id);
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
