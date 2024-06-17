package dk.dtu.compute.se.pisd.roborally.network;

public class LobbyService {
    private HttpRequestService httpRequestService;


    public LobbyService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    public String getLobby() {
        return httpRequestService.sendGet("http://localhost:3306/api/lobby");
    }

    public String createLobby(String body) {
        return httpRequestService.sendPost("http://localhost:3306/api/lobby", body);
    }

    public String joinLobby(String body) {
        return httpRequestService.sendPut("http://localhost:3306/api/getGameById/{game_id}", body);
    }

    public String leaveLobby(String body) {
        return httpRequestService.sendDelete("http://localhost:3306/api/lobby");
    }
}
