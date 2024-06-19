package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LobbyView2 {

    private Stage lobbyStage;


    private HttpClientAsynchronousPost httpClient = new HttpClientAsynchronousPost();

    public LobbyView2() {

        lobbyStage = new Stage();


        lobbyStage.setTitle("Lobby " + HttpClientAsynchronousPost.currentGame.getGameName());
        VBox dialogVbox = new VBox(10);
        dialogVbox.setPadding(new Insets(10, 10, 10, 10));





        Scene lobbyScene = new Scene(dialogVbox, 300, 400);
        lobbyStage.setScene(lobbyScene);

    }
    public void show() {
        lobbyStage.show();
    }


}
