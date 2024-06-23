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

import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
import dk.dtu.compute.se.pisd.roborally.client.Data.Players;
import dk.dtu.compute.se.pisd.roborally.client.Data.ProgCards;
import dk.dtu.compute.se.pisd.roborally.client.Data.Register;
import dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.PlayerView;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

import static dk.dtu.compute.se.pisd.roborally.client.HttpClientAsynchronousPost.player;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.EAST;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.NORTH;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.WEST;

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
    public int moves = 0;
    public Command command;
    public ArrayList<Player> priorityPlayers = new ArrayList<>();
    public ArrayList<Player> copyOfpriorityPlayers = new ArrayList<>();
    public Player interactivePlayer;
    private Map<Player, PlayerView> playerViews;
    public boolean wasActivated = false;
    public boolean wasOutside = false;
    public boolean hasCube = true;
    public boolean stop = false;
    public boolean powerCard = false;
    public ProgCards progCard;


    public GameController(Board board) {
        this.board = board;
        this.energyBank = board.getEnergyBank();
        this.playerViews = new HashMap<>();
    }


    public Player getVisiblePlayer() {
        int i = 0;
        while (player.getPlayerId() != HttpClientAsynchronousPost.playersList.get(i).getPlayerId()) {
            i++;
        }
        Player visiblePlayer = board.getPlayer(i);
        return visiblePlayer;
    }

    // Method to store PlayerView references
    public void setPlayerView(Player player, PlayerView playerView) {
        playerViews.put(player, playerView);
    }

    // Method to retrieve PlayerView for a given player
    public PlayerView getPlayerView(Player player) {
        return playerViews.get(player);
    }

    /**
     * @param moveForward
     * @author Daniel
     */
    public void moveForward(@NotNull Player player) {
        if (wasActivated) {
            wasActivated = false;
        }
        if (wasOutside) {
            wasOutside = false;
        }
        if (stop) {
            stop = false;
        }
        if (player.board == board) {
            Space currentSpace = player.getSpace();
            Heading heading = player.getHeading();

            if (isWallInfront(currentSpace, heading)) {
                return;
            }

            Space forwardSpace = board.getNeighbour(currentSpace, heading);

            if (forwardSpace != null) {
                if (isOutside(forwardSpace, player.getHeading())) {
                    if (findRespawnPoint().getPlayer() != null) {
                        pushPlayer(forwardSpace, player.getHeading());
                    }
                    player.setSpace(findRespawnPoint());
                    wasOutside = true;
                    return;
                }
                if (forwardSpace.getPlayer() != null) {
                    pushPlayer(forwardSpace, player.getHeading());
                    if (stop) {
                        stop = false;
                        return;
                    }
                    player.setSpace(forwardSpace);
                }
                if (forwardSpace.getPlayer() == null) {
                    player.setSpace(forwardSpace);
                    activatePitfall(player, player.getSpace());
                } else {
                    return;
                }
            }
        }
    }

    /**
     * @param player
     * @return none
     * @author Louise
     */

    public void moveBackward(@NotNull Player player) {
        if (player.board == board) {
            Space space = player.getSpace();
            Heading heading = player.getHeading().opposite();
            if (isWallInfront(space, heading)) {
                return;
            }

            Space target = board.getNeighbour(space, heading);

            if (target != null) {
                if (target.getPlayer() != null) {
                    pushPlayer(target, heading);
                }
                player.setSpace(target);
                activatePitfall(player, player.getSpace());
                if (isOutside(target, heading)) {
                    OutOfBoundsHandling(player);
                }
                if (wasActivated == true) {
                    wasActivated = false;
                }
            }
        }
    }

    /**
     * @param player
     * @return none
     * @author Louise
     */
    public void moveTwoForward(@NotNull Player player) {
        for (int i = 0; i < 2; i++) {
            moveForward(player);
            if ((wasActivated && wasOutside) || (wasActivated || wasOutside)) {
                booleanHandler(wasActivated, wasOutside);
                break;
            }
        }
    }

    /**
     * @param powerUp
     * @return none
     * <p>
     * Allows a player when power up card gets executed to add an energy cube to its reserve
     * @author Petrine
     */
    public void powerUp(@NotNull Player player) {
        powerCard = true;
        addEnergyCube(player, energyBank);
        powerCard = false;

        //her opdateres label views for energy reserve og banken
        getPlayerView(player).updateEnergyReserveLabel(player.getEnergyReserve());
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            getPlayerView(board.getPlayer(i)).updateBankLabel(energyBank.getBankStatus());
        }
    }

    /**
     * @param player
     * @return none
     * @author Louise
     */
    public void moveThreeForward(@NotNull Player player) {
        for (int i = 0; i < 3; i++) {
            moveForward(player);
            if ((wasActivated && wasOutside) || (wasActivated || wasOutside)) {
                booleanHandler(wasActivated, wasOutside);
                break;
            }
        }
    }

    /**
     * @param player
     * @return none
     * @author Louise
     */
    public void fastForward(@NotNull Player player) {
        for (int i = 0; i < 5; i++) {
            moveForward(player);
            if ((wasActivated && wasOutside) || (wasActivated || wasOutside)) {
                booleanHandler(wasActivated, wasOutside);
                break;
            }
        }
    }

    /**
     * @param player
     * @return none
     * @author Louise
     */
    public void turnRight(@NotNull Player player) {
        Heading playerHeading = player.getHeading();
        player.setHeading(playerHeading.next());
    }

    /**
     * @param player
     * @return none
     * @author Louise
     */
    public void turnLeft(@NotNull Player player) {
        Heading playerHeading = player.getHeading();
        player.setHeading(playerHeading.prev());
    }

    /**
     * @param player
     * @return none
     * @author Louise
     */
    public void uTurn(@NotNull Player player) {
        for (int i = 1; i <= 2; i++) {
            turnLeft(player);
        }
    }

    /**
     * @param player
     * @return none
     * @author Louise
     */
    public void again(@NotNull Player player) {
        int step = board.getStep();
        if (step > 0) {
            CommandCard card = player.getProgramField(step - 1).getCard();
            if (card != null) {
                Command command = card.command;
                executeCommand(player, command);
            }
        }
    }


    /**
     * @param player,command
     * @return none
     * @author Natali
     */
    public void leftOrRight(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            executeCommand(player, command);
            board.setPhase(Phase.ACTIVATION);
            int step = board.getStep();

            priorityPlayers.remove(0); // remove the current player from the priority list

            if (priorityPlayers.isEmpty()) { // if the priority list is empty

                step++; // go to the next card
                if (step < Player.NO_REGISTERS) {
                    makeProgramFieldsVisible(step); // make the next card visible
                    board.setStep(step);
                    priorityPlayers.addAll(copyOfpriorityPlayers); // determine the priority for the next round
                    Activator.getInstance().activateElements(board, this);  //nikolaj
                } else {
                    startProgrammingPhase();
                }

            }
            board.setCurrentPlayer(priorityPlayers.get(0));
        }
    }

    void moveToSpace(@NotNull Player player, @NotNull Space space, @NotNull Heading heading) throws ImpossibleMoveException {
        assert board.getNeighbour(player.getSpace(), heading) == space; // make sure the move to here is possible in principle
        Player other = space.getPlayer();
        if (other != null) {
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
        for (FieldAction action : space.getActions()) {
            action.doAction(this, space);
        }
    }

    public void moveCurrentPlayerToSpace(Space space) {
        Player currentPlayer = board.getCurrentPlayer();
        int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
        int step = board.getStep();
        space.setPlayer(currentPlayer);
        step++;
        if (nextPlayerNumber < board.getPlayersNumber()) {
            board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
            board.setStep(step);
        } else {
            board.setStep(step);
            board.setCurrentPlayer(board.getPlayer(0));
        }
    }


    /**
     * @param energyBank Checks if a player is on an energy space.
     *                   If the that is the case, a cube is added to the players reserve by addEnergyCube(),
     *                   and the labels showing the reserve and bank
     * @author Petrine & Louise
     */
    public void isPlayerOnEnergySpace(EnergyBank energyBank) {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            Space space = player.getSpace();
            if (space != null) {
                if (space.getEnergyField() != null) {
                    if (space.getEnergyField().hasEnergyCube()) {
                        if (energyBank.getBankStatus() > 0) {    //tjekker om banken er fuld
                            addEnergyCube(player, energyBank);      //tilføjer en cube til en spillers reserve
                            getPlayerView(player).updateEnergyReserveLabel(player.getEnergyReserve());
                            getPlayerView(board.getPlayer(i)).updateBankLabel(energyBank.getBankStatus());
                        }
                        hasCube = true;
                        space.getEnergyField().setEnergyCube(hasCube);
                    }
                } else {
                }
            }
        }
        restockEnergyField();
    }


    public void activateCheckpointSpace() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            Space space = player.getSpace();
            if (space != null) {
                if (space.getCheckpoint() != null) {
                    if (player.getTokens().contains(space.getCheckpoint().getNumber())) {
                        if (board.getStep() == 5 && player.getTokens().size() == findTotalCheckpoints()) {
                            board.setWinner(board.getPlayerNumber(player) + 1);
                            board.setPhase(Phase.RESULT);
                        }
                    } else {
                        int checkpointToken = space.getCheckpoint().getNumber();
                        List<Integer> playerTokens = player.getTokens();
                        playerTokens.add(checkpointToken);
                        player.setTokens(playerTokens);
                        getPlayerView(player).updateCheckPointTokensLabel(playerTokens);
                        if (board.getStep() == 5 && player.getTokens().size() == findTotalCheckpoints()) {
                            board.setWinner(board.getPlayerNumber(player) + 1);
                            board.setPhase(Phase.RESULT);
                        }
                    }
                }
            }
        }
    }


    /**
     * @author Petrine & Louise
     * Allows a player to have its own energyreserve, that will get updated every time
     * a cube gets added to it.
     */
    public void addEnergyCube(Player player, EnergyBank energyBank) {   //tilføjelse af en cube hvis ønsket. Kaldes når robot lander på energy space el. trækker power up kort        
        Integer playerBank = player.getEnergyReserve();
        energyBank = board.getEnergyBank();
        Integer energyBankStatus = energyBank.getBankStatus();
        Space energySpace = player.getSpace();
        if (energySpace.getEnergyField() != null) {
            if (energySpace.getEnergyField().hasEnergyCube() && energyBank.takeEnergyCube()) {   //hvis banken er fuld tilføjes en cube til reserven
                // TILFØJET AF LOUISE
                playerBank++;
                player.setEnergyReserve(playerBank);
                energyBankStatus--;
                energyBank.setEnergyBank(energyBankStatus);
            }
        } else if (powerCard) {
            playerBank++;
            player.setEnergyReserve(playerBank);
            energyBankStatus--;
            energyBank.setEnergyBank(energyBankStatus);
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
        HttpClientAsynchronousPost.addReristers(playerRegisters());
        HttpClientAsynchronousPost.changePlayerPhaseStatus(updatePlayer(1)).thenAccept(
                player -> {
                    System.out.println("Player phase status changed:" + player);
                }
        );

        System.out.println("Registers added:" + playerRegisters());

    }

    public void executePrograms() {
        HttpClientAsynchronousPost.countPlayersWithPhaseStatusOne(HttpClientAsynchronousPost.currentGame.getGameId()).thenAccept(
                amount -> {
                    if (HttpClientAsynchronousPost.phaseDone == player.getGameID().getPlayersAmount()) {
                        HttpClientAsynchronousPost.getPlayersRegisters(HttpClientAsynchronousPost.currentGame.getGameId()).thenAccept(
                                registers -> {
                                    System.out.println("Registers received:" + registers);
                                }
                        );
                        board.setStepMode(false);
                        continuePrograms();
                        HttpClientAsynchronousPost.changePlayerPhaseStatus(updatePlayer(0)).thenAccept(
                                status -> {
                                    System.out.println("Player phase status changed:" + status);
                                }
                        );
                    } else {
                        System.out.println("Not all players are done");
                    }
                }
       );

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
                //tilføj hvis kort er et powerUp kort, så forbliver man på samme felt? 
                if (card != null) {
                    Command command = card.command;
                    executeCommand(currentPlayer, command);

                    if (command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;

                    }
                }

                priorityPlayers.remove(0); // remove the current player from the priority list

                if (priorityPlayers.isEmpty()) { // if the priority list is empty
                    step++; // go to the next card
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step); // make the next card visible
                        board.setStep(step);
                        priorityPlayers.addAll(copyOfpriorityPlayers); // determine the priority for the next round
                        Activator.getInstance().activateElements(board, this); //initializes the boardElements
                    } else {
                        board.setStep(step);
                        Activator.getInstance().activateElements(board, this);
                        startProgrammingPhase();
                    }


                }
                board.setCurrentPlayer(priorityPlayers.get(0));

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
     * @param player
     * @return none
     * @author Louise
     */
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            switch (command) {

                case FORWARD:
                    this.moveForward(player);
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;

                case RIGHT:
                    this.turnRight(player);
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;

                case LEFT:
                    this.turnLeft(player);
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;

                case FAST_FORWARD:
                    this.fastForward(player);
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;

                case MOVE_TWO:
                    this.moveTwoForward(player);
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;

                case MOVE_THREE:
                    this.moveThreeForward(player);
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;

                case U_TURN:
                    this.uTurn(player);
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;

                case BACKWARD:
                    this.moveBackward(player);
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;

                case AGAIN:
                    this.again(player);
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;

                case POWERUP:
                    this.powerUp(player);
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;

                case OPTION_LEFT_RIGHT:
                    //board.setPhase(Phase.PLAYER_INTERACTION);
                    this.command = command;
                    moves = moves + 1;
                    board.setMoves(moves);
                    break;


                default:
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
        priorityPlayers.clear(); // Clear the list before recalculating
        priorityPlayers = determiningPriority();
        copyOfpriorityPlayers.clear(); // Clear the list before recalculating
        copyOfpriorityPlayers.addAll(priorityPlayers);
        priorityPlayers.clear(); // Clear the list before recalculating
        priorityPlayers = determiningPriority();
        copyOfpriorityPlayers.clear(); // Clear the list before recalculating
        copyOfpriorityPlayers.addAll(priorityPlayers);
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(priorityPlayers.get(0));
        board.setCurrentPlayer(priorityPlayers.get(0));
        board.setStep(0);


        Player player = getVisiblePlayer();
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
        //}
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


    /**
     * @return playersTurn
     * @author Natali
     */

    public ArrayList<Player> determiningPriority() {
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


    /**
     * @param player
     * @return distance
     * @author Natali
     */
    public int distanceToPriorityAntenna(@NotNull Player player) {
        int spaceX = player.getSpace().x;
        int spaceY = player.getSpace().y;
        int antennaX = board.getPriorityAntenna().x;
        int antennaY = board.getPriorityAntenna().y;
        int distance = 0;
        if (spaceX < antennaX) {
            distance = antennaX - spaceX;
        } else {
            distance = spaceX - antennaX;
        }
        if (spaceY < antennaY) {
            distance = distance + (antennaY - spaceY);
        } else {
            distance = distance + (spaceY - antennaY);
        }
        return distance;
    }

    /**
     * @throws ImpossibleMoveException activates conveyor belts and moves players standing on them. Uses manipulateSpace() to determine next space to move the player to. Uses setSpace() to move the player.
     * @author Nikolaj
     */
    public void activateConveyorBelt() throws ImpossibleMoveException {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            int moveAmount;
            if (player.getSpace() != null) {
                if (player.getSpace().getConveyorBelt() != null) {

                    if (player.getSpace().getConveyorBelt().getBeltType() == 1) {
                        moveAmount = 1;
                    } else if (player.getSpace().getConveyorBelt().getBeltType() == 2) {
                        moveAmount = 2;
                    } else {
                        moveAmount = 0;
                    }

                    for (int c = moveAmount; c > 0; c--) {
                        Heading heading = player.getSpace().getConveyorBelt().getHeading();
                        Space target = null;

                        if (heading != null) {
                            target = manipulateSpace(1, heading, player.getSpace().x, player.getSpace().y);
                        } else {
                            return;
                        }
                        if (target == null) return;
                        if (target.getConveyorBelt() == null) {
                            if (target.getPlayer() == null) {
                                player.setSpace(target);
                                moveAmount = 0;
                            } else {
                                moveAmount = 0;
                            }
                        } else if (target.getConveyorBelt().getBeltType() == 1 || target.getConveyorBelt().getBeltType() == 2) {
                            player.setSpace(target);
                        }
                    }
                }
            }
        }
    }


    /**
     * @param OFFSET
     * @param heading
     * @param x
     * @param y
     * @return
     * @throws ImpossibleMoveException Used for manipulating spaces without using the players heading. Which is a big advantage in many cases.
     * @author Nikolaj
     */
    protected Space manipulateSpace(int OFFSET, Heading heading, int x, int y) throws ImpossibleMoveException {
        Space space = null;
        switch (heading) {
            case NORTH:
                if (OFFSET < 0 && y < board.height - 1) {
                    space = board.getSpace(x, y + Math.abs(OFFSET));
                } else if (y >= OFFSET && OFFSET > 0) {
                    space = board.getSpace(x, y - OFFSET);
                } else {
                    throw new ImpossibleMoveException(null, space, heading);
                }
                break;

            case SOUTH:
                if (OFFSET < 0 && y > 0) {
                    space = board.getSpace(x, y - Math.abs(OFFSET));
                } else if (y < board.height - OFFSET && OFFSET >= 0) {
                    space = board.getSpace(x, y + OFFSET);
                } else {
                    throw new ImpossibleMoveException(null, space, heading);
                }
                break;

            case EAST:
                if (OFFSET < 0 && x > 0) {
                    space = board.getSpace(x - Math.abs(OFFSET), y);
                } else if (x < board.width - OFFSET && OFFSET >= 0) {
                    space = board.getSpace(x + OFFSET, y);
                } else {
                    throw new ImpossibleMoveException(null, space, heading);
                }
                break;

            case WEST:
                if (OFFSET < 0 && x < board.width - 1) {
                    space = board.getSpace(x + Math.abs(OFFSET), y);
                } else if (x >= OFFSET && OFFSET > 0) {
                    space = board.getSpace(x - OFFSET, y);
                } else {
                    throw new ImpossibleMoveException(null, space, heading);
                }
                break;
        }
        if (space == null) {
            throw new ImpossibleMoveException(null, space, heading);
        }
        return space;
    }

    /**
     * @author Nikolaj
     * activates gear spaces and turns every player standing on them.
     */
    public void activateGearSpaces() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            Space space = player.getSpace();
            if (space != null) {
                if (space.getGearSpace() != null) {
                    if (space.getGearSpace().getGearType().equals("LEFT")) {
                        turnLeft(player);
                    } else if (space.getGearSpace().getGearType().equals("RIGHT")) {
                        turnRight(player);
                    }
                }
            }
        }
    }

    public void activatePushPanels() throws ImpossibleMoveException {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            Space space = player.getSpace();
            if (space != null) {
                if (space.getPushPanel() != null) {
                    int[] currentRegisters = space.getPushPanel().getRegisters();
                    for (int c = 0; c <= currentRegisters.length - 1; c++) {
                        if (board.getStep() == currentRegisters[c]) {
                            Heading heading = space.getPushPanel().getHeading();
                            Space target = null;
                            if (heading != null) {
                                target = manipulateSpace(1, heading, player.getSpace().x, player.getSpace().y);

                            } else {
                            }
                            if (target == null) {
                                return;
                            }
                            if (target.getPlayer() == null) {
                                player.setSpace(target);
                            }
                        }
                    }
                }
            }
        }
    }

    public void activatePitfall(Player player, Space space) {
        if (space.getPitfall() instanceof Pitfall) {
            OutOfBoundsHandling(player);
            wasActivated = true;
        }
    }

    public boolean isOutside(Space space, @NotNull Heading heading) {
        if (space.y == 0 && heading == SOUTH) {
            return true;
        }
        if (space.y == board.height - 1 && heading == NORTH) {
            return true;
        }
        if (space.x == 0 && heading == EAST) {
            return true;
        }
        if (space.x == board.width - 1 && heading == WEST) {
            return true;
        } else {
            return false;
        }
    }

    public Space findRespawnPoint() {
        Space respawnPoint;
        for (int i = 0; i <= board.width - 1; i++) {
            for (int j = 0; j <= board.height - 1; j++) {
                respawnPoint = board.getSpace(i, j);
                if (respawnPoint.getRespawnPoint() instanceof RespawnPoint) {
                    return respawnPoint;
                }
            }
        }
        return respawnPoint = board.getSpace(0, 0);
    }

    public int findTotalCheckpoints() {
        Space checkpoint;
        int totalCheckpoints = 0;
        for (int i = 0; i <= board.width - 1; i++) {
            for (int j = 0; j <= board.height - 1; j++) {
                checkpoint = board.getSpace(i, j);
                if (checkpoint.getCheckpoint() instanceof Checkpoint) {
                    totalCheckpoints = totalCheckpoints + 1;
                }
            }
        }
        return totalCheckpoints;
    }

    public void restockEnergyField() {
        Space energyField;
        if (board.getStep() == 5) {
            for (int i = 0; i <= board.width - 1; i++) {
                for (int j = 0; j <= board.height - 1; j++) {
                    energyField = board.getSpace(i, j);
                    if (energyField.getEnergyField() instanceof EnergyField) {
                        if (!energyField.getEnergyField().hasEnergyCube()) {
                            energyField.getEnergyField().setEnergyCube(false);
                        }
                    }
                }
            }
        }
    }

    public void pushPlayer(Space space, Heading heading) {
        booleanHandler(wasActivated, wasOutside);

        Player playerBeingPushed = space.getPlayer();

        if (isWallInfront(space, heading)) {
            stop = true;
            return;
        }

        Space pushSpace = board.getNeighbour(space, heading);

        if (pushSpace != null) {
            Heading backwardHeading = heading.opposite();
            if (pushSpace.getPlayer() != null) {
                pushPlayer(pushSpace, heading);
            }
            if (isOutside(pushSpace, heading)) {
                OutOfBoundsHandling(playerBeingPushed);
                return;
            }
            if (playerBeingPushed == null) {
                try {
                    respawnPush(findRespawnPoint().getPlayer());
                } catch (ImpossibleMoveException e) {
                    new ImpossibleMoveException(findRespawnPoint().getPlayer(), findRespawnPoint(), backwardHeading);
                }
                return;
            }
            if (stop) {
                return;
            }
            playerBeingPushed.setSpace(pushSpace);
            activatePitfall(playerBeingPushed, playerBeingPushed.getSpace());
            wasActivated = false;
        }
    }

    public void respawnPush(@NotNull Player player) throws ImpossibleMoveException {
        Heading respawnHeading = findRespawnPoint().getRespawnPoint().getHeading();
        Space respawnSpace = player.getSpace();
        Space target = null;

        if (respawnHeading != null) {
            target = manipulateSpace(1, respawnHeading, respawnSpace.x, respawnSpace.y);
        } else {
            return;
        }
        if (target.equals(null)) {
            return;
        }
        if (target.getPlayer() != null) {
            respawnPush(target.getPlayer());
            player.setSpace(target);
        } else {
            player.setSpace(target);
        }
    }

    public Boolean isWallInfront(@NotNull Space space, @NotNull Heading heading) {
        if (space != null && space instanceof WallSpace) {
            WallSpace wallSpace = (WallSpace) space;

            if (wallSpace.getHeading() == heading && wallSpace.hasWall()) {
                return true;
            }
        }
        Space pushSpace = board.getNeighbour(space, heading);

        if (pushSpace != null && pushSpace instanceof WallSpace) {
            Heading backwardHeading = heading.opposite();
            WallSpace wallSpace2 = (WallSpace) pushSpace;

            if (wallSpace2.getHeading() == backwardHeading && wallSpace2.hasWall()) {
                return true;
            }

        }
        return false;
    }

    public void booleanHandler(Boolean wasActivated, Boolean wasOutside) {
        wasActivated = false;
        wasOutside = false;
    }

    public void OutOfBoundsHandling(Player player) {
        if (findRespawnPoint().getPlayer() != null) {
            try {
                respawnPush(findRespawnPoint().getPlayer());
            } catch (ImpossibleMoveException e) {
                new ImpossibleMoveException(findRespawnPoint().getPlayer(), findRespawnPoint(), findRespawnPoint().getRespawnPoint().getHeading());
            }
        }
        player.setSpace(findRespawnPoint());
    }

    public void updateEnergy() {
        Integer playerNo = board.getPlayersNumber();
        EnergyBank energyBank = board.getEnergyBank();
        for (int i = 0; i < playerNo; i++) {
            Player player = board.getPlayer(i);
            getPlayerView(player).updateEnergyReserveLabel(player.getEnergyReserve());
            getPlayerView(player).updateBankLabel(energyBank.getBankStatus());
        }
    }

    private List<Register> playerRegisters() {
        List<Register> registers = new ArrayList<>();

        // Initialize progCard before using it
        this.progCard = new ProgCards(0, "", "", "");

        for (int i = 0; i < getVisiblePlayer().NO_REGISTERS; i++) {
            ProgCards progCardsInRegister = new ProgCards(0, "", "", "");
            CommandCard card = getVisiblePlayer().getProgramField(i).getCard();
            if (card != null) {
                int j = 0;
                if (j < progCard.progCardsList().size()) {
                    while (!progCard.progCardsList().get(j).getCardType().equals(card.command.displayName)) {
                        j++;
                        if (j >= progCard.progCardsList().size()) {
                            break;
                        }
                    }
                }
                progCardsInRegister = (new ProgCards(progCardsInRegister.progCardsList().get(j).getCardId(), "", "Not Executed", card.command.displayName));
            }

            Register register = new Register();
            register.setPlayerId(player);
            register.setCardId(progCardsInRegister);
            register.setRegisterNumber(i);
            register.setRegisterStatus(0);
            registers.add(register);
        }
        return registers;
    }

    private Players updatePlayer(int phase_status) {
        Players updatedPlayer = new Players();
        updatedPlayer.setPlayerId(player.getPlayerId());
        updatedPlayer.setPhaseStatus(phase_status);
        return updatedPlayer;
    }



}
