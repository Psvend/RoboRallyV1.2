package dk.dtu.compute.se.pisd.roborally.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();

                return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .exceptionally(e -> {
                            e.printStackTrace();
                            return null;
                        }).join();

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

public String createPlayer()  {
    String url = "http://localhost:8080/api/createPlayer";
    return httpRequestService.sendPost(url, "");
}



    public String creategame(RoboRally game) {
        String url = "http://localhost:8080/createGame";


        ObjectMapper objectMapper = new ObjectMapper();
        String gameJson = "";
        try {
            gameJson = objectMapper.writeValueAsString(game);
            System.out.println(gameJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return httpRequestService.sendPost(url, gameJson);
    }
    public String startGame(){
    String url ="http://localhost:8080/api/startGame";
    return sendPost(url, "");
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
