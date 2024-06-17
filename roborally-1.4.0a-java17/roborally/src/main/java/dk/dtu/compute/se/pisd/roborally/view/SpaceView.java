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

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.EnergyField;
import dk.dtu.compute.se.pisd.roborally.controller.GearSpace;
import dk.dtu.compute.se.pisd.roborally.controller.Pitfall;
import dk.dtu.compute.se.pisd.roborally.controller.PushPanel;
import dk.dtu.compute.se.pisd.roborally.controller.RespawnPoint;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.WallSpace;
import dk.dtu.compute.se.pisd.roborally.model.PriorityAntenna;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.EAST;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.NORTH;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.WEST;


/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */

public class SpaceView extends StackPane implements ViewObserver {

    public final Space space;
    final public static int SPACE_HEIGHT = 50; // 75;
    final public static int SPACE_WIDTH = 50; // 75;


    public SpaceView(@NotNull Space space) {
        
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        this.setId("space-view");


        
        //updated by Petrine for each different type of space accounted
        if(space.getEnergyField() instanceof EnergyField) {
            this.setId("energyspace-view");
        } else if (space instanceof WallSpace) {
            WallSpace wallSpace = (WallSpace) space;
            if(wallSpace.getHeading().equals(NORTH)){
                this.setId("wallspaceNORTH");
            } else if(wallSpace.getHeading().equals(SOUTH)){
                this.setId("wallspaceSOUTH");
            } else if(wallSpace.getHeading().equals(EAST)){
                this.setId("wallspaceEAST");
            } else if(wallSpace.getHeading().equals(WEST)){
                this.setId("wallspaceWEST");
            }
        }else if(space instanceof PriorityAntenna){
            this.setId("priorityantenna-view");
        }else if (space.getGearSpace() instanceof GearSpace){
            if(space.getGearSpace().getGearType().equals("LEFT")){
                this.setId("gearspaceleft-view");
            } else if(space.getGearSpace().getGearType().equals("RIGHT")){
                this.setId("gearspaceright-view");
            }
        } else if(space.getCheckpoint() instanceof Checkpoint) {
            if(space.getCheckpoint().getNumber() == 1) {
                this.setId("checkpoint1");
            } else if(space.getCheckpoint().getNumber() == 2) {
                this.setId("checkpoint2");
            } else if(space.getCheckpoint().getNumber() == 3) {
                this.setId("checkpoint3");
            } else if(space.getCheckpoint().getNumber() == 4) {
                this.setId("checkpoint4");
            } else if(space.getCheckpoint().getNumber() == 5) {
                this.setId("checkpoint5");
            } else if(space.getCheckpoint().getNumber() == 6) {
                this.setId("checkpoint6");
            }
        } else if (space.getPitfall() instanceof Pitfall) {
            this.setId("pitfall");
        } else if (space.getRespawnPoint() instanceof RespawnPoint) {
            this.setId("respawnPoint");
        } else if (space.y == 0 && space.x == 0) {
            this.setId("startField");
        } else if(space.getPushPanel() instanceof PushPanel){
            if(space.getPushPanel().getHeading() == WEST){
                if(space.getPushPanel().getRegisters().length == 3){
                    this.setId("pushUnevenWEST");
                } else if(space.getPushPanel().getRegisters().length == 2) {
                    this.setId("pushEvenWEST");
                }   
            } else if (space.getPushPanel().getHeading() == EAST) {
                if(space.getPushPanel().getRegisters().length == 3){
                    this.setId("pushUnevenEAST");
                } else if(space.getPushPanel().getRegisters().length == 2) {
                    this.setId("pushEvenEAST");
                }     
            } else if (space.getPushPanel().getHeading() == SOUTH) {
                if(space.getPushPanel().getRegisters().length == 3){
                    this.setId("pushUnevenSOUTH");
                } else if(space.getPushPanel().getRegisters().length == 2) {
                    this.setId("pushEvenSOUTH");
                }      
            } else if(space.getPushPanel().getHeading() == NORTH){
                if(space.getPushPanel().getRegisters().length == 3){
                    this.setId("pushUnevenNORTH");
                } else if(space.getPushPanel().getRegisters().length == 2) {
                    this.setId("pushEvenNORTH");
                }     
            }
        } else if(space.getConveyorBelt() instanceof ConveyorBelt) {   //nikolaj
            if(space.getConveyorBelt().getBeltType()==1){
                if(space.getConveyorBelt().getTurnBelt().equals("LEFT")){
                    if(space.getConveyorBelt().getHeading().equals(NORTH)) {
                        this.setId("greenTurnLeftNORTH");
                    } else if(space.getConveyorBelt().getHeading().equals(SOUTH)){
                        this.setId("greenTurnLeftSOUTH");
                    } else if(space.getConveyorBelt().getHeading().equals(EAST)){
                        this.setId("greenTurnLeftEAST");
                    } else {
                        this.setId("greenTurnLeftWEST");
                    }     
                } else if(space.getConveyorBelt().getTurnBelt().equals("RIGHT")){
                    if(space.getConveyorBelt().getHeading().equals(NORTH)) {
                        this.setId("greenTurnRightNORTH");
                    } else if(space.getConveyorBelt().getHeading().equals(SOUTH)){
                        this.setId("greenTurnRightSOUTH");
                    } else if(space.getConveyorBelt().getHeading().equals(EAST)){
                        this.setId("greenTurnRightEAST");
                    } else {
                        this.setId("greenTurnRightWEST");
                    } 
                } else {
                    if(space.getConveyorBelt().getHeading().equals(NORTH)){
                        this.setId("greenNORTH");
                    } else if(space.getConveyorBelt().getHeading().equals(SOUTH)){
                        this.setId("greenSOUTH");
                    } else if(space.getConveyorBelt().getHeading().equals(EAST)){
                        this.setId("greenEAST");
                    } else if(space.getConveyorBelt().getHeading().equals(WEST)){
                        this.setId("greenWEST");
                    }
                }
            } else if (space.getConveyorBelt().getBeltType()==2){
                if(space.getConveyorBelt().getTurnBelt().equals("LEFT")){
                    if(space.getConveyorBelt().getHeading().equals(NORTH)) {
                        this.setId("blueTurnLeftNORTH");
                    } else if(space.getConveyorBelt().getHeading().equals(SOUTH)){
                        this.setId("blueTurnLeftSOUTH");
                    } else if(space.getConveyorBelt().getHeading().equals(EAST)){
                        this.setId("blueTurnLeftEAST");
                    } else {
                        this.setId("blueTurnLeftWEST");
                    } 
                } else if (space.getConveyorBelt().getTurnBelt().equals("RIGHT")) {
                    if(space.getConveyorBelt().getHeading().equals(NORTH)) {
                        this.setId("blueTurnRightNORTH");
                    } else if(space.getConveyorBelt().getHeading().equals(SOUTH)){
                        this.setId("blueTurnRightSOUTH");
                    } else if(space.getConveyorBelt().getHeading().equals(EAST)){
                        this.setId("blueTurnRightEAST");
                    } else {
                        this.setId("blueTurnRightWEST");
                    }
                } else {
                    if(space.getConveyorBelt().getHeading().equals(NORTH)){
                        this.setId("blueNORTH");
                    } else if(space.getConveyorBelt().getHeading().equals(SOUTH)){
                        this.setId("blueSOUTH");
                    } else if(space.getConveyorBelt().getHeading().equals(EAST)){
                        this.setId("blueEAST");
                    } else if(space.getConveyorBelt().getHeading().equals(WEST)){
                        this.setId("blueWEST");
                    }
                }
            }    
        } else {
            this.setId("space-view");
        }
        
        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }


    private void updatePlayer() {
        // Clear only previous player icons, leave the background intact
        this.getChildren().removeIf(child -> child instanceof ImageView);

        Player player = space.getPlayer();
        if (player != null) {
            ImageView playerRobot = createPlayerIcon(player);
            this.getChildren().add(playerRobot); // Add player icon last to ensure it is on top
            System.out.println("check");
        }
    }

    private ImageView createPlayerIcon(Player player) {
        ImageView playerRobot = new ImageView();
        try {
                playerRobot.setId("player1");
                playerRobot.set
        } catch (Exception e) {
            System.out.println("didnt work");
        }
        playerRobot.setRotate((90 * player.getHeading().ordinal()) % 360);
        return playerRobot;
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            updatePlayer();
        }
    }
}
