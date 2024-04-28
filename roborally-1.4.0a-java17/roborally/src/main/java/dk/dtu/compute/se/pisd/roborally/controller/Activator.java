package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;

public class Activator {
    private Board board;

    private void activateConveyorBelt(Board board){
        this.board = board;
        for(int x = 0; x < board.width; x++){
            for(int y = 0; y < board.height; y++) {
                if(this.board.getSpace(x, y).getConveyorBelt() != null){

                }
            }
        }
    }
}
