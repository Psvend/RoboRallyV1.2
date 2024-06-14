package dk.dtu.compute.se.pisd.roborally.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ServiceClient implements HttpRequestService{
    private HttpRequestService httpRequestService;


private static final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();

public static String getProducts() throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("http://localhost:8080/products"))
            .setHeader("User-Agent", "Product Client")
            .header("Content-Type", "application/json")
            .build();
    CompletableFuture<HttpResponse<String>> response =
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    String result = response.thenApply((r)->r.body()).get(5, TimeUnit.SECONDS);
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
