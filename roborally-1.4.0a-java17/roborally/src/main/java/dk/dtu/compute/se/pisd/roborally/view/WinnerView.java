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


public class WinnerView extends BoardView {

    private CheckpointSpace checkPointSpace;

    private GridPane winnerBoardPane;

    private Label winnerLabel;

    private Board board;


    private Player player;

    private AppController appController;

    private GameController gameController;


    //@NotNull AppController appController
    public WinnerView (@NotNull GameController gameController) {
        super(gameController);
        board = gameController.board;
        //this.appController = appController;
        //this.gameController = gameController;
        //getWinner.

        winnerBoardPane = new GridPane();
        winnerLabel = new Label();

        this.getChildren().add(winnerBoardPane);
        this.getChildren().add(winnerLabel);
        System.out.println("MIWMIW");

        board.attach(this);
        update(board);
    }


    @Override
    public void updateView(Subject subject) {
        if (subject == board && board.getPhase() == Phase.RESULT) {
            // Update GUI to display winning player's name
            Player winner = board.getCurrentPlayer(); // Get the winning player from the Board
            winnerLabel.setText("THE WINNER IS " + winner.getName());
        }
    }
     
}
