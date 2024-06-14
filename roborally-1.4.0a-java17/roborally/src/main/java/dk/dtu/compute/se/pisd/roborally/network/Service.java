package dk.dtu.compute.se.pisd.roborally.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class Service implements HttpRequestService{

    private HttpClient client;
    public Service(){
        this.client = HttpClient.newHttpClient();
    }
    @Override
    public String sendGet(String url) {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("User-Agent", "Product Client")
                .header("Content-Type", "application/json")
                .build();
        try {
            return client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String sendPost(String url, String body) {

        return null;
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
