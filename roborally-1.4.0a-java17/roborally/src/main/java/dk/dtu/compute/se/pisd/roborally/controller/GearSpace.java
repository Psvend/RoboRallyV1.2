package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class GearSpace extends FieldAction{
    private Heading heading;

    public Heading getHeading(){
        return heading;
    }
    
    public void setHeading(Heading heading){
        this.heading = heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doAction'");
    }
    
}
