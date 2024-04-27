package dk.dtu.compute.se.pisd.roborally.model;


import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;



/*TROR DER ER NOGET GALT. MANGLER EN CONNECTION TIL NÅR RESERVEN PRINTES (L. 108 PLAYERVIEW), OPDATERER IKKE
*NÅR EN SPILLER LANDER PÅ ET ENERGYSPACE....
*/



/**
 * @author Petrine
 * @param EnergySpace
 * The design of the energy space. Is connected to a player, by checking if the player is on its field.
 * If the player is on the field, and the space has a cube, the player will get one added to its energyReserve
 */


public class EnergySpace extends Space {
    
    public boolean hasEnergyCube;

    public EnergySpace(Board board, int x, int y) {
        super(board, x, y);  //space on the board
        this.hasEnergyCube = true;  //initialiserer space med cube
        
    }

    //skal tjekke om en spiller er landet på et energySpace
    public boolean isPlayerOnEnergySpace(Player player, EnergyBank EnergyBank) {
        Space currentSpace = player.getSpace();

        if(currentSpace instanceof EnergySpace) {   //hvis spiller lander på et energySpace 
            if(EnergyBank.getBankStatus() > 0) {    //tjekker om banken er fuld
            player.addEnergyCube(EnergyBank);      //tilføjer en cube til en spillers reserve
            notifyAll();    //opdaterer det hele om ændringen
            return true;
           } else {
            return true;
           }
        }
        return false;
    }



}

        /*

        //håndterer når en spiller lander på et felt med en energy cube på sig
        FieldAction energyAction = new FieldAction() {

            @Override
            public boolean doAction(GameController gameController, Space space) {
                Player player = space.getPlayer();


                //tjekker om der er en spiller og om energy space har en cube
                if(player != null && hasEnergyCube() == true) {
                    if(player.addEnergyCube(board.getEnergyBank()) == true) {
                            hasEnergyCube = false;  //feltet er nu tømt
                            notifyChange();   //underretter alle om at der er sket en opdatering, involverer bl.a. player view
                            return true;
                    }
                }
                return false;
                
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
*/