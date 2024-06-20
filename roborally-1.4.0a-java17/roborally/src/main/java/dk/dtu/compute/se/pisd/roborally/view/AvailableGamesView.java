package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class AvailableGamesView {
    private Stage gamesStage;
    private List<Games> availableGames;
    private HttpClientAsynchronousPost httpClient = new HttpClientAsynchronousPost();

    public AvailableGamesView() throws Exception {
        gamesStage = new Stage();
        gamesStage.setTitle("Available Games");

        VBox dialogVbox = new VBox(10);
        dialogVbox.setPadding(new Insets(10, 10, 10, 10));


        httpClient.getAvailableGames().thenAccept(games -> {
            availableGames = games;

            // Use Platform.runLater to update the UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                for(Games game : availableGames) {
                    Button gameButton = new Button(game.getGameName());
                    dialogVbox.getChildren().add(gameButton);
                }
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            System.out.println("Error setting up game.");
            return null;
        });

        Scene gamesScene = new Scene(dialogVbox, 300, 400);
        gamesStage.setScene(gamesScene);
        gamesStage.show();
    }

    public void show() {
        gamesStage.show();
    }
}
