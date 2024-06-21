package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;

public class RespawnPoint {
    Heading heading;
    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public Heading getHeading(){
        return heading;
    }
}
