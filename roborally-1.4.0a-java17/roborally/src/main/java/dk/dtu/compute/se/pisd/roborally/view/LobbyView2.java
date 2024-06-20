package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class LobbyView2 {

    private Stage lobbyStage;
    private GameController gameController;
    private RoboRally roboRally;

    public void AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;

    }


    private HttpClientAsynchronousPost httpClient = new HttpClientAsynchronousPost();

    private AppController appController;


    public LobbyView2() {

        lobbyStage = new Stage();



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
            Board board = new Board(8,8);
            gameController = new GameController(board);
            System.out.println("Starting Game successfully");
            gameController.startProgrammingPhase();
            roboRally.createBoardView(gameController);
        });



    }
    public void show() {
        lobbyStage.show();
    }


}
