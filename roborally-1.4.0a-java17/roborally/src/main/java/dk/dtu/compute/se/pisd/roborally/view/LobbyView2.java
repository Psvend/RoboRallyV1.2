package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
import dk.dtu.compute.se.pisd.roborally.client.Data.Players;
import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
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

public class LobbyView2 {

    private Stage lobbyStage;
    private GameController gameController;
    private RoboRally roboRally;

    private List<Players> joinedPlayers;




    private HttpClientAsynchronousPost httpClient = new HttpClientAsynchronousPost();

    private AppController appController;
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");


    public LobbyView2(RoboRally roboRally) {

        lobbyStage = new Stage();
        this.roboRally = roboRally;

        lobbyStage.setTitle("Lobby " + HttpClientAsynchronousPost.currentGame.getGameName());
        VBox dialogVbox = new VBox(10);
        dialogVbox.setPadding(new Insets(10, 10, 10, 10));

        Scene lobbyScene = new Scene(dialogVbox, 300, 400);
        lobbyStage.setScene(lobbyScene);

        //Starts the game with the game view
        Button startGameButton = new Button("Start Game"); // Add a test button
        dialogVbox.getChildren().add(startGameButton);

        //sets action of the startGame button
        startGameButton.setOnAction(e -> {
            lobbyStage.close();
            Board board = new Board(8, 8);
            int amountPlayers = HttpClientAsynchronousPost.currentGame.getPlayersAmount();

            //creates players on the board
            for (int i = 0; i < amountPlayers ; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));
            }


            gameController = new GameController(board);
            System.out.println("Starting Game successfully");
            gameController.startProgrammingPhase();
            roboRally.createBoardView(gameController);
        });

        try {
            HttpClientAsynchronousPost.getPlayers(HttpClientAsynchronousPost.currentGame.getGameId()).thenAccept(players -> {
                // Use Platform.runLater to update the UI on the JavaFX Application Thread
                Platform.runLater(() -> {
                    Label joinedPlayers = new Label(HttpClientAsynchronousPost.player.getGameID().getJoinedPlayers()+ " Players out of " + HttpClientAsynchronousPost.currentGame.getPlayersAmount());
                    dialogVbox.getChildren().add(joinedPlayers);
                    //dialogVbox.getChildren().add(updatePlayerListButton);
                    for (Players player : players) {
                        //updates updateButton to get list of players
                        Button updatePlayerListButton = new Button(player.getPlayerName());
                        dialogVbox.getChildren().add(updatePlayerListButton);
                    }
                });
            }).exceptionally(ex -> {
                ex.printStackTrace();
                System.out.println("Error in lobby.");
                return null;
            });
        } catch (Exception e) {
            System.out.println("get list of players failed");
        }
    }





    public void show() {
        lobbyStage.show();
    }


}
