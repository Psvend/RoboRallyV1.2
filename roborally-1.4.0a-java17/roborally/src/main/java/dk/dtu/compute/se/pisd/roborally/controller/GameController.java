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
package dk.dtu.compute.se.pisd.roborally.controller;
import dk.dtu.compute.se.pisd.roborally.model.EnergyBank;
import dk.dtu.compute.se.pisd.roborally.model.*;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;
    final public EnergyBank energyBank;
    public EnergySpace energySpace;
    public int moves = 0;
    public Command command;
    public ArrayList<Player> priorityPlayers = new ArrayList<>();

    public GameController(Board board) {
        this.board = board;
        this.energyBank = new EnergyBank(1);
        this.energySpace = new EnergySpace(board, 1, 1);

        
    }

    public int distanceToPriorityAntenna(@NotNull Player player){
        int spaceX = player.getSpace().x;
        int spaceY = player.getSpace().y;
        int antennaX = board.getPriorityAntenna().x;
        int antennaY = board.getPriorityAntenna().y;
        int distance = 0;
        if(spaceX < antennaX){
            distance = antennaX - spaceX;
            } else {
            distance = spaceX - antennaX;
        }
        if(spaceY < antennaY){
            distance = distance + (antennaY - spaceY);
        } else {
            distance = distance + (spaceY - antennaY);
        }
        return distance;
    }

    public ArrayList<Player> determiningPriority(){
        ArrayList<Player> playersTurn = new ArrayList<>();
        HashMap<Player, Integer> playerDistances = new HashMap<>();

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player currentPlayer = board.getPlayer(i);
            int distance = distanceToPriorityAntenna(currentPlayer);
            playerDistances.put(currentPlayer, distance);
        }

        List<Map.Entry<Player, Integer>> list = new ArrayList<>(playerDistances.entrySet());
        list.sort(Map.Entry.comparingByValue());

        for (Map.Entry<Player, Integer> entry : list) {
            playersTurn.add(entry.getKey());
        }

        return playersTurn;
    }


    public void moveForward(@NotNull Player player) {
        if (player.board == board) {
            Space space = player.getSpace();
            Heading heading = player.getHeading();
            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                try {
                    moveToSpace(player, target, heading);
                } catch (ImpossibleMoveException e) {
                    // we don't do anything here  for now; we just catch the
                    // exception so that we do no pass it on to the caller
                    // (which would be very bad style).
                }
            }
        }
    }
    /**
     * @author Louise
     * @param player
     * @return none
     */

    public void moveBackward(@NotNull Player player) {
        if (player.board == board) {
            Space space = player.getSpace();
            Heading heading = player.getHeading().next().next();
            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                try {
                    moveToSpace(player, target, heading);
                } catch (ImpossibleMoveException e) {
                    // we don't do anything here  for now; we just catch the
                    // exception so that we do no pass it on to the caller
                    // (which would be very bad style).
                }
            }
        }
    }
    
    /**
     * @author Louise
     * @param player
     * @return none
     */

    public void moveTwoForward(@NotNull Player player) {
        for (int i = 0; i < 2; i++) {
            moveForward(player);
        }
    }
    /**
     * @author Louise
     * @param player
     * @return none
     */

    public void moveThreeForward(@NotNull Player player) {
        for (int i = 0; i < 3; i++) {
            moveForward(player);
        }
    }

    /**
     * @author Louise
     * @param player
     * @return none
     */
    // TODO Assignment A3
    public void fastForward(@NotNull Player player) {
        for (int i = 0; i < 5; i++) {
            moveForward(player);
        }
    }

    /**
     * @author Louise
     * @param player
     * @return none
     */
    // TODO Assignment A3
    public void turnRight(@NotNull Player player) {
        Heading playerHeading = player.getHeading();
        player.setHeading(playerHeading.next());
    }

    /**
     * @author Louise
     * @param player
     * @return none
     */
    // TODO Assignment A3

    public void turnLeft(@NotNull Player player) {
        Heading playerHeading = player.getHeading();
        player.setHeading(playerHeading.prev());
    }

    /**
     * @author Louise
     * @param player
     * @return none
     */
    public void uTurn(@NotNull Player player) {
        for (int i = 1; i <= 2; i++){
            turnLeft(player);
        }
    }

    /**
     * @author Louise
     * @param player
     * @return none
     */
    public void again(@NotNull Player player){
        int step = board.getStep();
        if (step > 0 ) {
            CommandCard card = player.getProgramField(step - 1).getCard();
            if (card != null) {
                Command command = card.command;
                executeCommand(player, command);
            }
        }
    }

    void moveToSpace(@NotNull Player player, @NotNull Space space, @NotNull Heading heading) throws ImpossibleMoveException {
        assert board.getNeighbour(player.getSpace(), heading) == space; // make sure the move to here is possible in principle
        Player other = space.getPlayer();
        if (other != null){
            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                // XXX Note that there might be additional problems with
                //     infinite recursion here (in some special cases)!
                //     We will come back to that!
                moveToSpace(other, target, heading);

                // Note that we do NOT embed the above statement in a try catch block, since
                // the thrown exception is supposed to be passed on to the caller

                assert target.getPlayer() == null : target; // make sure target is free now
            } else {
                throw new ImpossibleMoveException(player, space, heading);
            }
        }
        player.setSpace(space);

        //tester om doAction er kaldet i gameControlleren 
        for(FieldAction action : space.getActions()) {
            action.doAction(this, space);
        }
    }

    public void moveCurrentPlayerToSpace(Space space) {
        Player currentPlayer =board.getCurrentPlayer();
        int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
        int step = board.getStep();
        space.setPlayer(currentPlayer);
        step++;
        if(nextPlayerNumber < board.getPlayersNumber()) {
            board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
            board.setStep(step);
        }else{
            board.setStep(step);
            board.setCurrentPlayer(board.getPlayer(0));
        }
    }

    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(priorityPlayers.get(0));
        board.setStep(0);
    }

    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    private void executeNextStep() {
        if (board.getPhase() == Phase.ACTIVATION && !priorityPlayers.isEmpty()) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                Player currentPlayer = priorityPlayers.get(0); // get the first player from the priority list
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    executeCommand(currentPlayer, command);

                    if(command.isInteractive()){
                        board.setPhase(Phase.PLAYER_INTERACTION);
                    }
                }

                priorityPlayers.remove(0); // remove the current player from the priority list

                if (priorityPlayers.isEmpty()) { // if the priority list is empty
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        priorityPlayers = determiningPriority(); // determine the priority for the next step
                        board.setCurrentPlayer(priorityPlayers.get(0));
                    } else {
                        startProgrammingPhase();
                    }
                } else {
                    board.setCurrentPlayer(priorityPlayers.get(0)); // set the next player from the priority list
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    /**
     * @author Natali
     * @param player,
     * @return none
     */

    // TODO Assignment A3
        public void leftOrRight(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            board.setPhase(Phase.ACTIVATION);
            executeCommand(player, command);
        }
    }



    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {


            switch (command) {
                case OPTION_LEFT_RIGHT:
                board.setPhase(Phase.PLAYER_INTERACTION);
                this.command = command;
                break;

                case FORWARD:
                    this.moveForward(player);
                    moves = moves +1;
                    board.setMoves(moves);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    moves = moves +1;
                    board.setMoves(moves);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    moves = moves +1;
                    board.setMoves(moves);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    moves = moves +1;
                    board.setMoves(moves);
                    break;
                /**
                 * @author Louise
                 * @param player
                 * @return none
                 */

                case MOVE_TWO:
                    this.moveTwoForward(player);
                    moves = moves +1;
                    board.setMoves(moves);
                    break;

                case MOVE_THREE:
                    this.moveThreeForward(player);
                    moves = moves +1;
                    board.setMoves(moves);
                    break;

                case U_TURN:
                    this.uTurn(player);
                    moves = moves +1;
                    board.setMoves(moves);
                    break;

                case BACKWARD:
                    this.moveBackward(player);
                    moves = moves +1;
                    board.setMoves(moves);
                    break;

                case AGAIN:
                    this.again(player);
                    moves = moves +1;
                    board.setMoves(moves);
                    break;


                default:
                    // DO NOTHING (for now)
            }
        }
    }

    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }


    public void startProgrammingPhase() {
        priorityPlayers= determiningPriority();
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(priorityPlayers.get(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }


    class ImpossibleMoveException extends Exception {

        private Player player;
        private Space space;
        private Heading heading;

        public ImpossibleMoveException(Player player, Space space, Heading heading) {
            super("Move impossible");
            this.player = player;
            this.space = space;
            this.heading = heading;
        }
    }

}
