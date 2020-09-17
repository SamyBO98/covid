package fr.univ_lyon1.info.m1.stopcovid_simulator;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.simulator.SimulatorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(final Stage mainStage) throws Exception {
        mainStage.setTitle("Stop COVID : Simulator");
        setMainScene(mainStage);
        mainStage.show();
    }

    /**
     * A main method in case the user launches the application using
     * App as the main class.
     *
     * @param args Command-line arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }

    private void setMainScene(final Stage mainStage) throws IOException {
        var mainController = new SimulatorController();

        var mainFXMLLoader = new FXMLLoader(
                getClass().getResource("/view/simulator/SimulatorView.fxml")
        );
        mainFXMLLoader.setController(mainController);

        var mainScene = new Scene(mainFXMLLoader.load());
        mainStage.setScene(mainScene);
        mainStage.setOnHidden(e -> mainController.destroy());
    }
}
