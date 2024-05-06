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

}

     