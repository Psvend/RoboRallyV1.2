package dk.dtu.compute.se.pisd.roborally.controller;

/**
 * @author Nikolaj
 */
public class GearSpace{
    private String gearType;

    /**
     * @author Nikolaj 
     * @return gearType
     * returns the string gearType, which determines if the gear spins LEFT or RIGHT.
     */
    public String getGearType(){
        return gearType;
    }

    /**
     * @author Nikolaj
     * @param gearType
     * Sets the String gearType. Only 'LEFT' and 'RIGHT' is allowed.
     */
    public void setGearType(String gearType){
        this.gearType = gearType;
    }
}