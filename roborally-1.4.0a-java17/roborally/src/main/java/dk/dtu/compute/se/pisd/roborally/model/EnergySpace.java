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


    // DEN Er FLYTTET TIL GAMECONTROLLER
    //skal tjekke om en spiller er landet på et energySpace
    // public boolean isPlayerOnEnergySpace(Player player, EnergyBank energyBank) {
    //     Space currentSpace = player.getSpace();

    //     if(currentSpace instanceof EnergySpace) {   //hvis spiller lander på et energySpace 
    //         if(energyBank.getBankStatus() > 0) {    //tjekker om banken er fuld
    //         player.addEnergyCube(energyBank);      //tilføjer en cube til en spillers reserve
    //         notifyAll();    //opdaterer det hele om ændringen
    //         return true;
    //        } else {
    //         return true;
    //        }
    //     }
    //     return false;
    // }



}

     