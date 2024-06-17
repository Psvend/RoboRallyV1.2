package dk.dtu.compute.se.pisd.roborally.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LobbyService implements HttpRequestService{
    private HttpRequestService httpRequestService;



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
