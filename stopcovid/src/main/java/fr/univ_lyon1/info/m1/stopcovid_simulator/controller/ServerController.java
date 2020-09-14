package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.util.enums.CautionLevel;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.ServerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    @FXML
    private ComboBox<CautionLevel> cautionLevelComboBox;
    @FXML
    private Button changeCautionLevelButton;

    private final ServerModel server;

    //region : Initialization

    /**
     * Constructor.
     *
     * @param server The server to control.
     */
    public ServerController(final ServerModel server) {
        this.server = server;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        cautionLevelComboBox.getItems().add(CautionLevel.UNWARY);
        cautionLevelComboBox.getItems().add(CautionLevel.BASIC);
        cautionLevelComboBox.getItems().add(CautionLevel.WARY);

        changeCautionLevelButton.setOnAction(this::handleChangeCautionLevel);
    }
    //endregion : Initialization

    //region : FX handler

    /**
     * Handler of `change caution level button`.
     * Change the `caution level` with the one selected in the `caution level combo box`.
     *
     * @param event JavaFX event.
     */
    private void handleChangeCautionLevel(final ActionEvent event) {
        var cautionLevel = cautionLevelComboBox.getValue();
        if (cautionLevel != null) {
            server.setCautionLevel(cautionLevel);
        }
    }
    //endregion : FX handler
}
