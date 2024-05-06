package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

/**
 * @author Benjamin
 * @param GearSpaceTemplate
 * this class is a template for the gear space and to save the gear space in the json file.
 */

public class GearSpaceTemplate {
    public int x;
    public int y;
    public String gearType;


    public String getGearType() {
        return gearType;
    }
    public void setGearType(String gearType) {
        this.gearType = gearType;
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
