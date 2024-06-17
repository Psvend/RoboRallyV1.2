package dk.dtu.compute.se.pisd.roborally.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServiceClient implements HttpRequestService{
    private HttpRequestService httpRequestService;

    public ServiceClient(){
        this.httpRequestService = new HttpRequestService() {
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
        };

    }


private static final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();

public static String getPlayer() throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("http://localhost:8080/api/players"))
            .build();
    CompletableFuture<HttpResponse<String>> response =
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    String result = response.thenApply((r)-> {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<String > players = objectMapper.readValue(r.body(), List.class);
            Board board = new Board(8, 8);
            for (String player : players) {
                Player player1 = objectMapper.readValue(player, Player.class);
                return player1.toString();

            }
            return r.body();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }).join();
    return result;
}
    public String markPlayerAsReady(int playerID) {
        String url = "http://localhost:8080/api/players/" + playerID + "/ready";
        return sendPost(url, "");
    }
    public String creategame(int boardID) {
        String url = "http://localhost:8080/api/createGame";
        return httpRequestService.sendPost(url, "");
    }
    @Override
    public String sendGet(String url) {

     return httpRequestService.sendGet(url);
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
        return httpRequestService.sendPut(url, body);
    }

    @Override
    public String sendDelete(String url) {
        return httpRequestService.sendDelete(url);
    }
}
