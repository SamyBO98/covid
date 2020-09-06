package fr.univ_lyon1.info.m1.stopcovid_simulator;

import fr.univ_lyon1.info.m1.stopcovid_simulator.view.JfxView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {
    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    private static final int NB_USERS = 4;


    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        //StopCovidUser model = new StopCovidUser(new File("."));
        //Controller c = new Controller(model);
        new JfxView(stage, WIDTH, HEIGHT, NB_USERS);
    }


    /**
     * A main method in case the user launches the application using
     * App as the main class.
     *
     * @param args Command-line arguments
     */
    // TODO: checkstyle will (rightfully) complain about this brace. Make sure it
    // does, and then fix it. Remove this TODO comment when done.
    public static void main(final String[] args){
        Application.launch(args);
    }
}
