package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
import dk.dtu.compute.se.pisd.roborally.client.Data.Players;
import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
import dk.dtu.compute.se.pisd.roborally.client.HttpGetPlayers;
import dk.dtu.compute.se.pisd.roborally.client.HttpPostPlayer;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LobbyView2 {

    private Stage lobbyStage;
    private GameController gameController;
    private RoboRally roboRally;

    private List<Players> joinedPlayers;
    private CompletableFuture<List<Players>> playerList;





    private HttpClientAsynchronousPost httpClient = new HttpClientAsynchronousPost();

    private AppController appController;
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    private int numberOfPlayers;

    public LobbyView2(RoboRally roboRally) {

        lobbyStage = new Stage();
        this.roboRally = roboRally;

        if (HttpPostPlayer.createdGame != null) {
            lobbyStage.setTitle("Lobby " + HttpPostPlayer.createdGame.getGameName());
            numberOfPlayers = HttpPostPlayer.createdGame.getPlayersAmount();
        } else {
            lobbyStage.setTitle("Lobby " + AvailableGamesView.gameName);
        }
        //Starts the game with the game view
        VBox dialogVbox = new VBox(10);
        dialogVbox.setPadding(new Insets(10, 10, 10, 10));

        Scene lobbyScene = new Scene(dialogVbox, 300, 400);
        lobbyStage.setScene(lobbyScene);

        Button startGameButton = new Button("Start Game"); // Add a test button
        dialogVbox.getChildren().add(startGameButton);

        //sets action of the startGame button
        startGameButton.setOnAction(e -> {
            Board board = new Board(8, 8);

            //creates players on the board
            for (int i = 0; i < numberOfPlayers; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));
            }


            gameController = new GameController(board);
            System.out.println("Starting Game successfully");
            gameController.startProgrammingPhase();
            roboRally.createBoardView(gameController);
        });
        Button updatePlayerListButton = new Button("Update");
        dialogVbox.getChildren().add(updatePlayerListButton);

        VBox vbPlayerNames = new VBox();
        dialogVbox.getChildren().add(vbPlayerNames);

        updatePlayerListButton.setOnAction(e -> {
            vbPlayerNames.getChildren().clear();

            try {
                if (HttpPostPlayer.createdGame != null) {
                    playerList = HttpGetPlayers.getPlayersInLobby(HttpPostPlayer.createdGame.getGameId());
                    System.out.println(playerList);
                } else {
                    playerList = HttpGetPlayers.getPlayersInLobby(AvailableGamesView.gameID);

                }
                //CompletableFuture<List<Players>> allPlayerNames = HttpGetPlayers.getPlayerNames(playerList);
                //System.out.println(allPlayerNames);

                playerList.thenAccept(players -> {
                    List<String> playerNames = HttpGetPlayers.getPlayerNames(players);

                    // Update the UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        for (String name : playerNames) {
                            Label label = new Label(name);
                            vbPlayerNames.getChildren().add(label);
                        }
                    });
                }).exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });



                // Use Platform.runLater to update the UI on the JavaFX Application Thread
                //Platform.runLater(() -> {
                   /* for (Player player : joinedPlayers) {
                        //updates updateButton to get list of players
                        Button updatePlayerListButton = new Button("Update"); // Add a test button
                        dialogVbox.getChildren().add(updatePlayerListButton);
                    }*/
                //});
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

    }





    public void show() {
        lobbyStage.show();
    }


}
