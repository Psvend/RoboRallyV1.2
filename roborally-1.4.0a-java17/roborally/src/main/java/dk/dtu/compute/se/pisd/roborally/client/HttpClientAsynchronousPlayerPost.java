package dk.dtu.compute.se.pisd.roborally.client;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dtu.compute.se.pisd.roborally.model.Player;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientAsynchronousPlayerPost {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();


    public static void AddPlayer(Player player) {

        try {
            String jsonBody = new ObjectMapper().writeValueAsString(player);


            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .uri(URI.create("http://localhost:8080/addPlayer"))
                    .header("Content-Type", "application/json")
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        System.out.println("s" + response);
                    })
                    .join();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static class Players{
    @JsonProperty("player_id")
    private int playerId;

    @JsonProperty("player_name")
    private String playerName;

    @JsonProperty("phase_status")
    private String phaseStatus;



    }
}
