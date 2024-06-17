package dk.dtu.compute.se.pisd.roborally.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServiceClient implements HttpRequestService{
    private HttpRequestService httpRequestService;


private static final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();

public static String getPlayer() throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("http://localhost:3306/api/players"))
            .build();
    CompletableFuture<HttpResponse<String>> response =
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    String result = response.thenApply((r)-> {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<String > players = objectMapper.readValue(r.body(), List.class);
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
    @Override
    public String sendGet(String url) {

     return httpRequestService.sendGet(url);
    }

    @Override
    public String sendPost(String url, String body) {

        return httpRequestService.sendPost(url, body);
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
