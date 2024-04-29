package dk.dtu.compute.se.pisd.roborally.model;

public class WallSpace extends Space {

    private final Heading heading;
    private final boolean hasWall;

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

    // Optionally, you can override methods from the Space class if needed
    // For example, you may want to override getPlayer() to return null always
    @Override
    public Player getPlayer() {
        return null;
    }

    // Add any additional methods or fields specific to WallSpace if needed
}



}
