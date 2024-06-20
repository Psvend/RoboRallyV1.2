/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally;

import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.view.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//Det er det meste layout er defineret da det er her Stage og Pane defineres 

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class RoboRally extends Application {

    private static final int MIN_APP_WIDTH = 800;
    private static final int MIN_APP_HEIGHT = 800;

    private Stage stage= new Stage();
    private BorderPane boardRoot= new BorderPane();

    private Button createGameButton;
    private Button joinGameButton;
    private Button exitButton;

    private AppController appController;



    @Override
    public void init() throws Exception {
        super.init();
    }
    /**
     * @author Louise
     * @param primaryStage
     * Added implementation of stylesheet (style.css)
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        // Load the CSS stylesheet

        Scene primaryScene = new Scene(new VBox(), MIN_APP_WIDTH, MIN_APP_HEIGHT);
        var temp = getClass().getResource("/style.css");

        //if (temp != null) {
            primaryScene.getStylesheets().add(temp.toExternalForm());
        //}


        AppController appController = new AppController(this);
        RoboRallyMenuBar menuBar = new RoboRallyMenuBar(appController);
        menuBar.setId("menuBar-view");  //styrer baggrund på første pop up
        boardRoot = new BorderPane();

        //test 1
        VBox vbox = new VBox(menuBar, boardRoot);
        vbox.setId("main-window");  //styrer baggrund på spillepladen


        vbox.setMinWidth(MIN_APP_WIDTH);
        primaryScene.setRoot(vbox);
        stage.setScene(primaryScene);
        stage.setTitle("RoboRally");
        stage.setOnCloseRequest(
                e -> {
                    e.consume();
                    appController.exit();
                });
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        Button createGameButton = new Button("Create Game");
        createGameButton.setId("create-game-button");

        Button joinGameButton = new Button("Join Game");
        joinGameButton.setId("join-game-button");

        Button exitButton = new Button("Exit");
        exitButton.setId("exit-button");


        // Set button actions
        createGameButton.setOnAction(e -> createGame());
        joinGameButton.setOnAction(e -> {
            try {
                joinGame();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        exitButton.setOnAction(e -> exitApplication());

        createGameButton.setMaxWidth(Double.MAX_VALUE);
        joinGameButton.setMaxWidth(Double.MAX_VALUE);
        exitButton.setMaxWidth(Double.MAX_VALUE);

        VBox vbButtons = new VBox();
        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(50, 20, 10, 20));
        vbButtons.getChildren().addAll(createGameButton, joinGameButton, exitButton);
        // Create a VBox layout and add the buttons
        boardRoot.getChildren().addAll(vbButtons);


    }
    private void createGame() {
        System.out.println("Create Game button clicked");
        CreateGameView dialog = new CreateGameView();
        dialog.show();
    }

    private void onCreateGame() {
        System.out.println("Game setup confirmed");
        // Implement game setup logic here
    }

    private void joinGame() throws Exception {
        System.out.println("Join Game button clicked");
        AvailableGamesView gamesList = new AvailableGamesView();
        gamesList.show();
       // LobbyView dialog = new LobbyView();

        // Add logic to handle joining a game
    }

    private void exitApplication() {
        System.out.println("Exit button clicked");
        // Add logic to handle exiting the application
        System.exit(0);
    }

    public void createBoardView(GameController gameController) {
        // if present, remove old BoardView
        boardRoot.getChildren().clear();



        if (gameController != null) {
            // create and add view for new board
            BoardView boardView = new BoardView(gameController);
            boardRoot.setCenter(boardView);
            boardRoot.setId("board-view");

        }

        stage.sizeToScene();
    }
    public void createLobbyView(Player player, Board board, GameController gameController){
        // if present, remove old BoardView
        boardRoot.getChildren().clear();


        if (player != null) {
            // create and add view for new board
            LobbyView lobbyView = new LobbyView(player, board, gameController);
            Node lobbyViewContent = lobbyView.getContent();
            boardRoot.setCenter(lobbyViewContent);
            boardRoot.setId("lobby-view");

        }

        stage.sizeToScene();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        // XXX just in case we need to do something here eventually;
        //     but right now the only way for the user to exit the app
        //     is delegated to the exit() method in the AppController,
        //     so that the AppController can take care of that.
    }

    public static void main(String[] args) {
        launch(args);
    }

}