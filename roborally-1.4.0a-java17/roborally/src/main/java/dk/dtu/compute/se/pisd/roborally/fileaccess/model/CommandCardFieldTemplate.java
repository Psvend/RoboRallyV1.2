package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

import dk.dtu.compute.se.pisd.roborally.model.CommandCard;

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
