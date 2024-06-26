package dk.dtu.compute.se.pisd.roborally.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dtu.compute.se.pisd.roborally.client.Data.Games;
import dk.dtu.compute.se.pisd.roborally.client.Data.Players;
import dk.dtu.compute.se.pisd.roborally.client.Data.ProgCards;

import dk.dtu.compute.se.pisd.roborally.client.Data.Register;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class HttpClientAsynchronousPost {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2) // Use HTTP/2
            .connectTimeout(Duration.ofSeconds(10)) // Timeout after 10 seconds
            .build();

    // Static variable to store the current game
    public static Games currentGame;
    public static List<Games> availableGames;
    public static Players player;
    public static List<Players> playersList;
    public static int joinedPlayers;

    public static int phaseDone;

    public static ArrayList<Register> registersList;

    public static ArrayList<Register> playersRegisters;

    public static CompletableFuture<Games> addGame(Games game) {
        CompletableFuture<Games> futureGame = new CompletableFuture<>();

        try {
            // Convert Games object to JSON string
            String jsonBody = new ObjectMapper().writeValueAsString(game);

            // Prepare HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .uri(URI.create("http://localhost:8080/createGame")) // Replace with your endpoint
                    .header("Content-Type", "application/json")
                    .build();

            // Send HTTP POST request asynchronously
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            currentGame = objectMapper.readValue(response, Games.class);
                            futureGame.complete(currentGame); // Complete the future when the game is added
                            System.out.println("Game added: " + currentGame);
                        } catch (Exception e) {
                            e.printStackTrace();
                            futureGame.completeExceptionally(e); // Complete the future exceptionally if there was an error
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            futureGame.completeExceptionally(e); // Complete the future exceptionally if there was an error
        }

        return futureGame; // Return the future that will be completed in the future
    }

    public static CompletableFuture<Players> addPlayer(Players players) {
        CompletableFuture<Players> futurePlayer = new CompletableFuture<>();

        try {
            // Convert Players object to JSON string
            String jsonBody = new ObjectMapper().writeValueAsString(players);

            // Prepare HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .uri(URI.create("http://localhost:8080/addPlayer")) // Replace with your endpoint
                    .header("Content-Type", "application/json")
                    .build();

            // Send HTTP POST request asynchronously
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            player = objectMapper.readValue(response, Players.class);
                            futurePlayer.complete(player); // Complete the future when the player is added
                            System.out.println("Player added: " + player);
                        } catch (Exception e) {
                            e.printStackTrace();
                            futurePlayer.completeExceptionally(e); // Complete the future exceptionally if there was an error
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            futurePlayer.completeExceptionally(e); // Complete the future exceptionally if there was an error
        }

        return futurePlayer; // Return the future that will be completed in the future
    }


    //Get list of available games
    public static CompletableFuture<List<Games>> getAvailableGames() throws Exception {
        CompletableFuture<List<Games>> futureGame = new CompletableFuture<>();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/availableGames/0"))
                .header("Content-Type", "application/json")
                .build();


        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        availableGames = objectMapper.readValue(response, new TypeReference<List<Games>>() {
                        });
                        futureGame.complete(availableGames);

                        // Print the games (for demonstration)
                        /*for (Games game : gamesList) {
                            System.out.println(game);
                        }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                        futureGame.completeExceptionally(e);
                    }
                })
                .join(); // Block main thread to wait for completion (for demonstration)
        return futureGame;
    }


    //GET list of joined players
    public static CompletableFuture<List<Players>> getPlayers(int game_id) throws Exception {
        CompletableFuture<List<Players>> getPlayers = new CompletableFuture<>();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/findJoinedPlayers/"+game_id)) // Replace with your endpoint
                .header("Content-Type", "application/json")
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
               playersList = objectMapper.readValue(response, new TypeReference<List<Players>>() {
                });
                getPlayers.complete(playersList);
                for (Players player : playersList) {
                    System.out.println(player);
                }
                getPlayers.complete(playersList);

            } catch (Exception e) {
                e.printStackTrace();
                getPlayers.completeExceptionally(e);
            }
        })
                .join(); // Block main thread to wait for completion (for demonstration)
        return getPlayers;
    }

    public static CompletableFuture<Games> getCurrentGame(int game_id) {
        CompletableFuture<Games> futureGame = new CompletableFuture<>();

        try {
             // Prepare HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:8080/getGameById/"+game_id)) // Replace with your endpoint
                    .header("Content-Type", "application/json")
                    .build();


            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            currentGame = objectMapper.readValue(response, Games.class);
                            futureGame.complete(currentGame); // Complete the future when the game is added
                            System.out.println("Current game is: " + currentGame);
                        } catch (Exception e) {
                            e.printStackTrace();
                            futureGame.completeExceptionally(e); // Complete the future exceptionally if there was an error
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            futureGame.completeExceptionally(e); // Complete the future exceptionally if there was an error
        }

        return futureGame; // Return the future that will be completed in the future
    }

    public static CompletableFuture<Integer> getAmountOfJoinedPlayers(int game_id){
        CompletableFuture<Integer> futureJoined = new CompletableFuture<>();

        try {
            // Prepare HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:8080/getJoinedPlayers/"+game_id)) // Replace with your endpoint
                    .header("Content-Type", "application/json")
                    .build();


            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            joinedPlayers = objectMapper.readValue(response, Integer.class);
                            futureJoined.complete(joinedPlayers); // Complete the future when the game is added
                            System.out.println("Current game is: " + joinedPlayers);
                        } catch (Exception e) {
                            e.printStackTrace();
                            futureJoined.completeExceptionally(e); // Complete the future exceptionally if there was an error
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            futureJoined.completeExceptionally(e); // Complete the future exceptionally if there was an error
        }

        return futureJoined; // Return the future that will be completed in the future
    }


    public static CompletableFuture<Games> startGame(Games game) {
        CompletableFuture<Games> futureGame = new CompletableFuture<>();

        try {
            // Convert Games object to JSON string
            String jsonBody = new ObjectMapper().writeValueAsString(game);

            // Prepare HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .uri(URI.create("http://localhost:8080/startGame")) // Replace with your endpoint
                    .header("Content-Type", "application/json")
                    .build();

            // Send HTTP POST request asynchronously
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            currentGame = objectMapper.readValue(response, Games.class);
                            futureGame.complete(currentGame); // Complete the future when the game is added
                            System.out.println("Game added: " + currentGame);
                        } catch (Exception e) {
                            e.printStackTrace();
                            futureGame.completeExceptionally(e); // Complete the future exceptionally if there was an error
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            futureGame.completeExceptionally(e); // Complete the future exceptionally if there was an error
        }

        return futureGame; // Return the future that will be completed in the future
    }

    public static void addReristers(List<Register> registers) {
        CompletableFuture<ArrayList<Register>> futureRegisters = new CompletableFuture<>();

        try {
            // Convert Registers object to JSON string
            String jsonBody = new ObjectMapper().writeValueAsString(registers);

            // Prepare HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .uri(URI.create("http://localhost:8080/addRegisters")) // Replace with your endpoint
                    .header("Content-Type", "application/json")
                    .build();

            // Send HTTP POST request asynchronously
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            registersList = objectMapper.readValue(response, new TypeReference<List<Register>>() {
                            });
                            futureRegisters.complete(registersList); // Complete the future when the registers are added
                            System.out.println("Registers added: " + registersList);
                        } catch (Exception e) {
                            e.printStackTrace();
                            futureRegisters.completeExceptionally(e); // Complete the future exceptionally if there was an error
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            futureRegisters.completeExceptionally(e); // Complete the future exceptionally if there was an error
        }
    }

    public static CompletableFuture<Players> changePlayerPhaseStatus(Players putPlayer) {
        CompletableFuture<Players> futurePlayer = new CompletableFuture<>();

        try {
            // Convert Games object to JSON string
            String jsonBody = new ObjectMapper().writeValueAsString(putPlayer);

            // Prepare HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .uri(URI.create("http://localhost:8080/changePhase")) // Replace with your endpoint
                    .header("Content-Type", "application/json")
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            player = objectMapper.readValue(response, Players.class);
                            futurePlayer.complete(player); // Complete the future when the player is added
                            System.out.println("Player added: " + player);
                        } catch (Exception e) {
                            e.printStackTrace();
                            futurePlayer.completeExceptionally(e); // Complete the future exceptionally if there was an error
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            futurePlayer.completeExceptionally(e); // Complete the future exceptionally if there was an error
        }

        return futurePlayer; // Return the future that will be completed in the future
    }



    public static CompletableFuture<Integer> countPlayersWithPhaseStatusOne(int game_id){
        CompletableFuture<Integer> futurePlayers = new CompletableFuture<>();

        try {
            // Prepare HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:8080/countPlayersWithPhaseStatusOne/"+game_id))
                    .header("Content-Type", "application/json")
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            phaseDone = objectMapper.readValue(response, Integer.class);
                            futurePlayers.complete(phaseDone); // Complete the future when the registers are added
                            System.out.println("Registers added: " + phaseDone);
                        } catch (Exception e) {
                            e.printStackTrace();
                            futurePlayers.completeExceptionally(e); // Complete the future exceptionally if there was an error
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            futurePlayers.completeExceptionally(e); // Complete the future exceptionally if there was an error
        }
        return futurePlayers;
    }

    public static CompletableFuture<ArrayList<Register>> getPlayersRegisters(int game_id){
        CompletableFuture<ArrayList<Register>> futureRegisters = new CompletableFuture<>();

        try {
            // Prepare HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:8080/registerGet/"+game_id)) // Replace with your endpoint
                    .header("Content-Type", "application/json")
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            playersRegisters = objectMapper.readValue(response, new TypeReference<List<Register>>() {
                            });
                            futureRegisters.complete(playersRegisters); // Complete the future when the registers are added
                            System.out.println("Registers added: " + playersRegisters);
                        } catch (Exception e) {
                            e.printStackTrace();
                            futureRegisters.completeExceptionally(e); // Complete the future exceptionally if there was an error
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            futureRegisters.completeExceptionally(e); // Complete the future exceptionally if there was an error
        }
        return futureRegisters;
    }
}
