package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.controller.GameController.ImpossibleMoveException;
import dk.dtu.compute.se.pisd.roborally.model.Board;

public class Activator {
    private static Activator activator;
    private Board board;
    private GameController gameController;

/**
 * @author Nikolaj
 * @return
 * Used for instantiating an object, which the activateElements() can be tied to.
 */
    public static Activator getInstance(){
        if(activator == null){
            activator = new Activator();
        }
        return activator;
    }

    /**
     * @author Nikolaj
     * @param board
     * @param gameController
     * Used to tie every method activator the board into one. Now you only have to make one call instead of many.
     */
    public void activateElements(Board board, GameController gameController) {
        this.board = board;
        this.gameController = gameController;
        activateConveyorBelts();
        activateGearSpaces();
    }

    /**
     * @author Nikolaj
     * ties the gameController.activateConveyorBelts()-method to a method inside the activator class.
     */
    private void activateConveyorBelts(){
        try{
            gameController.activateConveyorBelt();
        } catch (ImpossibleMoveException e) {

        }
    }

    /**
     * @author Nikolaj
     * ties the gameController.activateGearSpaces()-method to a method inside the activator class.
     */
    private void activateGearSpaces(){
        gameController.activateGearSpaces();
    }

}