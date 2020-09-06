package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StopCovidServerView {
    private final VBox gui = new VBox();
    /** Get the GUI object corresponding to the server. */
    public Node getGui() {
        return gui;
    }

    /**
    * Declare this user as risky, i.e. having been in contact with an infected person.
    *
    * @param text  Name of the user to declare risky.
    */
    public void declareRisky(final String text) {
        for (Node c : gui.getChildren()) {
            if (((Label) c).getText().equals(text)) {
                return;
            }
        }
        gui.getChildren().add(new Label("Risky users:"));
        gui.getChildren().add(new Label(text));
        for (StopCovidUserView u : ((JfxView) gui.getParent().getParent()).getUsers()) {
            if (u.getName().equals(text)) {
                u.setStatus("RISKY");
            }
        }
    }

}
