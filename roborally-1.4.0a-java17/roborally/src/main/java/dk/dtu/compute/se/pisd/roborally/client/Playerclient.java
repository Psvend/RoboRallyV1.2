package dk.dtu.compute.se.pisd.roborally.client;

import com.google.gson.Gson;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Playerclient implements HttpRequestService{

    private HttpRequestService httpRequestService;
    private Gson gson;

    public Playerclient(){
        this.gson = new Gson();

    }
    public Player addPlayer(Player player) {
        String url = "http://localhost:8080 /addPlayer"; // replace with the actual URL
        String body = gson.toJson(player);
        String response = sendPost(url, body);

        // Assuming the response is a JSON representation of the Players object
        return gson.fromJson(response, Player.class);
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
