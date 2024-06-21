package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
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
    private List<Games> availableGames;
    private HttpClientAsynchronousPost httpClient = new HttpClientAsynchronousPost();

    public AvailableGamesView() throws Exception {
        gamesStage = new Stage();
        gamesStage.setTitle("Available Games and Player Name");

        VBox dialogVbox1 = new VBox();
        dialogVbox1.setPadding(new Insets(10, 10, 10, 10));
        dialogVbox1.getChildren().add(new Label("Player Name: "));

        VBox dialogVbox2 = new VBox();
        dialogVbox2.setPadding(new Insets(10, 10, 30, 10));
        dialogVbox2.getChildren().add(new Label("List of Available Games: "));

        //add the row to here
        GridPane root = new GridPane();
        root.add(dialogVbox1,0,0);
        root.add(dialogVbox2,1,0);


        //Adds the input box for the player name
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



        //Adds the list of available games
        httpClient.getAvailableGames().thenAccept(games -> {
            availableGames = games;

            // Use Platform.runLater to update the UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                for(Games game : availableGames) {
                    Button gameButton = new Button(game.getGameName());
                    dialogVbox2.getChildren().add(gameButton);
                }
            });



        }).exceptionally(ex -> {
            ex.printStackTrace();
            System.out.println("Error setting up game.");
            return null;
        });



        Scene gamesScene = new Scene(root, 300, 400);
        gamesStage.setScene(gamesScene);
        gamesStage.show();
    }

    public void show() {
        gamesStage.show();
    }
}
