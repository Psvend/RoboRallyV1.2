package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    private HBox playerBox;
    private BorderPane borderPane;



    public LobbyView(@NotNull Player player) {
        this.setText("Lobby");
        top = new VBox();
        this.setContent(top);

        borderPane = new BorderPane();
        playerLabel = new Label("Players:");
        playerBox = new HBox();
        playerBox.setAlignment(Pos.CENTER_LEFT);

        List<Player> players = new ArrayList<>();

        players.add(player);
        for (Player p : players) {
            Label playerLabel = new Label(p.getName());
            playerBox.getChildren().add(playerLabel);
        }
        borderPane.setCenter(playerBox);





        startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> { appController.lobby(); });


        buttonPanel = new VBox(startGameButton);
        buttonPanel.setAlignment(Pos.BOTTOM_RIGHT);
        buttonPanel.setSpacing(3.0);


        top.getChildren().add(buttonPanel);
        top.getChildren().add(borderPane);




        updateView(player);

    }

    @Override
    public void updateView(Subject subject) {
        if (subject instanceof Player) {
            Player player = (Player) subject;
            if (player.isReady()) {
                startGameButton.setText("Ready to Start");
            } else {
                startGameButton.setText("Start Game");
            }
        }


    }
}
