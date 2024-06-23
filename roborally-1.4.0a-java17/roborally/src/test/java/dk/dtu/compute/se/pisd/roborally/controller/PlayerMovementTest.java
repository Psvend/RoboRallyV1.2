package dk.dtu.compute.se.pisd.roborally.controller;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.EAST;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.NORTH;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.WEST;
import static dk.dtu.compute.se.pisd.roborally.model.Phase.ACTIVATION;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.view.PlayerView;

public class PlayerMovementTest extends ApplicationTest{
    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;
    private GameController gameController;
    
    @BeforeEach
    void setUp(){
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 3; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        gameController.copyOfpriorityPlayers.add(0, board.getPlayer(0));
        gameController.copyOfpriorityPlayers.add(1, board.getPlayer(1));
        gameController.copyOfpriorityPlayers.add(2, board.getPlayer(2));
        gameController.priorityPlayers.addAll(gameController.copyOfpriorityPlayers);
        board.setCurrentPlayer(gameController.priorityPlayers.get(0));
    }

    @AfterEach
    void tearDown(){
        gameController = null;
    }

  @Test
    void OutsideBoard_Test1(){
        Board board = gameController.board;

        //We set Player 1 to face away from the edge of the board at 0,0
        //and test that the player is moved to respawnPoint, if the player moves backwards off the board -1,0 (not really what is happening).
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setHeading(EAST);
        player1.getProgramField(0).setCard(new CommandCard(Command.BACKWARD));
        player1.setEnergyReserve(0);

        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executeStep();

        //We assert if player's space is equal to that of the checkpoint
        Assert.assertEquals(gameController.findRespawnPoint(), player1.getSpace());
    }

    @Test
    void OutsideBoard_Test2(){
        Board board = gameController.board;

        //We set Player 1 to face the edge of the board at 0,0
        //and test that the player is moved to respawnPoint, if the player moves off the board 0,-1 (not really what is happening) and that further movement is cancelled.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setHeading(NORTH);
        player1.getProgramField(0).setCard(new CommandCard(Command.MOVE_THREE));
        player1.setEnergyReserve(0);

        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executeStep();

        //We assert if player's space is equal to that of the checkpoint
        Assert.assertEquals(gameController.findRespawnPoint(), player1.getSpace());
    }




    @Test
    void PushPlayerForward_Test(){
        Board board = gameController.board;

        //We set Player 1 to face player 2. Player 3 is behind player 2 in the direction that player 1 is heading. The whole column should move 3 spaces.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setSpace(board.getSpace(5, 0));
        player1.setColor("red");
        player1.setHeading(SOUTH);
        player1.getProgramField(0).setCard(new CommandCard(Command.MOVE_THREE));
        player1.setEnergyReserve(0);

        //Instatiating the player and changing the heading to show that it is the pusher's heading that's used throughout the whole push.
        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setSpace(board.getSpace(5, 1));
        player2.setColor("green");
        player2.setHeading(EAST);
        player2.setEnergyReserve(0);

        //Instatiating the player and changing the heading to show that it is the pusher's heading that's used throughout the whole push.
        Player player3 = board.getSpace(2, 2).getPlayer();
        player3.setSpace(board.getSpace(5, 2));
        player3.setColor("green");
        player3.setHeading(WEST);
        player3.setEnergyReserve(0);

        PlayerView playerView1 = new PlayerView(gameController, player1);
        PlayerView playerView2 = new PlayerView(gameController, player2);
        PlayerView playerView3 = new PlayerView(gameController, player3);
        gameController.setPlayerView(player1, playerView1);
        gameController.setPlayerView(player2, playerView2);
        gameController.setPlayerView(player3, playerView3);


        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executePrograms();

        //We assert the expected spaces.
        Assert.assertEquals(board.getSpace(5, 3), player1.getSpace());
        Assert.assertEquals(board.getSpace(5, 4), player2.getSpace());
        Assert.assertEquals(board.getSpace(5, 5), player3.getSpace());        
    }

    @Test
    void PushPlayerBackwards_Test(){
        Board board = gameController.board;

        //We set Player 1 to face away from player 2. Player 3 is behind player 2 in the opposite direction that player 1 is heading. The whole column should move 1 space.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setSpace(board.getSpace(5, 0));
        player1.setColor("red");
        player1.setHeading(NORTH);
        player1.getProgramField(0).setCard(new CommandCard(Command.BACKWARD));
        player1.setEnergyReserve(0);

        //Instatiating the player and changing the heading to show that it is the pusher's heading that's used throughout the whole push.
        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setSpace(board.getSpace(5, 1));
        player2.setColor("green");
        player2.setHeading(EAST);
        player2.setEnergyReserve(0);

        //Instatiating the player and changing the heading to show that it is the pusher's heading that's used throughout the whole push.
        Player player3 = board.getSpace(2, 2).getPlayer();
        player3.setSpace(board.getSpace(5, 2));
        player3.setColor("green");
        player3.setHeading(WEST);
        player3.setEnergyReserve(0);

        PlayerView playerView1 = new PlayerView(gameController, player1);
        PlayerView playerView2 = new PlayerView(gameController, player2);
        PlayerView playerView3 = new PlayerView(gameController, player3);
        gameController.setPlayerView(player1, playerView1);
        gameController.setPlayerView(player2, playerView2);
        gameController.setPlayerView(player3, playerView3);


        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executePrograms();

        //We assert the expected spaces.
        Assert.assertEquals(board.getSpace(5, 1), player1.getSpace());
        Assert.assertEquals(board.getSpace(5, 2), player2.getSpace());
        Assert.assertEquals(board.getSpace(5, 3), player3.getSpace());
    }

    @Test
    void RespawnPush_Test1(){
        Board board = gameController.board;

        //We set Player 1 to face the edge of the board at 0,0
        //and test that the player is moved to respawnPoint, if the player moves off the board 0,-1 (not really what is happening) and that further movement is cancelled.
        //If someone stands on the respawnPoint they're pushed in the direction marked by the respawnPoints arrow. This will repeat for everybody standing in that direction.        
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setSpace(board.getSpace(5, 7));
        player1.setColor("red");
        player1.setHeading(SOUTH);
        player1.getProgramField(0).setCard(new CommandCard(Command.MOVE_THREE));
        player1.setEnergyReserve(0);

        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setSpace(gameController.findRespawnPoint());
        player2.setColor("green");
        player2.setHeading(EAST);
        player2.setEnergyReserve(0);

        Player player3 = board.getSpace(2, 2).getPlayer();
        player3.setSpace(board.getSpace(3, 2));
        player3.setColor("green");
        player3.setHeading(WEST);
        player3.setEnergyReserve(0);

        PlayerView playerView1 = new PlayerView(gameController, player1);
        PlayerView playerView2 = new PlayerView(gameController, player2);
        PlayerView playerView3 = new PlayerView(gameController, player3);
        gameController.setPlayerView(player1, playerView1);
        gameController.setPlayerView(player2, playerView2);
        gameController.setPlayerView(player3, playerView3);


        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executePrograms();

        //the respawnPoint is located at 3,3 and has the heading NORTH, player 2 and 3 should be pushed in that direction.
        Assert.assertEquals(gameController.findRespawnPoint(), player1.getSpace());
        Assert.assertEquals(board.getSpace(3, 2), player2.getSpace());
        Assert.assertEquals(board.getSpace(3, 1), player3.getSpace());          
    }

    @Test
    void RespawnPush_Test2(){
        Board board = gameController.board;

        //We set Player 1 to face away from the edge of the board at 0,0
        //and test that the player is moved to respawnPoint, if the player moves off the board -1,0 (not really what is happening) and that further movement is cancelled.
        //If someone stands on the respawnPoint they're pushed in the direction marked by the respawnPoints arrow. This will repeat for everybody standing in that direction. 
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setSpace(board.getSpace(5, 7));
        player1.setColor("red");
        player1.setHeading(NORTH);
        player1.getProgramField(0).setCard(new CommandCard(Command.BACKWARD));
        player1.setEnergyReserve(0);

        //We do the same deal with player2 and try from the other side.
        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setSpace(gameController.findRespawnPoint());
        player2.setColor("green");
        player2.setHeading(EAST);
        player2.setEnergyReserve(0);

        //We do the same deal with player3 and try from the other side.
        Player player3 = board.getSpace(2, 2).getPlayer();
        player3.setSpace(board.getSpace(3, 2));
        player3.setColor("green");
        player3.setHeading(WEST);
        player3.setEnergyReserve(0);

        PlayerView playerView1 = new PlayerView(gameController, player1);
        PlayerView playerView2 = new PlayerView(gameController, player2);
        PlayerView playerView3 = new PlayerView(gameController, player3);
        gameController.setPlayerView(player1, playerView1);
        gameController.setPlayerView(player2, playerView2);
        gameController.setPlayerView(player3, playerView3);


        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executePrograms();

        //the respawnPoint is located at 3,3 and has the heading NORTH, player 2 and 3 should be pushed in that direction.
        Assert.assertEquals(gameController.findRespawnPoint(), player1.getSpace());
        Assert.assertEquals(board.getSpace(3, 2), player2.getSpace());
        Assert.assertEquals(board.getSpace(3, 1), player3.getSpace());
    }

    @Test
    void CheckPoint_Test(){
        Board board = gameController.board;

        //Player 1 collects the CheckpointToken at 5,7
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setSpace(board.getSpace(5, 6));
        player1.setColor("red");
        player1.setHeading(NORTH);
        player1.getProgramField(0).setCard(new CommandCard(Command.BACKWARD));
        player1.setEnergyReserve(0);

        //Player 2 collects the CheckpointToken at 0,7
        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setSpace(board.getSpace(1, 7));
        player2.setColor("green");
        player2.setHeading(WEST);
        player2.getProgramField(0).setCard(new CommandCard(Command.FORWARD));
        player2.setEnergyReserve(0);

        //To show no one starts with tokens
        Player player3 = board.getSpace(2, 2).getPlayer();
        player3.setSpace(board.getSpace(3, 2));
        player3.setColor("green");
        player3.setHeading(WEST);
        player3.setEnergyReserve(0);

        PlayerView playerView1 = new PlayerView(gameController, player1);
        PlayerView playerView2 = new PlayerView(gameController, player2);
        PlayerView playerView3 = new PlayerView(gameController, player3);

        gameController.setPlayerView(player1, playerView1);
        gameController.setPlayerView(player2, playerView2);
        gameController.setPlayerView(player3, playerView3);



        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executePrograms();

        //Expected token to be collected by each player
        List<Integer> expectedPlayer1 = new ArrayList<>();
        expectedPlayer1.add(2);
        List<Integer> expectedPlayer2 = new ArrayList<>();
        expectedPlayer2.add(1);

        //Expected- vs actual tokens
        Assert.assertEquals(expectedPlayer1, player1.getTokens());
        Assert.assertEquals(expectedPlayer2, player2.getTokens());
        Assert.assertEquals(new ArrayList<>(), player3.getTokens());
    }

    @Test
    void Winner_Test(){
        Board board = gameController.board;

        //We set player one to collect both tokens by starting at token 2 and waiting one turn to collect it then proceed to head to token 1.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setSpace(board.getSpace(5, 7));
        player1.setColor("red");
        player1.setHeading(WEST);
        player1.getProgramField(1).setCard(new CommandCard(Command.MOVE_TWO));
        player1.getProgramField(2).setCard(new CommandCard(Command.MOVE_THREE));
        player1.setEnergyReserve(0);

        //Just to show that there more than one player in the game. 
        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setSpace(board.getSpace(5, 5));
        player2.setColor("green");
        player2.setHeading(WEST);
        player2.setEnergyReserve(0);

        //Just to show that there more than one player in the game. 
        Player player3 = board.getSpace(2, 2).getPlayer();
        player3.setSpace(board.getSpace(3, 2));
        player3.setColor("green");
        player3.setHeading(WEST);
        player3.setEnergyReserve(0);

        PlayerView playerView1 = new PlayerView(gameController, player1);
        PlayerView playerView2 = new PlayerView(gameController, player2);
        PlayerView playerView3 = new PlayerView(gameController, player3);

        gameController.setPlayerView(player1, playerView1);
        gameController.setPlayerView(player2, playerView2);
        gameController.setPlayerView(player3, playerView3);

        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executePrograms();

        //Player number 1 should win
        int expectedPlayer = 1;

        Assert.assertEquals(expectedPlayer, board.getWinner());
    }
}
