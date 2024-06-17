package dk.dtu.compute.se.pisd.roborally.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NewGameService {
    private HttpRequestService httpRequestService;


    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static String getNewGame() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:3306/api/createGame"))
                .build();
        CompletableFuture<HttpResponse<String>> response =
                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String result = response.thenApply((r)-> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<String > newGames = objectMapper.readValue(r.body(), List.class);
                for (String newGame : newGames) {
                    GameController newGame1 = objectMapper.readValue(newGame, GameController.class);
                    return newGame1.toString();

                }
                return r.body();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).join();
        return result;
    }


}
