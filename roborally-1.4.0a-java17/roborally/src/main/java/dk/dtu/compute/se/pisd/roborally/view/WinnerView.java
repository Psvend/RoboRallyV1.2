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


public class WinnerView extends VBox {

    private CheckpointSpace checkPointSpace;

    private GridPane winnerBoardPane;

    private Label winnerLabel;

    private Player player;

    private AppController appController;

    private GameController gameController;

    public WinnerView (@NotNull AppController appController) {
        this.appController = appController;
        //this.gameController = gameController;
        //getWinner.

        winnerBoardPane = new GridPane();
        winnerLabel = new Label("THE WINNER IS YOU IDIOT");
    }    
}
