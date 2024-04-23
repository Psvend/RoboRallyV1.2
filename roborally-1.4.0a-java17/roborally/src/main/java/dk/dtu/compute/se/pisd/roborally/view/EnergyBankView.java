package dk.dtu.compute.se.pisd.roborally.view;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;


public class EnergyBankView extends GridPane implements ViewObserver {

    private Label energyLabel;
    private EnergyBank energyBank;


    public EnergyBankView(EnergyBank energyBank) {

        this.energyBank = energyBank;
        energyLabel = new Label("Energy Bank Level: " + EnergyBank.getBankStatus());
        this.add(energyLabel, 0, 0);
        this.energyBank.attach(this);
    

    }


    @Override
    public void updateView(Subject subject) {
        throw new UnsupportedOperationException("Unimplemented method 'updateView'");
    }
 
}   

