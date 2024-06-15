package dk.dtu.compute.se.pisd.roborally.model;

/**
 * 
 * @author: Petrine
 * @param EnergyBank
 * Creates an energy bank that fills up the energySpaces when necessary
 * and whenever a fifth register is evoked with the energy card. 
 * 
 * 
 */


public class EnergyBank{
    
    private int energyCubes;

    public EnergyBank(int energyCube) {
        this.energyCubes = 50; //sætter den til altid at starte med at have 50 cubes
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

    /**
     * @author Louise
     * @param none
     */
    public int getBankStatus() { 
        return this.energyCubes;
    }

    /**
     * @author Louise
     * @param none
     */
    public void setEnergyBank(int i){
        this.energyCubes = i;
    }


    public void attach(EnergyBank energyBankView) {
        throw new UnsupportedOperationException("Unimplemented method 'attach'");
    }
}

