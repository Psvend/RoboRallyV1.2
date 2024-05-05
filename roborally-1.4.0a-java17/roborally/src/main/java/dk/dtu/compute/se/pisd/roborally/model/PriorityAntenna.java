package dk.dtu.compute.se.pisd.roborally.model;

public class PriorityAntenna extends Space {

      public boolean isPriorityAntenna;//boolean to check if the space is a priority antenna

    public PriorityAntenna(Board board, int x, int y) {
        super(board, x, y);
        this.isPriorityAntenna = true;

    }
    public void setPriorityAntenna(boolean isPriorityAntenna) {
        this.isPriorityAntenna = isPriorityAntenna;
    }
}
