package fr.univ_lyon1.info.m1.stopcovid_simulator.controller.prefabs;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.UserController;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserBoxController implements Initializable {
    @FXML
    private Label idLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button openUserButton;

    private final UserModel user;

    /**
     * this `user` : `user` reference.
     * @param user The `UserModel` to control
     */
    public UserBoxController(final UserModel user) {
        this.user = user;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        idLabel.setText(String.format("Id : %s", user.getId()));
        statusLabel.setText(String.format("Status : %s", user.getStatus()));
        openUserButton.setOnAction(this::openUser);
    }

    private void openUser(final ActionEvent actionEvent) {
        var userController = new UserController(user);

        var userFXMLLoader = new FXMLLoader(
                getClass().getResource("/view/UserView.fxml")
        );
        userFXMLLoader.setController(userController);

        try {
            var userStage = new Stage();
            userStage.setTitle("Stop COVID");
            userStage.setScene(new Scene(userFXMLLoader.load()));
//            userStage.setResizable(false);
            userStage.show();

            openUserButton.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
