package dk.dtu.compute.se.pisd.roborally.network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dtu.compute.se.pisd.roborally.RoboRally;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LobbyService implements HttpRequestService {
    private HttpRequestService httpRequestService;

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public CompletableFuture<List<RoboRally>> getAvailableGames(int gameStatus){
        String url = "http://localhost:8080/availableGames/" + gameStatus;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        String body = response.body();
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            return objectMapper.readValue(body, new TypeReference<List<RoboRally>>(){});
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Collections.emptyList();
                        }
                    } else {
                        System.out.println("Error: " + response.statusCode());
                        return Collections.emptyList();
                    }
                });

    }


    @Override
    public String sendGet(String url) {
        return null;
    }

    @Override
    public String sendPost(String url, String body) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String sendPut(String url, String body) {
        return null;
    }

    @Override
    public String sendDelete(String url) {
        return null;
    }
}
