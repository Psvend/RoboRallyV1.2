package dk.dtu.compute.se.pisd.roborally.model;

public class EnergySpace extends Space{
    public boolean hasEnergyCube;
    public EnergySpace(Board board, int x, int y) {
        super(board, x, y);
        this.hasEnergyCube = true;
    }
    public boolean hasEnergyCube() {
        return hasEnergyCube;
    }
    public boolean setHasEnergyCube(boolean hasEnergyCube) {
        return hasEnergyCube = hasEnergyCube;
    }
}
