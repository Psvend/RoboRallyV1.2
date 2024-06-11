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
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GearSpace;
<<<<<<< HEAD
import dk.dtu.compute.se.pisd.roborally.controller.PitFall;
=======
import dk.dtu.compute.se.pisd.roborally.controller.PushPanel;
>>>>>>> main

import java.util.ArrayList;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Space extends Subject {

    private boolean pitfall = false;
    private ConveyorBelt ConveyorBelt;
    private GearSpace gearSpace;
    private PushPanel pushPanel;
    private Player player;
    private List<Heading> walls = new ArrayList<>();
    private List<FieldAction> actions = new ArrayList<>();
    public final Board board;
    public final int x;
    public final int y;

    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        player = null;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        Player oldPlayer = this.player;
        if (player != oldPlayer &&
                (player == null || board == player.board)) {
            this.player = player;
            if (oldPlayer != null) {
                // this should actually not happen
                oldPlayer.setSpace(null);
            }
            if (player != null) {
                player.setSpace(this);
            }
            notifyChange();
        }
    }

    public List<Heading> getWalls() {
        return walls;
    }

    public List<FieldAction> getActions() {
        return actions;
    }

    void playerChanged() {
        // This is a minor hack; since some views that are registered with the space
        // also need to update when some player attributes change, the player can
        // notify the space of these changes by calling this method.
        notifyChange();
    }

    /**
     * @author Nikolaj
     * @return ConveyorBelt
     * gets the value of the ConveyorBelt on the given space. This value is null, if nothing else is declared. 
     */
    public ConveyorBelt getConveyorBelt(){
        return ConveyorBelt;
    }

    /**
     * @author Nikolaj 
     * @param conveyorBelt
     * sets the conveyorBelt.
     */
    public void setConveyorBelt(ConveyorBelt conveyorBelt) {
        this.ConveyorBelt = conveyorBelt;
    }
   
    /**
     * @author Nikolaj
     * @return
     * gets the value of the gearSpace on the given space. This value is null, if nothing else is declared.
     */
    public GearSpace getGearSpace(){
        return gearSpace;
    }

    /**
     * @author Nikolaj
     * @param gearSpace
     * Sets the gearSpace.
     */
    public void setGearSpace(GearSpace gearSpace) {
        this.gearSpace = gearSpace;
    }

    public PushPanel getPushPanel(){
        return pushPanel;
    }

    public void setPushPanel(PushPanel pushPanel){
        this.pushPanel = pushPanel;
    }

     /**
     * @author Benjamin
     * @return Boolean
     */
    public boolean isFree(){
        return player==null;
        }
    
    public void setPitFall(){
        if(pitfall =! true) {
            pitfall = true;
        } else {
            pitfall = false;
        }
    }

    public boolean getPitFall(){
        return pitfall;
    }
}
