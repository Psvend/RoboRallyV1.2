package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;


public class WinnerView extends VBox implements ViewObserver {

    private CheckpointSpace checkPointSpace;

    private GridPane winnerBoardPane;

    private Label winnerLabel;

    private Player player;

    private AppController appController;

    public WinnerView (@NotNull AppController appController, @NotNull Player player) {
        this.appController = appController;

        winnerBoardPane = new GridPane();
        winnerLabel = new Label("THE WINNER IS: " + player.getName());
    }


    @Override
    public void updateView(Subject subject) {
        if (subject == player.getSpace()) {
            if (player.getSpace() instanceof CheckpointSpace) {
                
            }
        }
    }


    
}
