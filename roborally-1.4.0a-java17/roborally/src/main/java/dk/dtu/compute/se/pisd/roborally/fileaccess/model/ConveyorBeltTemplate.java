package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

public class ConveyorBeltTemplate extends FieldActionTemplate{

    public String getHeading() {
        return heading;
    }


    public void setHeading(String heading) {
        this.heading = heading;
    }

    public int getBeltType() {
        return beltType;
    }

    public void setBeltType(int beltType) {
        this.beltType = beltType;
    }

    public String getTurnBelt() {
        return turnBelt;
    }

    public void setTurnBelt(String turnBelt) {
        this.turnBelt = turnBelt;
    }

    public String heading;

    public int beltType;
    public String turnBelt = "";
    public int x;
    public int y;

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
