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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class RoboRallyMenuBar extends MenuBar {

    private AppController appController;

    private Menu controlMenu;
    private Menu gameMenu;

    private MenuItem saveGame;

    private MenuItem newGame;
    private MenuItem JoinGame;

    private MenuItem OnlineGame;

    private MenuItem loadGame;

    private MenuItem stopGame;


    private MenuItem exitApp;

    public RoboRallyMenuBar(AppController appController) {
        this.appController = appController;
        
        controlMenu = new Menu("File");
        this.getMenus().add(controlMenu);

        gameMenu = new Menu("Game");
        this.getMenus().add(gameMenu);



        newGame = new MenuItem("Offline game");
        newGame.setOnAction( e -> this.appController.newGame());
        gameMenu.getItems().add(newGame);

        OnlineGame = new MenuItem("Online game");
        OnlineGame.setOnAction( e -> this.appController.onlineGame());
        gameMenu.getItems().add(OnlineGame);

        stopGame = new MenuItem("Stop Game");
        stopGame.setOnAction( e -> this.appController.stopGame());
        controlMenu.getItems().add(stopGame);

        saveGame = new MenuItem("Save Game");
        saveGame.setOnAction( e -> this.appController.saveGame());
        controlMenu.getItems().add(saveGame);

        JoinGame = new MenuItem("Join Game");
        JoinGame.setOnAction( e -> this.appController.lobby());
        gameMenu.getItems().add(JoinGame);

        loadGame = new MenuItem("Load Game");
        loadGame.setOnAction( e -> this.appController.loadGame());
        controlMenu.getItems().add(loadGame);

        exitApp = new MenuItem("Exit");
        exitApp.setOnAction( e -> this.appController.exit());
        controlMenu.getItems().add(exitApp);

        controlMenu.setOnShowing(e -> update());
        controlMenu.setOnShown(e -> this.updateBounds());

        gameMenu.setOnShowing(e -> update());
        gameMenu.setOnShowing(e -> this.updateBounds());
        update();
    }



    public void update() {
        if (appController.isGameRunning()) {
            newGame.setVisible(false);
            stopGame.setVisible(true);
            saveGame.setVisible(true);
            loadGame.setVisible(false);
        } else {
            newGame.setVisible(true);
            stopGame.setVisible(false);
            saveGame.setVisible(false);
            loadGame.setVisible(true);
        }
    }

}
