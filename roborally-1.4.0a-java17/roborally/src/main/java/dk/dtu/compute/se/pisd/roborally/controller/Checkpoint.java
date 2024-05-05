package dk.dtu.compute.se.pisd.roborally.controller;

import org.jetbrains.annotations.NotNull;

import dk.dtu.compute.se.pisd.roborally.model.CheckpointSpace;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;



public class Checkpoint extends FieldAction{

    private Player player;
    
    
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        // TODO needs to be implemented
        if (gameController.board.getCurrentPlayer().getSpace() instanceof CheckpointSpace){
            return true;
        } else {
            return false;
        }
    }
    
}
