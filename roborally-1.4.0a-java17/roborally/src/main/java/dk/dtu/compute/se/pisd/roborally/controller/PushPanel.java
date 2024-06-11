package dk.dtu.compute.se.pisd.roborally.controller;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PushPanel extends FieldAction{
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

    @Override
    public boolean doAction(GameController gameController, Space space) {
        throw new UnsupportedOperationException("Unimplemented method 'doAction'");
    }

}
