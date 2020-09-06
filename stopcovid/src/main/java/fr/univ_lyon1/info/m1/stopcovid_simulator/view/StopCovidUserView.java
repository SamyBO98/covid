package fr.univ_lyon1.info.m1.stopcovid_simulator.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StopCovidUserView {
    private final VBox gui = new VBox();
    private final VBox contacts = new VBox();
    private final Label status = new Label("NO_DANGER");
    private final String name;
    private final EventHandler<ActionEvent> declare = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            setStatus("INFECTED");
            final StopCovidServerView server =
                    ((JfxView) (gui.getParent().getParent())).getServer();
            for (final Node l : contacts.getChildren()) {
                server.declareRisky(((Label) l).getText());
            }
        }
    };

    Node getGui() {
        return gui;
    }

    StopCovidUserView(final String name) {
        this.name = name;
        final Label l = new Label(name);
        gui.setStyle("-fx-padding: 10; -fx-border-width: 1;"
                + " -fx-border-radius: 5; -fx-border-color: #505050;");

        final Button declareBtn = new Button("Declare Infected");
        declareBtn.setOnAction(this.declare);
        gui.getChildren().addAll(l, new Label("Contacts:"), contacts, declareBtn, status);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Simulate the meeting of two users. Each user will keep the identifier of
     * the other in memory, and will notify the other if infected.
     *
     * @param otherUser The other user being met.
     */
    public void meet(final StopCovidUserView otherUser) {
        for (final Node c : contacts.getChildren()) {
            if (((Label) c).getText().equals(otherUser.toString())) {
                return;
            }
        }
        contacts.getChildren().add(new Label(otherUser.toString()));
    }

    public String getName() {
        return name;
    }

    /**
     * Changes the status in the view.
     * @param status the new status.
     */
    public void setStatus(final String status) {
        this.status.setText(status);
    }
}
