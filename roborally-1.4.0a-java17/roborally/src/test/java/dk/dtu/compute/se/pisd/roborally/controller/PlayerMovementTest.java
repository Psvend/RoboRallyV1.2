package dk.dtu.compute.se.pisd.roborally.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;

public class PlayerMovementTest extends ApplicationTest{
    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;
    private GameController gameController;
    
    @BeforeEach
    void setUp(){
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 2; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        gameController.copyOfpriorityPlayers.add(0, board.getPlayer(0));
        gameController.copyOfpriorityPlayers.add(1, board.getPlayer(1));
        gameController.priorityPlayers.addAll(gameController.copyOfpriorityPlayers);
        board.setCurrentPlayer(gameController.priorityPlayers.get(0));
    }

    @AfterEach
    void tearDown(){
        gameController = null;
    }

    @Test
    void OutsideOfBoard_Test(){

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
