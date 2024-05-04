package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;

/**
 * 
 * @author: Petrine
 * @param EnergyBank
 * Creates an energy bank that fills up the energySpaces when necessary
 * and whenever a fifth register is evoked with the energy card. 
 * 
 * 
 */


public class EnergyBank {
    private int energyCubes;
    private int energyCubesAmount;

    public EnergyBank(int energyCube) {
        this.energyCubesAmount = 50*energyCube;  //banken har 50 energyCubes i starten
        energyCube = 1;
        //initialCubes = 50;  //sætter den til altid at starte med at have 50 cubes
    }

    //ÆNDRET AF LOUISE
    public boolean takeEnergyCube() {  //metode til at opdatere når en energy cubes tages fra beholdningen i banken
        if (this.energyCubes > 0 && this.energyCubes <=50) {   //hvis beholdningen er fuld
            return true;
        } else {    //hvis beholdningen er tom
            energyCubes = 0;  //sætter beholdningen til 0
            return false;
        }
    }

    public int getBankStatus() {  //tjekker nuværende beholdning i banken
        // ÆNDRET AF LOUISE
        return this.energyCubes;
    }

    // TILFØJET AF LOUISE
    public void setEnergyBank(int i){
        this.energyCubes = i;
    }


    //MULIGVIS SLET?
    public void attach(EnergyBank energyBankView) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attach'");
    }


    //andre metoder tilføjes hernede




}

