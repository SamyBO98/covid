package fr.univ_lyon1.info.m1.stopcovid_simulator.controller;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.prefabs.UserBoxController;
import fr.univ_lyon1.info.m1.stopcovid_simulator.model.ServerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    @FXML
    private Button addUserButton;
    @FXML
    private VBox userContainer;

    private final ServerModel server;
    private final ArrayList<UserBoxController> userBoxControllers;

    /**
     * `server` : New instance.
     * `userBoxControllers` : New instance.
     */
    public ServerController() {
        server = new ServerModel();
        userBoxControllers = new ArrayList<>();
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        addUserButton.setOnAction(this::addUser);
    }

    private void addUser(final ActionEvent actionEvent) {
        var userBoxController = new UserBoxController(server.createUser());

        var userBoxFXMLLoader = new FXMLLoader(
                getClass().getResource("/view/prefabs/UserBoxView.fxml")
        );
        userBoxFXMLLoader.setController(userBoxController);

        try {
            userContainer.getChildren().add(userBoxFXMLLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        userBoxControllers.add(userBoxController);
    }
}
