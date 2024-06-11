package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class LobbyView extends Tab implements ViewObserver{


    private AppController appController;
    private VBox top;

    private VBox buttonPanel;

    //add label or button to vbox
    private Button startGameButton;



    public LobbyView(@NotNull Player player) {
        this.setText("Lobby");
        top = new VBox();
        this.setContent(top);


        buttonPanel = new VBox();
        top.getChildren().add(buttonPanel);

        startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> { appController.lobby(); });

        buttonPanel.getChildren().addAll(startGameButton);



        updateView(player);

    }

    @Override
    public void updateView(Subject subject) {
        if (subject instanceof Player) {
            Player player = (Player) subject;
            if (player.isReady()) {
                startGameButton.setText("Ready to Start");
            } else {
                startGameButton.setText("Start Game");
            }
        }


    }
}
