package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

/**
 * @author Benjamin
 * @param WallsTemplate
 * this class is a template for the walls and to save the walls in the json file.
 */

public class WallsTemplate {
    public int x;
    public int y;
    public String heading;
    public boolean hasWall;

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public String getHeading() {
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }
    public boolean getHasWall() {
        return hasWall;
    }

    public void setHasWall(boolean hasWall) {
        this.hasWall = hasWall;
    }
}
