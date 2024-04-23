package dk.dtu.compute.se.pisd.roborally.view;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;

public class EnergyView extends GridPane implements ViewObserver {

    private Button energyBankButton;
    private int energyBank;


    public int showEnergyButton() {
        energyBankButton = new Button("Energy Bank Level: " + energyBank);
        this.getChildren().add(energyBankButton);
        return EnergyBank.getBankStatus();
    }


    @Override
    public void updateView(Subject subject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateView'");
    }
    


    
}

