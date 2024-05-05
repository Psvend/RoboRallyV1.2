package dk.dtu.compute.se.pisd.roborally.controller;

import org.jetbrains.annotations.NotNull;

import dk.dtu.compute.se.pisd.roborally.controller.GameController.ImpossibleMoveException;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Activator {
    private static Activator activator;
    private Board board;
    private GameController gameController;

    /**
     * @author Nikolaj
     * @return Activator activator
     * Instantiates an object the functionality activateElements can be attached to, so that it can be called in GameController
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
     * activates the elements on the board. In future iteration every element should be listed here.
     */
    public void activateElements(Board board, GameController gameController) {
        this.board = board;
        this.gameController = gameController;
        activateConveyorBelts();
        activateGearSpaces();
    }

    /**
     * @author Nikolaj
     * Instantiates the gamecontroller.activateConveyorBelts() in the Activator class. The method is not defined explicitly here, since the calling methods makes more sense in
     * the GameController class. Catches potential ImpossibleMoveException.
     */
    private void activateConveyorBelts(){
        try{
            gameController.activateConveyorBelt();
        } catch (ImpossibleMoveException e) {

        }
    }

    private void activateGearSpaces(){
        gameController.activateGearSpaces();
    }
}
