package dk.dtu.compute.se.pisd.roborally.controller;

public class EnergyField {
    private boolean contains = true;
    
    public boolean hasEnergyCube(){
        return contains;
    }

    public void setEnergyCube(boolean contains) {
        if(contains){
            contains =false;
        } else {
            contains = true;
        }
    }
}
