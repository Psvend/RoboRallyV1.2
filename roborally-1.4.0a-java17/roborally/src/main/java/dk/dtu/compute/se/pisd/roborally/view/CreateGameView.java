package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.client.Data.Board;
import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
import dk.dtu.compute.se.pisd.roborally.client.Data.Players;
import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
import dk.dtu.compute.se.pisd.roborally.client.Data.Players;
import dk.dtu.compute.se.pisd.roborally.view.CreateGameView;

import static dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost.addPlayer;
import static dk.dtu.compute.se.pisd.roborally.client.HttpPostPlayer.createFirstPlayer;


public class CreateGameView {

    private Stage dialogStage;
    private HttpClientAsynchronousPost httpClient = new HttpClientAsynchronousPost();
    private Games newGame;

    private Players firstPlayer;

    private RoboRally roboRally;

    public CreateGameView(RoboRally roboRally) {
        this.roboRally = roboRally;
        dialogStage = new Stage();
        dialogStage.setTitle("Game Setup");

        VBox dialogVbox = new VBox(10);
        dialogVbox.setPadding(new Insets(10, 10, 10, 10));

        // Game Name
        Label gameNameLabel = new Label("Game Name:");
        TextField gameNameField = new TextField();
        dialogVbox.getChildren().addAll(gameNameLabel, gameNameField);

        // Number of Players
        Label numPlayersLabel = new Label("Number of Players (2-6):");
        TextField numPlayersField = new TextField();
        dialogVbox.getChildren().addAll(numPlayersLabel, numPlayersField);

        // Player Names
        VBox playerNamesVbox = new VBox(5);
        Label playerNamesLabel = new Label("Player Names:");
        TextField player1NameField = new TextField();
        player1NameField.setPromptText("Player 1 Name");
        playerNamesVbox.getChildren().addAll(playerNamesLabel, player1NameField);

        numPlayersField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numPlayersField.setText(newValue.replaceAll("[^\\d]", ""));
                return;
            }

            int numPlayers;
            try {
                numPlayers = Integer.parseInt(numPlayersField.getText());
            } catch (NumberFormatException e) {
                numPlayers = 0;
            }

            if (numPlayers < 2 || numPlayers > 6) {
                numPlayersField.setStyle("-fx-border-color: red;");
            } else {
                numPlayersField.setStyle(null);
            }
        });

        numPlayersField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                int numPlayers;
                try {
                    numPlayers = Integer.parseInt(numPlayersField.getText());
                } catch (NumberFormatException e) {
                    numPlayers = 0;
                }

                // Restrict number of players to between 2 and 6
                if (numPlayers < 2) {
                    numPlayers = 2;
                } else if (numPlayers > 6) {
                    numPlayers = 6;
                }

                numPlayersField.setText(String.valueOf(numPlayers));
            }
        });

        dialogVbox.getChildren().add(playerNamesVbox);

        // Confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {

            String gameName = gameNameField.getText();
            int numPlayers = Integer.parseInt(numPlayersField.getText());

            List<String> playerNames = new ArrayList<>();
            boolean validSetup = true;
            System.out.println("Er jeg idiot? ja kæmpe");

            // Add only the first player's name
            String player1Name = player1NameField.getText().trim();


            if (!player1Name.isEmpty()) {
                playerNames.add(player1Name);

            } else {
                validSetup = false;
            }

            System.out.println("Er jeg idiot? ja kæmpe mega meget fucking");

            if (validSetup) {
                System.out.println("Er jeg idiot? ja kæmpe mega meget fucking taber");

                // Create game object and send it to the server
                newGame = createNewGame(gameName, numPlayers);

                try {
                    createFirstPlayer(gameName, numPlayers, player1Name);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }


                    dialogStage.close();
                    //links to the new lobby
                    LobbyView2 lobby = new LobbyView2(roboRally);
                    lobby.show();


                /*
                HttpClientAsynchronousPost.addGame(newGame).thenAccept(game -> {
                    System.out.println("Er jeg idiot? ja kæmpe mega meget fucking taber er");
                    game = newGame;


                    Players newPlayer = createNewPlayer(player1Name, newGame);
                    addPlayer(newPlayer).thenAccept(player -> {
                        player = newPlayer;
                        System.out.println("Er jeg idiot? ja kæmpe mega meget fucking taber er måske");

                    });
                    System.out.println("Game setup successful!");

                    // Use Platform.runLater to update the UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        dialogStage.close();
                        //links to the new lobby
                        LobbyView2 lobby = new LobbyView2(roboRally);
                        lobby.show();
                    });
                }).exceptionally(ex -> {
                    ex.printStackTrace();
                    System.out.println("Error setting up game.");
                    return null;
                });*/

            } else {
                // Display error or prompt user to fill in all player names
                System.out.println("Please enter a name for Player 1.");
            }
        });

        dialogVbox.getChildren().add(confirmButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 400);
        dialogStage.setScene(dialogScene);
    }



    public Games createNewGame(String gameName, int numPlayers) {
        Games newGame = new Games();
        newGame.setGameName(gameName);
        newGame.setPlayersAmount(numPlayers);
        newGame.setJoinedPlayers(0); // Initially no players joined
        newGame.setGameStatus(0); // Initial game status

        Board board = new Board();
        board.setBoardId(5);
        board.setBoardName("Default Board");
        newGame.setBoard(board);
        return newGame;
    }


    private Players createNewPlayer(String playerName, Games game) {
        Players newPlayer = new Players();
        newPlayer.setPhaseStatus(false);
        newPlayer.setPlayerName(playerName);// Initial phase status
        newPlayer.setGame(game);


        return newPlayer;
    }



    public void show() {
        dialogStage.show();
    }
}
