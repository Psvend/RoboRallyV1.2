package dk.dtu.compute.se.pisd.roborally.model;


    /**
     * @author Louise
     * @param None
     *
     */

public class CheckpointSpace extends Space {

    private final boolean isPlayerOnCheckpointSpace;
    
    public CheckpointSpace(Board board, int x, int y) {
        super(board, x, y);

        this.isPlayerOnCheckpointSpace = false;

        
    }
    public boolean isPlayerOnCheckpointSpace() {
        return isPlayerOnCheckpointSpace;
    }
}