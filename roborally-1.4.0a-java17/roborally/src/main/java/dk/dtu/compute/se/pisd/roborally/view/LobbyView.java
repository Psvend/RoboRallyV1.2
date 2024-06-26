
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LobbyView extends Tab implements ViewObserver{

    private AppController appController;
    private VBox top;

    private VBox buttonPanel;

    //add label or button to vbox
    private Button startGameButton;
    private Label playerLabel;
    private VBox playerBox;
    private BorderPane borderPane;
    private RoboRally roboRally;
    private Button ReadyButton;
    private Label howManyPlayers;
    private int playersNumber;




    public LobbyView(@NotNull Player player, @NotNull Board board, @NotNull GameController gameController) {
        playersNumber = 0;
        roboRally = new RoboRally();
        appController = new AppController(roboRally);
        this.setText("Lobby");
        top = new VBox();
        this.setContent(top);

        borderPane = new BorderPane();
        playerLabel = new Label("Players:");
        playerBox = new VBox();
        playerBox.setAlignment(Pos.CENTER_LEFT);

        List<Player> players = new ArrayList<>();
        for(int i = 0; i < board.getPlayersNumber(); i++) {
            Player p = board.getPlayer(i);
            players.add(p);
        }
        for (Player p : players) {
            Label playerLabel = new Label(p.getName());
            ReadyButton = new Button("is Ready"); // create a new button for each player

            ReadyButton.setOnAction(e -> {
                p.setReady(true);
                updateView(p);
            }); // set the action for the button

            HBox playerHBox = new HBox(playerLabel, ReadyButton); // create a new HBox for each player

            playerBox.getChildren().add(playerHBox); // add the HBox to the playerBox

            playersNumber++;

        }


        borderPane.setCenter(playerBox);
        howManyPlayers = new Label("Number of players: " +playersNumber+"/"+ board.getPlayersNumber());
        borderPane.setTop(howManyPlayers);




        startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> {
            System.out.println("Start Game");
            top.getChildren().clear();
            appController.startGame(board, gameController, player);
        });



        buttonPanel = new VBox(startGameButton);
        buttonPanel.setAlignment(Pos.BOTTOM_RIGHT);
        buttonPanel.setSpacing(3.0);

        top.getChildren().add(playerBox);
        top.getChildren().add(buttonPanel);
        top.getChildren().add(borderPane);



        if (player.board != null) {
            player.board.attach(this);
            update(player.board);

        }
        updateView(player);

    }

    @Override
    public void updateView(Subject subject) {
        if (subject instanceof Player player) {

            if (player.isReady()) {
                startGameButton.setText("Ready to Start");
                ReadyButton.setText("Ready");
            } else {
                startGameButton.setText("Start Game");

            }
        }


    }
}
