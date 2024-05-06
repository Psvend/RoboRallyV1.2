package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

public class CheckPointSpaceTemplate {
    public int x;
    public int y;
    public boolean isPlayerOnCheckpointSpace;

    public boolean getIsPlayerOnCheckPointSpace() {
        return isPlayerOnCheckpointSpace;
    }
    public void setIsPlayerOnCheckpointSpace(boolean isPlayerOnCheckpointSpace) {
        this.isPlayerOnCheckpointSpace = isPlayerOnCheckpointSpace;
    }
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
