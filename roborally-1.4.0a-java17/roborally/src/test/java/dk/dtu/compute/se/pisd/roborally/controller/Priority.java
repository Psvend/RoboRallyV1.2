package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Priority extends ApplicationTest {
    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 4; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }

    @Test
    void testPriority() {
        // Given there are 4 players in a game, and they are at position (0,0), (1,1), (2,2), (3,3)
        Board board = gameController.board;
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);
        Player player3 = board.getPlayer(2);
        Player player4 = board.getPlayer(3);

        player1.setSpace(board.getSpace(0, 0));
        player2.setSpace(board.getSpace(1, 1));
        player3.setSpace(board.getSpace(2, 2));
        player4.setSpace(board.getSpace(3, 3));

        // When the game controller determines the players priority
        gameController.priorityPlayers.addAll(gameController.determiningPriority());

        // Then the players should have the following priority: player4, player3, player2, player1
        assertEquals(player4, gameController.priorityPlayers.get(0), "Player 4 should have the highest priority");
        assertEquals(player3, gameController.priorityPlayers.get(1), "Player 3 should have the second highest priority");
        assertEquals(player2, gameController.priorityPlayers.get(2), "Player 2 should have the third highest priority");
        assertEquals(player1, gameController.priorityPlayers.get(3), "Player 1 should have the lowest priority");
    }

    @Test
    void priorityAfterActivetionPhase(){
        // Given there are 4 players in a game, and they are at position (0,0), (1,1), (2,2), (3,3) and they have a command card in their register
        Board board = gameController.board;
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);
        Player player3 = board.getPlayer(2);
        Player player4 = board.getPlayer(3);

        player1.setSpace(board.getSpace(0, 0));
        player1.getProgramField(0).setCard(new CommandCard(Command.FORWARD));

        player2.setSpace(board.getSpace(1, 1));
        player2.getProgramField(0).setCard(new CommandCard(Command.LEFT));

        player3.setSpace(board.getSpace(2, 2));
        player3.getProgramField(0).setCard(new CommandCard(Command.FAST_FORWARD));

        player4.setSpace(board.getSpace(3, 3));
        player4.getProgramField(0).setCard(new CommandCard(Command.RIGHT));


        gameController.priorityPlayers.addAll(gameController.determiningPriority());
        board.setCurrentPlayer(gameController.priorityPlayers.get(0));

        // When players executes their command cards
        board.setPhase(Phase.ACTIVATION);
        gameController.executeStep();
        gameController.executeStep();
        gameController.executeStep();


        gameController.priorityPlayers.addAll(gameController.determiningPriority());

        
        // Then the players should have the following priority: player3, player4, player2, player1
        //assertEquals(player3, board.getCurrentPlayer(), "Player 3 should be the current player now");
        assertEquals(player3.getName(), gameController.priorityPlayers.get(1).getName(), "Player 3 should have the highest priority");
        assertEquals(player4, gameController.priorityPlayers.get(2), "Player 4 should have the second highest priority");
        assertEquals(player2, gameController.priorityPlayers.get(3), "Player 2 should have the third highest priority");
        assertEquals(player1, gameController.priorityPlayers.get(4), "Player 1 should have the lowest priority");
    }
}
