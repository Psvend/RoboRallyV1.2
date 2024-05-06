package dk.dtu.compute.se.pisd.roborally.controller;

import org.jetbrains.annotations.NotNull;

import dk.dtu.compute.se.pisd.roborally.controller.GameController.ImpossibleMoveException;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Activator {
    private static Activator activator;
    private Board board;
    private GameController gameController;
    private Space[][] space;

    public static Activator getInstance(){
        if(activator == null){
            activator = new Activator();
        }
        return activator;
    }

    public void activateElements(Board board, GameController gameController) {
        this.board = board;
        this.gameController = gameController;
        activateConveyorBelts();
        activateGearSpaces();
    }

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