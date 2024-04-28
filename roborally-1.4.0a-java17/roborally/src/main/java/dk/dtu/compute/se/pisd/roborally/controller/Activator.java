package dk.dtu.compute.se.pisd.roborally.controller;

import org.jetbrains.annotations.NotNull;

import dk.dtu.compute.se.pisd.roborally.model.Board;

public class Activator {
    private static Activator activator;
    private Board board;
    private GameController gameController;

    public static Activator getInstance(){
        if(activator == null){
            activator = new Activator();
        }
        return activator;
    }

    public void activateConveyorBelt(@NotNull Board board, @NotNull GameController gameController){
        this.board = board;
        this.gameController = gameController;
        for(int x = 0; x < this.board.width; x++){
            for(int y = 0; y < this.board.height; y++) {
                if(this.board.getSpace(x, y).getConveyorBelt() != null ){
                    this.board.getSpace(x, y).getConveyorBelt().doAction(this.gameController, this.board.getSpace(x, y));
                } else {
                }
            }
        }
    }
}
