package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;

/**
 * 
 * @author: Petrine
 * Creates an energy bank that fills up the energySpaces when necessary
 * and whenever a fifth register is evoked with the energy card. 
 * 
 * 
 */


public class EnergyBank {
    private int energyCubesAmount;  //gemmer antallet af energy cubes, værdien ændrer sig derfor non-static
    private int energyCube;  //en energyCube har altid værdien 1, derfor static 

    public EnergyBank(int energyCube) {
        this.energyCubesAmount = 50*energyCube;  //banken har 50 energyCubes i starten
        this.energyCube = 1;
        //initialCubes = 50;  //sætter den til altid at starte med at have 50 cubes
    }

    public boolean takeEnergyCube() {  //metode til at opdatere når en energy cubes tages fra beholdningen i banken
        if(energyCubesAmount > 0 && energyCubesAmount <=50) {   //hvis beholdningen er fuld
            energyCubesAmount--;      //beholdningen falder med en hvis true
            notifyAll();
            return true;  
        } else {   //hvis beholdningen er tom
            energyCubesAmount = 0;  //sætter beholdningen til 0
            notifyAll();
            return false;
        }
    }

    public int getBankStatus() {  //tjekker nuværende beholdning i banken
        return energyCubesAmount;
    }


    //MULIGVIS SLET?
    public void attach(EnergyBank energyBankView) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attach'");
    }


    //andre metoder tilføjes hernede




}

