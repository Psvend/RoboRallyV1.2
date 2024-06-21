package dk.dtu.compute.se.pisd.roborally.client.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Board {
    @JsonProperty("board_id")
    private int boardId;

    @JsonProperty("board_name")
    private String boardName;

    // Getters and setters for non-autogenerated fields
    public String getBoardName() {
        return boardName;
    }

    public void setBoardId (int boardId) {
        this.boardId = boardId;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    // Getters for autogenerated fields
    public int getBoardId() {
        return boardId;
    }

    // No setter for boardId as it's autogenerated

    // toString method
    @Override
    public String toString() {
        return "Board{" +
                "boardId=" + boardId +
                ", boardName='" + boardName + '\'' +
                '}';
    }
}