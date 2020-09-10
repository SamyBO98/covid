package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML private Label idLabel;
    @FXML private Label statusLabel;
    @FXML private Label contactsSummaryLabel;

    private final UserModel user;

    /**
     * this `user` : `user` reference.
     * @param user The `UserModel` to control.
     */
    public UserController(final UserModel user) {
        this.user = user;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        idLabel.setText(String.format("Id : %s", user.getId()));
        statusLabel.setText(String.format("Status : %s", user.getStatus()));
    }
}
