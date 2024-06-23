package dk.dtu.compute.se.pisd.roborally.client.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Register {
    @JsonProperty("register_id")
    private int registerId;

    @JsonProperty("player_id")
    private Players playerId;


    @JsonProperty("card_id")
    private ProgCards cardId;


    @JsonProperty("register_number")
    private int registerNumber;

    //1 for executed status of the register and 0 for not executed
    @JsonProperty("register_status")
    private int registerStatus;

   /* public Register(Players playerId, ProgCards cardId, int registerNumber, int registerStatus) {
        this.playerId = playerId;
        this.cardId = cardId;
        this.registerNumber = registerNumber;
        this.registerStatus = registerStatus;
    }*/


    public void setRegisterId(int register_Id) {
        this.registerId = register_Id;
    }

    public int getRegisterId() {
        return registerId;
    }

    public void setPlayerId(Players player_Id) {
        this.playerId = player_Id;
    }

    public Players getPlayerId() {
        return playerId;
    }


    public void setCardId(ProgCards card_Id) {
        this.cardId = card_Id;
    }

    public ProgCards getCardId() {
        return cardId;
    }

    public void setRegisterNumber(int registerNumber) {
        this.registerNumber = registerNumber;
    }

    public int getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterStatus(int registerStatus) {
        this.registerStatus = registerStatus;
    }

    public int getRegisterStatus() {
        return registerStatus;
    }

    @Override
    public String toString() {
        return "Register{" +
                "register_Id=" + registerId +
                ", player_Id=" + playerId.toString() +
                ", card_Id=" + cardId.toString() +
                ", registerNumber=" + registerNumber +
                ", registerStatus=" + registerStatus +
                '}';
    }
}
