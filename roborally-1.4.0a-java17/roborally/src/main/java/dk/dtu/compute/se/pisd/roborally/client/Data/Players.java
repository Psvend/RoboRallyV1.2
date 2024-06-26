package dk.dtu.compute.se.pisd.roborally.client.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Players {

    @JsonProperty("player_id")
    private int playerId; // Assuming game_id is autogenerated and Long type
    @JsonProperty("player_name")
    private String playerName;
    @JsonProperty("phase_status")
    private int phaseStatus;
    @JsonProperty("game_id")
    private Games gameID;

    public void setPlayerId (int playerId) {
        this.playerId = playerId;
    }
    public int getPlayerId() {
        return playerId;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public String getPlayerName() {
        return playerName;
    }
    public void setPhaseStatus(int phaseStatus) {
        this.phaseStatus = phaseStatus;
    }
    public int getPhaseStatus() {
        return phaseStatus;
    }
    public void setGameID(Games gameID) {
        this.gameID = gameID;
    }
    public Games getGameID() {
        return gameID;
    }
    @Override
    public String toString() {
        return "Players{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", phaseStatus=" + phaseStatus +
                ", gameID=" + gameID.toString() +
                '}';
    }



}
