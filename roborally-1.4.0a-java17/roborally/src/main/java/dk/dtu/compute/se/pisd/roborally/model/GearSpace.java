package dk.dtu.compute.se.pisd.roborally.model;

/**
 * @author Louise
 * The design of the gearspaces
 */
public class GearSpace  extends Space{

    final public boolean startSpace;

    public GearSpace (Board board, int x, int y) {
        super(board, x, y);
        this.startSpace = true;
    }

    
}
