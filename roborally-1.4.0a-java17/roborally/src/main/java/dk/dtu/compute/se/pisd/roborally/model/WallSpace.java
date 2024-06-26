package dk.dtu.compute.se.pisd.roborally.model;

public class WallSpace extends Space {
    
    private final Heading heading;
    private final boolean hasWall;

    /**
     * @author Daniel
     *
     * @param board
     * @param x
     * @param y
     * @param heading
     */

    public WallSpace(Board board, int x, int y, Heading heading) {
        super(board, x, y);
        this.heading = heading;
        this.hasWall = true; // By default, WallSpace objects have a wall
    }

    public Heading getHeading() {
        return heading;
    }

    public boolean hasWall() {
        return hasWall;
    }
}







