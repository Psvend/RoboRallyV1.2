package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
import dk.dtu.compute.se.pisd.roborally.client.Data.Players;
import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class AvailableGamesView {
    private Stage gamesStage;
    private RoboRally roboRally;
    private List<Games> availableGames;
    private HttpClientAsynchronousPost httpClient = new HttpClientAsynchronousPost();

    public AvailableGamesView(RoboRally roboRally) throws Exception {
        gamesStage = new Stage();
        this.roboRally = roboRally;
        gamesStage.setTitle("Available Games and Player Name");

        VBox dialogVboxPlayerLobby = new VBox();
        dialogVboxPlayerLobby.setPadding(new Insets(10, 10, 10, 10));
        dialogVboxPlayerLobby.getChildren().add(new Label("Player Name: "));

        VBox dialogVboxGamesLobby = new VBox();
        dialogVboxGamesLobby.setPadding(new Insets(10, 10, 50, 10));
        dialogVboxGamesLobby.getChildren().add(new Label("List of Available Games: "));

        //add the row to here
        GridPane root = new GridPane();
        root.add(dialogVboxPlayerLobby,0,0);
        root.add(dialogVboxGamesLobby,1,0);


        //Adds the input box for the player name
        // Player Names
        TextField playerNameField = new TextField();
        playerNameField.setPromptText("Player 1 Name");
        dialogVboxPlayerLobby.getChildren().addAll(playerNameField);
        //httpClient.addPlayer();


        //Adds the list of available games
        HttpClientAsynchronousPost.getAvailableGames().thenAccept(games -> {
            availableGames = games;

            // Use Platform.runLater to update the UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                for(Games game : availableGames) {
                    Button gameButton = new Button(game.getGameName());
                    dialogVboxGamesLobby.getChildren().add(gameButton);
                    gameButton.setOnAction(e -> {
                        try {
                            HttpClientAsynchronousPost.addPlayer(newPlayer(playerNameField.getText(), game)).thenAccept(player -> {
                                System.out.println("Player added: " + player);

                            HttpClientAsynchronousPost.getCurrentGame(game.getGameId()).thenAccept(currentGame -> {
                                System.out.println("Current game: " + currentGame);

                            Platform.runLater(() -> {
                                gamesStage.close();
                                //links to the new lobby
                                LobbyView2 lobby = new LobbyView2(roboRally);
                                lobby.show();
                            });});});
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            System.out.println("Error adding player to game.");
                        }
                    });
                }
            });



        }).exceptionally(ex -> {
            ex.printStackTrace();
            System.out.println("Error setting up game.");
            return null;
        });



        Scene gamesScene = new Scene(root, 350, 400);
        gamesStage.setScene(gamesScene);
        gamesStage.show();
    }

    public void show() {
        gamesStage.show();
    }
    private Players newPlayer(String playerName, Games game) {
        Players newPlayer = new Players();
        newPlayer.setPlayerId(0);
        newPlayer.setPlayerName(playerName);
        newPlayer.setPhaseStatus(0); // Initial phase status
        newPlayer.setGameID(game);
        return newPlayer;
    }
}
