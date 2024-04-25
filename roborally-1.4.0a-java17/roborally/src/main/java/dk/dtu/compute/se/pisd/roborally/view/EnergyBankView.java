package dk.dtu.compute.se.pisd.roborally.view;
import javafx.scene.image.Image;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;



//file made by Petrine

//VIRKER IKKE
public class EnergyBankView extends PlayerView {
    private VBox contentBox;
    private GameController gameController;
    private Label bankLabel;

    public EnergyBankView(Label label, EnergyBank.getBankStatus()) {
        bankLabel = new Label("Energy Bank Level: ");
        
    }


    @Override
    public void updateView(Subject subject) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'updateView'");
    }
  }

