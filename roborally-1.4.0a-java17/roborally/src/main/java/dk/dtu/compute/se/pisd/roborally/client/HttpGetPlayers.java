package dk.dtu.compute.se.pisd.roborally.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class HttpGetPlayers {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/getGames"))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        List<HttpClientAsynchronous.Games> gamesList = objectMapper.readValue(response, new TypeReference<List<HttpClientAsynchronous.Games>>() {});

                        // Print the games
                        for (HttpClientAsynchronous.Games game : gamesList) {
                            System.out.println(game);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .join();
    }

    // Games class
   /* public class Games {
        @JsonProperty("game_id")
        private int gameId;

        @JsonProperty("game_name")
        private String gameName;

        @JsonProperty("players_amount")
        private int playersAmount;

        // Other properties and methods...

        @Override
        public String toString() {
            return "Games{id=" + gameId + ", gameName='" + gameName + "', playersAmount=" + playersAmount + '}';
        }
*/
    // Static Games class
    public static class Games {
        @JsonProperty("game_id")
        private int gameId;

        @JsonProperty("game_name")
        private String gameName;

        @JsonProperty("players_amount")
        private int playersAmount;

        @JsonProperty("joined_players")
        private int joinedPlayers;

        @JsonProperty("game_status")
        private int gameStatus;

        @JsonProperty("board_id")
        private HttpClientAsynchronous.Board board;

        // Default constructor
        public Games() {}

        // Getters and setters
        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public int getPlayersAmount() {
            return playersAmount;
        }

        public void setPlayersAmount(int playersAmount) {
            this.playersAmount = playersAmount;
        }

        public int getJoinedPlayers() {
            return joinedPlayers;
        }

        public void setJoinedPlayers(int joinedPlayers) {
            this.joinedPlayers = joinedPlayers;
        }

        public int getGameStatus() {
            return gameStatus;
        }

        public void setGameStatus(int gameStatus) {
            this.gameStatus = gameStatus;
        }

        public HttpClientAsynchronous.Board getBoard() {
            return board;
        }

        public void setBoard(HttpClientAsynchronous.Board board) {
            this.board = board;
        }

        @Override
        public String toString() {
            return "Games{gameId=" + gameId + ", gameName='" + gameName + "', playersAmount=" + playersAmount +
                    ", joinedPlayers=" + joinedPlayers + ", gameStatus=" + gameStatus + ", board=" + board + '}';
        }
    }

    // Static Board class
    public static class Board {
        @JsonProperty("board_id")
        private int boardId;

        @JsonProperty("board_name")
        private String boardName;

        // Default constructor
        public Board() {}

        // Getters and setters
        public int getBoardId() {
            return boardId;
        }

        public void setBoardId(int boardId) {
            this.boardId = boardId;
        }

        public String getBoardName() {
            return boardName;
        }

        public void setBoardName(String boardName) {
            this.boardName = boardName;
        }

        @Override
        public String toString() {
            return "Board{boardId=" + boardId + ", boardName='" + boardName + "'}";
        }
    }
}
