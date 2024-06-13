package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

/**
 * @author Benjamin
 * @param CheckPointSpaceTemplate
 * this class is a template for the checkpoint space and to save the checkpoint space in the json file
 */

public class CheckPointSpaceTemplate {
    public int x;
    public int y;
    public int number;

    public int getCheckpointNumber(){
        return number;
    }

    public void setCheckPointNumber(int number){
        this.number = number;
    }

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
}
