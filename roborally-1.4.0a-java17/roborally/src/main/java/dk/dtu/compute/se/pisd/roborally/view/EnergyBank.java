package dk.dtu.compute.se.pisd.roborally.view;

/**
 * 
 * @author: Petrine
 * 
 * 
 * 
 * 
 */


public class EnergyBank {
    private int energyCubes;  //gemmer antallet af energy cubes 

    public EnergyBank(int initialCubes) {
        this.energyCubes = initialCubes;
        initialCubes = 50;  //sætter den til altid at starte med at have 50 cubes
    }

    public boolean takeEnergyCube() {  //metode til at opdatere når en energy cubes tages fra beholdningen i banken
        if(energyCubes > 0) {   //hvis beholdningen er fuld
            energyCubes--;
            /*tilføj at robottens reserve øges med 1 når den klasse er lavet*/
            return true;  
        } else {   //hvis beholdningen er tom
            return false;
        }
    }

    public int getBankStatus() {  //tjekker nuværende beholdning i banken
        return energyCubes;  
    }


    //andre metoder tilføjes hernede




}

