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
    private static int energyCubes;  //gemmer antallet af energy cubes 

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

    public static int getBankStatus() {  //tjekker nuværende beholdning i banken
        return energyCubes;  
    }


    //MULIGVIS SLET
    public void attach(EnergyBankView energyBankView) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attach'");
    }


    //andre metoder tilføjes hernede




}

