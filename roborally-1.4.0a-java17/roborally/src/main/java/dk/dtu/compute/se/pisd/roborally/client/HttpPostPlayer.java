package dk.dtu.compute.se.pisd.roborally.client;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dtu.compute.se.pisd.roborally.client.Data.Board;
import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
import dk.dtu.compute.se.pisd.roborally.client.Data.Players;
import dk.dtu.compute.se.pisd.roborally.view.CreateGameView;

public class HttpPostPlayer {

    public static Games createdGame;
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static void createFirstPlayer(String gameName, int playersAmount, String playerName) throws URISyntaxException, IOException, InterruptedException {
        // Create a new game object
        Games newGame = new Games();
        newGame.setGameName(gameName);
        newGame.setPlayersAmount(playersAmount);
        newGame.setJoinedPlayers(1);
        newGame.setGameStatus(0);

        Board board = new Board();
        board.setBoardId(5);
        board.setBoardName("difficult");
        newGame.setBoard(board);



        // First, POST the game
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/createGame"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(newGame)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            createdGame = new ObjectMapper().readValue(response.body(), Games.class);
            System.out.println(createdGame);
            int createdGameId = createdGame.getGameId();

            // Create a new player object
            Players newPlayer = new Players();
            newPlayer.setPhaseStatus(false);
            newPlayer.setPlayerName(playerName);
            newPlayer.setGame(createdGame);

            // Then, POST the player with the game ID
            HttpRequest playerRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/addPlayer"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(newPlayer)))
                    .build();

            HttpResponse<String> playerResponse = httpClient.send(playerRequest, HttpResponse.BodyHandlers.ofString());

            if (playerResponse.statusCode() == 201) {
                Players createdPlayer = new ObjectMapper().readValue(playerResponse.body(), Players.class);
                System.out.println("Player added: " + createdPlayer);
            } else {
                System.out.println("Failed to add player: " + playerResponse.body());
                System.out.println(playerResponse.statusCode());

            }
        } else {
            System.out.println("Failed to create game: " + response.body());
        }
    }
}
