package dk.dtu.compute.se.pisd.roborally.client.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProgCard {

    @JsonProperty("card_id")
    private int cardId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("execute_status")
    private String executeStatus;
    @JsonProperty("action")
    private String action;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
