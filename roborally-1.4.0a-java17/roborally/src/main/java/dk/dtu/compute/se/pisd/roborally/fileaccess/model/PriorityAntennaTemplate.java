package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

/**
 * @author Benjamin
 * @param PriorityAntennaTemplate
 * this class is a template for the priority antenna and to save the priority antenna in the json file
 */


public class PriorityAntennaTemplate {
    int x;
    int y;

    public boolean isPriorityAntenna;

    public boolean getIsPriorityAntenna() {
        return isPriorityAntenna;
    }
    public void setIsPriorityAntenna(boolean isPriorityAntenna) {
        this.isPriorityAntenna = isPriorityAntenna;
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
