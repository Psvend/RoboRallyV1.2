package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateGameView {

    private Stage dialogStage;
    private HttpClientAsynchronousPost httpClient = new HttpClientAsynchronousPost();

    public CreateGameView() {
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
        playerNamesVbox.getChildren().add(playerNamesLabel);

        // Button to dynamically add player name fields based on number of players
        numPlayersField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numPlayersField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            int numPlayers;
            try {
                numPlayers = Integer.parseInt(newValue);
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

            // Clear previous player name fields
            playerNamesVbox.getChildren().clear();
            playerNamesVbox.getChildren().add(playerNamesLabel);

            // Add new player name fields
            for (int i = 0; i < numPlayers; i++) {
                TextField playerNameField = new TextField();
                playerNameField.setPromptText("Player " + (i + 1) + " Name");
                playerNamesVbox.getChildren().add(playerNameField);
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
            for (var node : playerNamesVbox.getChildren()) {
                if (node instanceof TextField && !((TextField) node).getPromptText().equals("Player Names:")) {
                    String playerName = ((TextField) node).getText().trim();
                    if (!playerName.isEmpty()) {
                        playerNames.add(playerName);
                    } else {
                        validSetup = false;
                        break;
                    }
                }
            }

            if (validSetup) {
                // Create game object and send it to the server
                HttpClientAsynchronousPost.Games newGame = createNewGame(gameName, numPlayers, playerNames);
                HttpClientAsynchronousPost.addGame(newGame);
                System.out.println("Game setup successful!");
                dialogStage.close();
            } else {
                // Display error or prompt user to fill in all player names
                System.out.println("Please enter a name for each player.");
            }
        });

        dialogVbox.getChildren().add(confirmButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 400);
        dialogStage.setScene(dialogScene);
    }

    private HttpClientAsynchronousPost.Games createNewGame(String gameName, int numPlayers, List<String> playerNames) {
        HttpClientAsynchronousPost.Games newGame = new HttpClientAsynchronousPost.Games();
        newGame.setGameId(0);
        newGame.setGameName(gameName);
        newGame.setPlayersAmount(numPlayers);
        newGame.setJoinedPlayers(1); // Initially no players joined
        newGame.setGameStatus(0); // Initial game status

        HttpClientAsynchronousPost.Board board = new HttpClientAsynchronousPost.Board();
        board.setBoardId(5);
        board.setBoardName("Default Board");
        newGame.setBoard(board);

        // Add player names
        //for (int i = 0; i < playerNames.size(); i++) {
        //    newGame.getPlayerNames().put("Player " + (i + 1), playerNames.get(i));
        //}

        return newGame;
    }

    public void show() {
        dialogStage.show();
    }
}
