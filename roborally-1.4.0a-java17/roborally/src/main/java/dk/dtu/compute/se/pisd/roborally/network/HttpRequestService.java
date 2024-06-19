package dk.dtu.compute.se.pisd.roborally.network;

public interface HttpRequestService {
    public String sendGet(String url);
    public String sendPost(String url, String body);
    public String sendPut(String url, String body);
    public String sendDelete(String url);
}
