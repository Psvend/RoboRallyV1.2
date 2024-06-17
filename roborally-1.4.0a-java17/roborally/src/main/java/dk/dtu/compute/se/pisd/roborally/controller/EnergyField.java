package dk.dtu.compute.se.pisd.roborally.controller;

public class EnergyField {
    private boolean contains = true;
    
    public boolean hasEnergyCube(){
        return contains;
    }

    public void setEnergyCube(boolean contains) {
        this.contains = contains;
        if(this.contains){
            this.contains =false;
        } else {
            this.contains = true;
        }
    }
}
