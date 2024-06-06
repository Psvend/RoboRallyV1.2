package dk.dtu.compute.se.pisd.roborally.controller;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PitFall extends FieldAction {
    private boolean isPitFall = false;

    public void activePitFall(){
        if(isPitFall == false) {
            isPitFall = true;
        } else {
            isPitFall = false;
        }
    }
    
    public boolean isPitFall() {
        return isPitFall;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doAction'");
    }
}