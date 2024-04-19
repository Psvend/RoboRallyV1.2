package dk.dtu.compute.se.pisd.roborally.model;

public class EnergySpace extends Space{
    
    private boolean hasEnergyCube;

    public EnergySpace(Board board) {
        super(board, x, x);  //space on the board
        this.hasEnergyCube = true;  //an energy space will contain an energy cube initially
    }


    //When a player ends their fifth register on an energy space
    @Override
    public void action(Player player) {
        if(this.hasEnergyCube && player != null) {
            if(player.addEnergyCube(board.getEnergyBank())) {  //board has to have an energy bank method!
                this.hasEnergyCube = false; //cube gets collected, so now empty
            }
        }
    }

    //kan fylde den op med en cube igen hvis ønsket
    public void resetEnergyCube() {
        this.hasEnergyCube = true;
    }

    //kan tjekke om den er fuld el. tom
    public boolean hasEnergyCube() {
        return this.hasEnergyCube;
    }

}
