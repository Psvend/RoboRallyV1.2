package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class LobbyViewNY extends Tab {

    
    private AppController appController;
    private VBox top;
    private VBox buttonPanel;

    private Button startGameButton;
    private Label playerLabel;
    private VBox playerBox;
    private BorderPane borderPane;
    private RoboRally roboRally;
    private Button ReadyButton;
    private Label howManyPlayers;
    private int playersNumber;



    private Stage lobbyStage;

    private HttpClientAsynchronousPost httpClient = new HttpClientAsynchronousPost();

    public LobbyViewNY() {
        //Controls the view of the edges 
        lobbyStage = new Stage();
        lobbyStage.setTitle("Lobby");   

        VBox dialogVbox = new VBox(10);
        dialogVbox.setPadding(new Insets(10, 10, 10, 10));

        Scene lobbyScene = new Scene(dialogVbox, 300, 400);
        lobbyStage.setScene(lobbyScene);

    

        //handles the view of players
        playersNumber = 0;
        roboRally = new RoboRally();
        appController = new AppController(roboRally);
        top = new VBox();
        this.setContent(top);
    
        //handles the view on the pane itself
        borderPane = new BorderPane();
        playerLabel = new Label("Players:");
        playerBox = new VBox();
        playerBox.setAlignment(Pos.CENTER_LEFT);


        



    }

    //call when in need to show the entire view of the lobby
    public void show() {
        lobbyStage.show();
    }




}
