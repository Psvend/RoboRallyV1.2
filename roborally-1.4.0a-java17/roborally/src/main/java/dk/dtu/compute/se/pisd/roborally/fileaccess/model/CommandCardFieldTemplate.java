package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

import dk.dtu.compute.se.pisd.roborally.model.CommandCard;

/**
 * @author Benjamin
 * @param CommandCardFieldTemplate
 * this class is a template for the command card field and to save the command card field in the json file
 */

public class CommandCardFieldTemplate {

    private boolean visible;

    private CommandCard card;
    private String cardType;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public void getVisible(boolean visible) {
        this.visible = visible;
    }

    public CommandCard getCard() {
        return card;
    }

    public void setCard(CommandCard card) {
        this.card = card;
    }
}
