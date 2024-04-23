package dk.dtu.compute.se.pisd.roborally.view;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

//VIRKER IKKE
public class EnergyBankView extends VBox {

  private Label energyBankLabel;

  public EnergyBankView energyBankView() {
    EnergyBankView energyBankView = new EnergyBankView();
    energyBankLabel = new Label("Energy Bank Level: " + EnergyBank.getBankStatus());
    this.getChildren().add(energyBankLabel);
    this.setStyle("-fx-padding: 10;");
    energyBankLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
    return energyBankView;
}

}

