package fr.univ_lyon1.info.m1.stopcovid_simulator;

import fr.univ_lyon1.info.m1.stopcovid_simulator.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(final Stage mainStage) throws Exception {
        mainStage.setTitle("Stop COVID");
        mainStage.setScene(createMainScene());
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

    private Scene createMainScene() throws IOException {
        var mainController = new MainController();

        var mainFXMLLoader = new FXMLLoader(getClass()
                .getResource("/view/MainView.fxml"));
        mainFXMLLoader.setController(mainController);

        return new Scene(mainFXMLLoader.load());
    }
}
