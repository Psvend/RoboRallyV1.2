package dk.dtu.compute.se.pisd.roborally.client.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProgCards {
    @JsonProperty("card_id")
    private int cardId;

    @JsonProperty("action")
    private String cardAction;
    @JsonProperty("executed_status")
    private String executedStatus;
    @JsonProperty("type")
    private String type;

    public ProgCards() {
    }

    public ProgCards(int cardId, String cardAction, String executedStatus, String type) {
        this.cardId = cardId;
        this.cardAction = cardAction;
        this.executedStatus = executedStatus;
        this.type = type;
    }

    public void setCardId (int cardId) {
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardAction(String cardAction) {
        this.cardAction = cardAction;
    }

    public String getCardAction() {
        return cardAction;
    }

    public void setCardExecutedStatus(String cardExecutedStatus) {
        this.executedStatus = executedStatus;
    }

    public String getCardExecutedStatus() {
        return executedStatus;
    }

    public void setCardType(String type) {
        this.type = type;
    }

    public String getCardType() {
        return type;
    }

    public List<ProgCards> progCardsList(){
        return List.of(
                new ProgCards(1, "Move 1", "Not Executed", "Fwd"),
                new ProgCards(2, "Move 2", "Not Executed", "Bwd"),
                new ProgCards(3, "Move 3", "Not Executed", "2x Fwd"),
                new ProgCards(4, "Move Back", "Not Executed", "3x Fwd"),
                new ProgCards(5, "Rotate Left", "Not Executed", "Turn Right"),
                new ProgCards(6, "Rotate Right", "Not Executed", "Turn Left"),
                new ProgCards(7, "U-Turn", "Not Executed", "U-turn"),
                new ProgCards(8, " ", "Not Executed", "Fast Fwd"),
                new ProgCards(9, " ", "Not Executed", "Prvs commnd"),
                new ProgCards(10, "Power Up", "Not Executed", "Power Up")
        );
    }

    @Override
    public String toString() {
        return "Cards{" +
                "cardId=" + cardId +
                ", cardAction=" + cardAction +
                ", executedStatus=" + executedStatus +
                ", type=" + type +
                '}';
    }
}
