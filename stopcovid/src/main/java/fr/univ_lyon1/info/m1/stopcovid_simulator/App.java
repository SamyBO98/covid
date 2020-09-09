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
//        mainStage.setScene(CreateMainScene());
        mainStage.show();

        System.out.println(getClass().getResource("/fr/univ_lyon1/info/m1/stopcovid_simulator/view"));
    }

    public static void main(final String[] args) {
        launch(args);
    }

    private Scene CreateMainScene() throws IOException {
        var mainController = new MainController();

        var mainFXMLLoader = new FXMLLoader(getClass().getResource("/fr/univ_lyon1/info/m1/stopcovid_simulator/view/MainView.fxml"));
        mainFXMLLoader.setController(mainController);

        return new Scene(mainFXMLLoader.load());
    }
}