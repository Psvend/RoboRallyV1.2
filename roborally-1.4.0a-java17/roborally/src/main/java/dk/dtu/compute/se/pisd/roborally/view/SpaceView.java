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
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.GearSpace;
import dk.dtu.compute.se.pisd.roborally.controller.PushPanel;
import dk.dtu.compute.se.pisd.roborally.model.CheckpointSpace;
import dk.dtu.compute.se.pisd.roborally.model.EnergySpace;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.WallSpace;
import dk.dtu.compute.se.pisd.roborally.model.PriorityAntenna;
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
        if(space instanceof EnergySpace) {
            this.setId("energyspace-view");
        } else if (space instanceof WallSpace) {
            this.setId("wallspace-view");
        }else if(space instanceof PriorityAntenna){
            this.setId("priorityantenna-view");
        }else if (space.getGearSpace() instanceof GearSpace){
            if(space.getGearSpace().getGearType().equals("LEFT")){
                this.setId("gearspaceleft-view");
            } else if(space.getGearSpace().getGearType().equals("RIGHT")){
                this.setId("gearspaceright-view");
            }
        } else if(space instanceof CheckpointSpace) {
            this.setId("checkpoint-view");
        } else if(space.getPushPanel() instanceof PushPanel){
            if(space.getPushPanel().getHeading() == WEST){
                this.setStyle("-fx-background-color: bisque;");    
            } else if (space.getPushPanel().getHeading() == EAST) {
                this.setStyle("-fx-background-color: fuchsia;");    
            } else if (space.getPushPanel().getHeading() == SOUTH) {
                this.setStyle("-fx-background-color: gold;");    
            } else if(space.getPushPanel().getHeading() == NORTH){
                this.setStyle("-fx-background-color: plum;");    
            }
        } else if(space.getConveyorBelt() instanceof ConveyorBelt) {   //nikolaj
            if(space.getConveyorBelt().getBeltType()==1){
                if(space.getConveyorBelt().getTurnBelt().equals("LEFT")){
                    if(space.getConveyorBelt().getHeading().equals(NORTH)) {
                        this.setStyle("-fx-background-color: maroon;");
                    } else if(space.getConveyorBelt().getHeading().equals(SOUTH)){
                        this.setStyle("-fx-background-color: green;");
                    } else if(space.getConveyorBelt().getHeading().equals(EAST)){
                        this.setStyle("-fx-background-color: yellow;");
                    } else {
                        this.setStyle("-fx-background-color: teal;");
                    }     
                } else if(space.getConveyorBelt().getTurnBelt().equals("RIGHT")){
                    if(space.getConveyorBelt().getHeading().equals(NORTH)) {
                        this.setStyle("-fx-background-color: plum;");
                    } else if(space.getConveyorBelt().getHeading().equals(SOUTH)){
                        this.setStyle("-fx-background-color: turquoise;");
                    } else if(space.getConveyorBelt().getHeading().equals(EAST)){
                        this.setStyle("-fx-background-color: violet;");
                    } else {
                        this.setStyle("-fx-background-color: tan;");
                    } 
                } else {
                    this.setStyle("-fx-background-color: cyan;");
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
        this.getChildren().removeIf(child -> child instanceof Polygon);

        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = createPlayerIcon(player);
            this.getChildren().add(arrow); // Add player icon last to ensure it is on top
        }
    }

    private Polygon createPlayerIcon(Player player) {
        Polygon arrow = new Polygon(0.0, 0.0, 10.0, 20.0, 20.0, 0.0);
        try {
            arrow.setFill(Color.valueOf(player.getColor()));
        } catch (Exception e) {
            arrow.setFill(Color.MEDIUMPURPLE); // Default color in case of an error
        }
        arrow.setRotate((90 * player.getHeading().ordinal()) % 360);
        return arrow;
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            updatePlayer();
        }
    }
}
