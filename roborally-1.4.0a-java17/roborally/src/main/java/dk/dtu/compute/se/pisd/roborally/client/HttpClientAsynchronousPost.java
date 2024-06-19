package dk.dtu.compute.se.pisd.roborally.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dtu.compute.se.pisd.roborally.client.Data.Games;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class HttpClientAsynchronousPost {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2) // Use HTTP/2
            .connectTimeout(Duration.ofSeconds(10)) // Timeout after 10 seconds
            .build();

    // Static variable to store the current game
    public static Games currentGame;

    public static CompletableFuture<Games> addGame(Games game) {
        CompletableFuture<Games> futureGame = new CompletableFuture<>();

        try {
            // Convert Games object to JSON string
            String jsonBody = new ObjectMapper().writeValueAsString(game);

            // Prepare HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .uri(URI.create("http://localhost:8080/createGame")) // Replace with your endpoint
                    .header("Content-Type", "application/json")
                    .build();

            // Send HTTP POST request asynchronously
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            currentGame = objectMapper.readValue(response, Games.class);
                            futureGame.complete(currentGame); // Complete the future when the game is added
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            futureGame.completeExceptionally(e); // Complete the future exceptionally if there was an error
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            futureGame.completeExceptionally(e); // Complete the future exceptionally if there was an error
        }

        return futureGame; // Return the future that will be completed in the future
    }





    private static void fetchGames() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/getGames")) // Replace with your endpoint
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        List<Games> gamesList = objectMapper.readValue(response, new TypeReference<List<Games>>() {
                        });

                        // Print the games (for demonstration)
                        for (Games game : gamesList) {
                            System.out.println(game);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .join(); // Block main thread to wait for completion (for demonstration)
    }
}
