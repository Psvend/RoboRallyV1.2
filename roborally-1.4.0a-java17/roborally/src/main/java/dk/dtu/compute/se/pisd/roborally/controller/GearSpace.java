package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * @author Nikolaj
 */
public class GearSpace extends FieldAction{
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

    /**
     * currently not used. Will be used in future iterations.
     */
    @Override
    public boolean doAction(GameController gameController, Space space) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doAction'");
    }
    
}