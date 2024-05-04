package dk.dtu.compute.se.pisd.roborally.model;

public class CheckpointSpace extends Space {

    private final boolean isPlayerOnCheckpointSpace;
    
    public CheckpointSpace(Board board, int x, int y) {
        super(board, x, y);

        this.isPlayerOnCheckpointSpace = false;

        
    }
}
