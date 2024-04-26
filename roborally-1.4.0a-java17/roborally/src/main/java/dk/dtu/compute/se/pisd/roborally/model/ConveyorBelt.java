package dk.dtu.compute.se.pisd.roborally.model;

public class ConveyorBelt extends Space{

    private Heading heading;
    private int beltType;

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

    FieldAction ConveyorBelt = 

    public ConveyorBelt(Board board, int x, int y) {
        super(board, x, y);


    }
     
}
