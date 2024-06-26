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
import dk.dtu.compute.se.pisd.roborally.controller.GearSpace;
import dk.dtu.compute.se.pisd.roborally.controller.Pitfall;
import dk.dtu.compute.se.pisd.roborally.controller.PushPanel;
import dk.dtu.compute.se.pisd.roborally.controller.RespawnPoint;
import dk.dtu.compute.se.pisd.roborally.controller.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.EnergyField;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;
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
public class Board extends Subject {

    public final int width;

    public final int height;

    private Integer gameId;

    private Space[][] spaces;

    private final List<Player> players = new ArrayList<>();
    
    private Player current;

    private Phase phase = INITIALISATION;

    private int step = 0;

    private int moves = 0;

    private boolean stepMode;

    private EnergyBank energyBank;

    private int winnerNumber = 0;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.energyBank = new EnergyBank(50);
        spaces = new Space[width][height];
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Space space = new Space(this, x, y);
                spaces[x][y] = space;
            }
        }
        initEnergySpaces();
        initPriorityAntenna();
        initWallSpaces();  
        initBelt(); 
        initGear();
        initCheckpointSpaces();
        initPanels();
        initPits();
        initRespawnPoint();
        this.stepMode = false;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        if (this.gameId == null) {
            this.gameId = gameId;
        } else {
            if (!this.gameId.equals(gameId)) {
                throw new IllegalStateException("A game with a set id may not be assigned a new id!");
            }
        }
    }

    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    public int getPlayersNumber() {
        return players.size();
    }

    public void addPlayer(@NotNull Player player) {
        if (player.board == this && !players.contains(player)) {
            players.add(player);
            notifyChange();
        }
    }

    public Player getPlayer(int i) {
        if (i >= 0 && i < players.size()) {
            return players.get(i);
        } else {
            return null;
        }
    }

    public Player getCurrentPlayer() {
        return current;
    }

    public void setCurrentPlayer(Player player) {
        if (player != this.current && players.contains(player)) {
            this.current = player;
            notifyChange();
        }
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        if (phase != this.phase) {
            this.phase = phase;
            notifyChange();
        }
    }

    /**
     * @author Nikolaj
     * @return moves
     * gets moves. used in the message.
     */
    public int getMoves() {
        return moves;
    }

    /**
     * @author Nikolaj
     * @param moves
     * sets moves, if they change.
     */
    public void setMoves(int moves) {
        if (moves != this.moves) {
            this.moves = moves;
            notifyChange();
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        if (step != this.step) {
            this.step = step;
            notifyChange();
        }
    }

    public boolean isStepMode() {
        return stepMode;
    }

    public void setStepMode(boolean stepMode) {
        if (stepMode != this.stepMode) {
            this.stepMode = stepMode;
            notifyChange();
        }
    }

    public int getPlayerNumber(@NotNull Player player) {
        if (player.board == this) {
            return players.indexOf(player);
        } else {
            return -1;
        }
    }


    /**
     * Returns the neighbour of the given space of the board in the given heading.
     * The neighbour is returned only, if it can be reached from the given space
     * (no walls or obstacles in either of the involved spaces); otherwise,
     * null will be returned.
     *
     * @param space the space for which the neighbour should be computed
     * @param heading the heading of the neighbour
     * @return the space in the given direction; null if there is no (reachable) neighbour
     */
    public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) {
        if (space.getWalls().contains(heading)) {
            return null;
        }

        int x = space.x;
        int y = space.y;
        switch (heading) {
            case SOUTH:
                y = (y + 1) % height;
                break;
            case WEST:
                x = (x + width - 1) % width;
                break;
            case NORTH:
                y = (y + height - 1) % height;
                break;
            case EAST:
                x = (x + 1) % width;
                break;
        }
        Heading reverse = Heading.values()[(heading.ordinal() + 2)% Heading.values().length];
        Space result = getSpace(x, y);
        if (result != null) {
            if (result.getWalls().contains(reverse)) {
                return null;
            }
        }
        return result;
    }

    public String getStatusMessage() {

        return "Phase: " + getPhase().name() +
                ", Player = " + getCurrentPlayer().getName() +
                ", Step: " + getStep() +
                ", Moves: " + getMoves();
    }

    public int getWinner(){
        return winnerNumber;
    }

    public void setWinner(int winnerNumber) {
        this.winnerNumber = winnerNumber;
    }



    /**
     * @author Petrine
     * @param getEnergyBank()
     * allows a energybank to work
     */
    public EnergyBank getEnergyBank() {
        return energyBank;
    }


    /**
     * @author Petrine
     * @param initEnergySpaces
     *
     * The four energy spaces defined and seen on the board. The functionalities can 
     * be found in EnergySpaces.java
     */
    private void initEnergySpaces() {
        EnergyField energyField1 = new EnergyField();
        EnergyField energyField2 = new EnergyField();
        EnergyField energyField3 = new EnergyField();
        EnergyField energyField4 = new EnergyField();

        spaces[7][1].setEnergyField(energyField1);
        spaces[1][6].setEnergyField(energyField2);
        spaces[2][7].setEnergyField(energyField3);;
        spaces[6][3].setEnergyField(energyField4);
    }

    /**
     * @auhor Daniel
     * @param initWallSpaces
     */

    private void initWallSpaces() {
        spaces[0][1] = new WallSpace(this, 0, 1, Heading.NORTH);
        spaces[2][3] = new WallSpace(this, 2, 3, Heading.SOUTH);
        spaces[5][6] = new WallSpace(this, 5, 6, Heading.EAST);
        spaces[7][7] = new WallSpace(this, 7, 7, Heading.WEST);
    }



    private void initPriorityAntenna() {

        spaces[4][7] = new PriorityAntenna(this, 4, 7);

    }

    public Space getPriorityAntenna() {
        return spaces[4][7];
    }

/**
 * @author Nikolaj
 * declares the Belts' attributes, ties them to spaces and initializes them.
 */
    public void initBelt() {
        ConveyorBelt conveyorBelt1 = new ConveyorBelt();
        ConveyorBelt conveyorBelt2 = new ConveyorBelt();
        ConveyorBelt conveyorBelt3 = new ConveyorBelt();
        ConveyorBelt conveyorBelt4 = new ConveyorBelt();
        ConveyorBelt conveyorBelt5 = new ConveyorBelt();
        ConveyorBelt conveyorBelt6 = new ConveyorBelt();
        ConveyorBelt conveyorBelt7 = new ConveyorBelt();
        ConveyorBelt conveyorBelt8 = new ConveyorBelt();

        conveyorBelt1.setBeltType(1);
        conveyorBelt2.setBeltType(2);
        conveyorBelt3.setBeltType(1);
        conveyorBelt4.setBeltType(2);
        conveyorBelt5.setBeltType(1);
        conveyorBelt6.setBeltType(2);
        conveyorBelt7.setBeltType(1);
        conveyorBelt8.setBeltType(2);

        conveyorBelt1.setHeading(SOUTH);
        conveyorBelt2.setHeading(NORTH);
        conveyorBelt3.setHeading(EAST);
        conveyorBelt4.setHeading(WEST);
        conveyorBelt5.setHeading(EAST);
        conveyorBelt6.setHeading(SOUTH);
        conveyorBelt7.setHeading(NORTH);
        conveyorBelt8.setHeading(WEST);

        spaces[1][1].setConveyorBelt(conveyorBelt1);
        spaces[1][2].setConveyorBelt(conveyorBelt1);
        spaces[1][3].setConveyorBelt(conveyorBelt1);
        spaces[1][4].setConveyorBelt(conveyorBelt1);

        spaces[4][1].setConveyorBelt(conveyorBelt2);
        spaces[4][2].setConveyorBelt(conveyorBelt2);
        spaces[4][3].setConveyorBelt(conveyorBelt2);
        spaces[4][4].setConveyorBelt(conveyorBelt2);

        spaces[1][5].setConveyorBelt(conveyorBelt5);
        spaces[2][5].setConveyorBelt(conveyorBelt3);
        spaces[3][5].setConveyorBelt(conveyorBelt3);
        spaces[4][5].setConveyorBelt(conveyorBelt7);

        spaces[1][0].setConveyorBelt(conveyorBelt6);
        spaces[2][0].setConveyorBelt(conveyorBelt4);
        spaces[3][0].setConveyorBelt(conveyorBelt4);
        spaces[4][0].setConveyorBelt(conveyorBelt8);

        spaces[1][0].getConveyorBelt().setTurnBelt("LEFT");
        spaces[1][5].getConveyorBelt().setTurnBelt("LEFT");
        spaces[4][0].getConveyorBelt().setTurnBelt("LEFT");
        spaces[4][5].getConveyorBelt().setTurnBelt("LEFT");
    }

    /**
     * @author Nikolaj
     * Declares  the gears' attributes, ties them to spaces and initializes them.
     */
    private void initGear() {
        GearSpace gearSpace1 = new GearSpace();
        GearSpace gearSpace2 = new GearSpace();

        spaces[7][0].setGearSpace(gearSpace1);
        spaces[7][6].setGearSpace(gearSpace2);

        gearSpace1.setGearType("LEFT");
        gearSpace2.setGearType("RIGHT");
    }

         /**
     * @author Louise
     * @param none
     *
     * Initialize the checkpointspace
     */
     private void initCheckpointSpaces () {
        Checkpoint checkpoint1 = new Checkpoint();
        Checkpoint checkpoint2 = new Checkpoint();

        spaces[0][7].setCheckPoint(checkpoint1);
        spaces[5][7].setCheckPoint(checkpoint2);

        checkpoint2.setCheckPointNumber(2);
        checkpoint1.setCheckPointNumber(1);
    }

    private void initPanels() {
        PushPanel pushPanel1 = new PushPanel();
        PushPanel pushPanel2 = new PushPanel();
        int [] pushRegister1 = {1,3,5};
        int [] pushRegister2 = {2, 4};

        spaces[6][7].setPushPanel(pushPanel1);
        spaces[7][5].setPushPanel(pushPanel2);

        pushPanel1.setHeading(NORTH);
        pushPanel2.setHeading(WEST);
        
        pushPanel1.setRegisters(pushRegister1);
        pushPanel2.setRegisters(pushRegister2);;
    }

    private void initPits() {
        Pitfall pitfall1 = new Pitfall();
        Pitfall pitfall2 = new Pitfall();

        spaces[0][3].setPitfall(pitfall1);
        spaces[0][5].setPitfall(pitfall2);
    }

    private void initRespawnPoint() {
        RespawnPoint respawnPoint1 = new RespawnPoint();
        respawnPoint1.setHeading(NORTH);

        spaces[3][3].setRespawnPoint(respawnPoint1);
    }
}
    




