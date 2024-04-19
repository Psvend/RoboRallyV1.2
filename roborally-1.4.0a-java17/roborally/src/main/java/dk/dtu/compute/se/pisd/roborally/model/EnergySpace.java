package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;

public class EnergySpace extends Space{
    
    private boolean hasEnergyCube;

    public EnergySpace(Board board, int x, int y) {
        super(board, x, y);  //space on the board
        FieldAction energyAction = new FieldAction() {
            @Override
            public void doAction(Player player) {
                if (hasEnergyCube && player != null) {
                    if(player.addEnergyCube(board.getEnergyBank())) {
                        hasEnergyCube = false;
                    }
                }
            }

            @Override
            public boolean doAction(GameController gameController, Space space) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'doAction'");
            }
        };
        this.getActions().add(energyAction);
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
