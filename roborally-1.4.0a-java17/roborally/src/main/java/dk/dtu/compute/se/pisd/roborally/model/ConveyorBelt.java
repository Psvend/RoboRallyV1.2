package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;

public class ConveyorBelt extends Space{

    private Heading heading;
    private int beltType;

    public ConveyorBelt(Board board, int x, int y) {
        super(board, x, y);
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public int getBeltType(){
        return beltType;
    }

    public void setBeltType(int beltType) {
        if(beltType != this.beltType) {
            this.beltType = beltType;
            notifyChange();
        }
    }

    //@Override
    //public boolean doAction(GameController gameController, Space space) {
        
        //Player player = space.getPlayer();
        
        //if(player != null){
            //if(getBeltType()==1) {
                
                //return true;
            //} else if (getBeltType() == 2) {
                //return true;
            //} else return false;
        //} else {
          //  return false;
        //}
    //}
        
        
}
