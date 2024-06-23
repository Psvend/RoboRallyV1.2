package dk.dtu.compute.se.pisd.roborally.controller;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.EAST;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.NORTH;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
import static dk.dtu.compute.se.pisd.roborally.model.Phase.ACTIVATION;

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

        //We set Player 1 to face away from the pitfall at 0,3
        //and test, if the pitfall moves the player to respawnPoint, if the player moves backwards.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setHeading(EAST);
        player1.getProgramField(0).setCard(new CommandCard(Command.BACKWARD));
        player1.setEnergyReserve(0);

        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executeStep();

        Assert.assertEquals(gameController.findRespawnPoint(), player1.getSpace());
    }

    @Test
    void OutsideBoard_Test2(){
        Board board = gameController.board;

        //We set Player 1 to face towards from the pitfall at 0,3
        //and test, if the pitfall moves the player to respawnPoint and cancels movement, regardless of how many spaces the player should move.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setHeading(NORTH);
        player1.getProgramField(0).setCard(new CommandCard(Command.MOVE_THREE));
        player1.setEnergyReserve(0);

        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executeStep();

        Assert.assertEquals(gameController.findRespawnPoint(), player1.getSpace());
    }




    @Test
    void PushPlayerForward_Test(){

    }

    @Test
    void PushPlayerBackwards_Test(){

    }

    @Test
    void RespawnPush_Test(){

    }

    @Test
    void CheckPoint_Test(){

    }

    @Test
    void Winner_Test(){

    }
}
