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

import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.PlayerView;
import org.jetbrains.annotations.NotNull;

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
    public ArrayList<Player> copyOfpriorityPlayers = new ArrayList<>();
    public Player interactivePlayer;
    private Map<Player, PlayerView> playerViews;
    public boolean wasActivated = false;

    public GameController(Board board) {
        this.board = board;
        this.energySpace = new EnergySpace(board, 1, 1);
        this.energyBank = board.getEnergyBank();
        this.playerViews = new HashMap<>();
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
     * @author Daniel
     * @param moveForward
     * 
     * 
     */
    public void moveForward(@NotNull Player player) {
    if (player.board == board) {
        Space currentSpace = player.getSpace();
        Heading heading = player.getHeading();

        // Check if there's a wall in front of the player (either on the current space or the neighboring space)
        if (currentSpace != null && currentSpace instanceof WallSpace) {
        WallSpace wallSpace = (WallSpace) currentSpace;
            if (wallSpace.getHeading() == heading && wallSpace.hasWall()) {
                return; // Cannot move forward: Wall detected in the way
            }
        }

        // Get the space in the forward direction using getNeighbour method
        Space forwardSpace = board.getNeighbour(currentSpace, heading);

        // Check if the forward space is valid
        if (forwardSpace != null) {
        // Check if there's a wall facing the space the player came from
            Heading backwardHeading = heading.opposite();
            Space backwardSpace = board.getNeighbour(forwardSpace, backwardHeading);

                    // Check if there's a wall facing the backward space in the forward space
                    if (backwardSpace != null && backwardSpace instanceof WallSpace) {
                        WallSpace backwardWallSpace = (WallSpace) backwardSpace;
                        if (backwardWallSpace.getHeading() == backwardHeading && backwardWallSpace.hasWall()) {
                            return; // Cannot move forward: Wall detected in the opposite direction
                        }
                    }

                    // Move the player to the forward space
                    player.setSpace(forwardSpace);
                    activatePitfall(player, player.getSpace());
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
                    activatePitfall(player, player.getSpace());
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
            if(wasActivated = true) {
                wasActivated = false;
                break;
            }
        }
    }


    /**
     * @author Petrine
     * @param powerUp
     * @return none
     * 
     * Allows a player when power up card gets executed to add an energy cube to its reserve
     * 
     */  
    public void powerUp(@NotNull Player player) {
        addEnergyCube(player, energyBank);

        //her opdateres label views for energy reserve og banken
        getPlayerView(player).updateEnergyReserveLabel(player.getEnergyReserve());
                for (int i = 0; i < board.getPlayersNumber(); i++ ) {
                    getPlayerView(board.getPlayer(i)).updateBankLabel(energyBank.getBankStatus());
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
            if(wasActivated = true) {
                wasActivated = false;
                break;
            }
        }
    }

    /**
     * @author Louise
     * @param player
     * @return none
     */
    public void fastForward(@NotNull Player player) {
        for (int i = 0; i < 5; i++) {
            moveForward(player);
            if(wasActivated = true) {
                wasActivated = false;
                break;
            }
        }
    }

    /**
     * @author Louise
     * @param player
     * @return none
     */
    public void turnRight(@NotNull Player player) {
        Heading playerHeading = player.getHeading();
        player.setHeading(playerHeading.next());
    }

    /**
     * @author Louise
     * @param player
     * @return none
     */
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


    
    /**
     * @author Natali
     * @param player,command
     * @return none
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


    /**
     * @author Petrine & Louise
     * @param energyBank
     * 
     * 
     * Checks if a player is on an energy space. 
     * If the that is the case, a cube is added to the players reserve by addEnergyCube(), 
     * and the labels showing the reserve and bank 
     * 
     */
    public void isPlayerOnEnergySpace(Player player, EnergyBank energyBank) {
        Space currentSpace = player.getSpace();
        energyBank = this.energyBank;
        if(currentSpace instanceof EnergySpace) {   //hvis spiller lander på et energySpace 
            if(energyBank.getBankStatus() > 0) {    //tjekker om banken er fuld
                addEnergyCube(player, energyBank);      //tilføjer en cube til en spillers reserve
                getPlayerView(player).updateEnergyReserveLabel(player.getEnergyReserve());
                for (int i = 0; i < board.getPlayersNumber(); i++ ) {
                    getPlayerView(board.getPlayer(i)).updateBankLabel(energyBank.getBankStatus());
                }
           } 
        }
    }


    public void isPlayerOnCheckpointSpace(Player player) {
        Space currentSpace = player.getSpace();
        if(currentSpace instanceof CheckpointSpace) {
            board.setPhase(Phase.RESULT);
        }
    }




    /**
     * @author Petrine & Louise
     * Allows a player to have its own energyreserve, that will get updated every time 
     * a cube gets added to it. 
     * 
     */
    public void addEnergyCube(Player player, EnergyBank energyBank) {   //tilføjelse af en cube hvis ønsket. Kaldes når robot lander på energy space el. trækker power up kort        
        Integer playerBank = player.getEnergyReserve();
        energyBank = board.getEnergyBank();
        Integer energyBankStatus = energyBank.getBankStatus();
        if(energyBank.takeEnergyCube() == true) {   //hvis banken er fuld tilføjes en cube til reserven
            // TILFØJET AF LOUISE
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

        Integer playerNo = board.getPlayersNumber();
        EnergyBank energyBank = board.getEnergyBank();
        for (int i = 0; i < playerNo; i++ )  {
            Player player = board.getPlayer(i);
            isPlayerOnEnergySpace(player, energyBank);
            isPlayerOnCheckpointSpace(player);

        }
        
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
     * @author Louise
     * @param player
     * @return none
     */
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            switch (command) {
                
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



    
    /**
     * @author Natali
     *
     * @return playersTurn
     */

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


    



    
 /**
  * @author Natali
  * @param player
  * @return distance
*/
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

    /**
     * @author Nikolaj
     * @throws ImpossibleMoveException
     * activates conveyor belts and moves players standing on them. Uses manipulateSpace() to determine next space to move the player to. Uses setSpace() to move the player.
     */
    public void activateConveyorBelt() throws ImpossibleMoveException{
        for(int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            int moveAmount;
            if(player.getSpace() != null){
                if (player.getSpace().getConveyorBelt()!=null){
                    
                    if (player.getSpace().getConveyorBelt().getBeltType()==1){
                        moveAmount = 1;
                    } else if(player.getSpace().getConveyorBelt().getBeltType()==2){
                        moveAmount = 2;
                    } else{
                        moveAmount = 0;
                    }
                    
                    for(int c = moveAmount; c > 0; c--){
                        Heading heading = player.getSpace().getConveyorBelt().getHeading();  
                        Space target = null;

                        switch (heading) {
                            case NORTH:
                                target = manipulateSpace(1, heading, player.getSpace().x, player.getSpace().y);
                                break;
                            
                            case SOUTH:
                                target = manipulateSpace(1, heading, player.getSpace().x, player.getSpace().y);
                                break;

                            case WEST:
                                target = manipulateSpace(1, heading, player.getSpace().x, player.getSpace().y);
                                break;
                        
                            case EAST:
                                target = manipulateSpace(1, heading, player.getSpace().x, player.getSpace().y);
                                break;
                            default:
                                throw new ImpossibleMoveException(player, player.getSpace(), heading);
                        }
                        if (target == null) return;
                        if (target.getConveyorBelt() == null) {
                            if(target.getPlayer() == null) {
                                player.setSpace(target);
                                moveAmount = 0;
                            } else {
                                moveAmount = 0;
                            }
                        } else if (target.getConveyorBelt().getBeltType() ==1 || target.getConveyorBelt().getBeltType() == 2) {
                            player.setSpace(target);
                        } else {}
                    }
                }
            }
        }

    }


    /**
     * @author Nikolaj
     * @param OFFSET
     * @param heading
     * @param x
     * @param y
     * @return
     * @throws ImpossibleMoveException
     * Used for manipulating spaces without using the players heading. Which is a big advantage in many cases.
     */
    protected Space manipulateSpace(int OFFSET, Heading heading, int x, int y) throws ImpossibleMoveException{
        Space space = null;
            switch (heading) {
                case NORTH:
                    if (OFFSET < 0 && y < board.height - 1) {
                        space = board.getSpace(x, y + Math.abs(OFFSET));
                    } else if (y >=  OFFSET && OFFSET > 0){
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
                    if (OFFSET < 0 && x < board.width -1) {
                        space = board.getSpace(x + Math.abs(OFFSET), y);
                    } else if (x >= OFFSET && OFFSET > 0) {
                        space = board.getSpace(x - OFFSET, y);
                    } else {
                        throw new ImpossibleMoveException(null, space, heading);
                    }
                break;
        }
            if(space == null) {
                throw new ImpossibleMoveException(null, space, heading);
            }
        return space;
    }

/**
 * @author Nikolaj
 * activates gear spaces and turns every player standing on them.
 */
    public void activateGearSpaces() {
        for(int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            Space space = player.getSpace();
            if(space != null) {
                if(space.getGearSpace() != null){
                    if(space.getGearSpace().getGearType().equals("LEFT")){
                        turnLeft(player);
                    } else if(space.getGearSpace().getGearType().equals("RIGHT")){
                        turnRight(player);
                    }
                } else {}
            }
        }
    }

    public void activatePushPanels() throws ImpossibleMoveException{
        for(int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            Space space = player.getSpace();
            if(space != null) {
                if(space.getPushPanel() != null){
                    int [] currentRegisters = space.getPushPanel().getRegisters();
                    for (int c = 0; c <= currentRegisters.length -1; c++){
                        if (board.getStep() == currentRegisters[c]){
                            Heading heading = space.getPushPanel().getHeading();
                            Space target = null;
                            switch (heading) {
                                case EAST:
                                    target = manipulateSpace(1, heading, player.getSpace().x, player.getSpace().y);
                                    break;
                                case NORTH:
                                    target = manipulateSpace(1, heading, player.getSpace().x, player.getSpace().y);
                                    break;
                                case SOUTH:
                                    target = manipulateSpace(1, heading, player.getSpace().x, player.getSpace().y);
                                    break;
                                case WEST:
                                    target = manipulateSpace(1, heading, player.getSpace().x, player.getSpace().y);
                                    break;
                                default:
                                    throw new ImpossibleMoveException(player, space, heading);
                            }
                            if (target == null) {
                                return;
                            } if(target.getPlayer()==null) {
                                player.setSpace(target);
                            }
                        } else {}
                    }
                } else {}
            }
        }
    }

    public void activatePitfall(Player player, Space space){
        if(space.getPitfall() instanceof Pitfall) {
            player.setSpace(findRespawnPoint());
            wasActivated = true;
        }
    }

    public Space findRespawnPoint(){
        int i = 0;
        int j = 0;
        Space respawnPoint = board.getSpace(i, j);
        for(i = 0; i <= board.width-1; i++) {
            for (j = 0; j <= board.height-1; j++) {
                respawnPoint = board.getSpace(i, j);
                if (respawnPoint.getRespawnPoint() instanceof RespawnPoint){
                    return respawnPoint;
                } else {}
            }
        }
        return respawnPoint = board.getSpace(0, 0);
    }


}








