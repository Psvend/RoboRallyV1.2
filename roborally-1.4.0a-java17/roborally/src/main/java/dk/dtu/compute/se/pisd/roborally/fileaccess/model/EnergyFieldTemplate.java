package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

/**
 * @author Benjamin
 * @param energySpaceTemplate
 * this class is a template for the energy space and to save the energy space in the json file.
 */

public class EnergyFieldTemplate {
    public int x;
    public int y;
    public boolean hasEnergyCube;

    // getters and setters
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

    public boolean hasEnergyCube() {
        return hasEnergyCube;
    }

    public void setEnergyCube(boolean hasEnergyCube) {
        this.hasEnergyCube = hasEnergyCube;
    }
}
