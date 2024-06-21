package dk.dtu.compute.se.pisd.roborally.controller;
import dk.dtu.compute.se.pisd.roborally.model.Heading;

public class PushPanel{
    private Heading heading;
    private int pushRegisters[];

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public Heading getHeading(){
        return heading;
    }

    public void setRegisters(int pushRegisters[]) {
        this.pushRegisters = pushRegisters;
    }

    public int[] getRegisters() {
        return pushRegisters;
    }
}
