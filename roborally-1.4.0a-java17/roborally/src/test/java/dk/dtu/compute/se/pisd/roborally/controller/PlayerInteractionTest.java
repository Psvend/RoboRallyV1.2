package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.PlayerView;
import javafx.scene.control.Button;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerInteractionTest extends ApplicationTest {
    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;
    private GameController gameController;

    @BeforeEach
    void setUp() {
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
    void tearDown() {
        gameController = null;
    }

    @Test
    void Execute_Interactive_Command_Card(){
        //Given the game is in ACTIVATION phase and the player has "LEFT_RIGHT" card in the current register
        Board board = gameController.board;
        Player player = board.getCurrentPlayer();
        player.getProgramField(0).setCard(new CommandCard(Command.OPTION_LEFT_RIGHT));
        player.setSpace(board.getSpace(0, 0));
        player.setHeading(Heading.SOUTH);


        board.setPhase(Phase.ACTIVATION);

        //When the player presses the button "Execute Current Register"
        gameController.executeStep();


        //Then the game phase changes to PLAYER_INTERACTION and the interactive command executed is "OPTION_LEFT_RIGHT"
        assertEquals(Phase.PLAYER_INTERACTION, board.getPhase(), "Game phase should change to PLAYER_INTERACTION");
        assertEquals(true, gameController.command.isInteractive(), "The command card should be interactive");
        assertEquals(Command.OPTION_LEFT_RIGHT, gameController.command, "The command should be OPTION_LEFT_RIGHT");
    }
    @Test
    void Display_Interactive_Panel() {
       // Given the player has "LEFT RIGHT" card in the current register
        Board board = gameController.board;
        Player player = board.getCurrentPlayer();
        CommandCard commandCard = new CommandCard(Command.OPTION_LEFT_RIGHT);
        //gameController.command = commandCard.command;
        player.getProgramField(0).setCard(commandCard);
        player.setSpace(board.getSpace(0, 0));
        player.setColor("red");
        player.setHeading(Heading.SOUTH);
        board.setPhase(Phase.ACTIVATION);

        PlayerView playerView = new PlayerView(gameController, player);

        //When the player presses the button "Execute Current Register"
        playerView.updateView(board);
        gameController.executeStep();


        //Then Interactive panel should be displayed with
        //        the option buttons "Turn Left" and "Turn Right"
        for (int i = 0; i < commandCard.command.getOptions().size(); i++) {
            Command option = commandCard.command.getOptions().get(i);
            Button button = (Button) playerView.getPlayerInteractionPanel().getChildren().get(i);


            assertEquals(Phase.PLAYER_INTERACTION, board.getPhase(), "Game phase should change to PLAYER_INTERACTION");
            assertEquals(playerView.getPlayerInteractionPanel().getChildren().size(), commandCard.command.getOptions().size(),
                    "The number of buttons should be equal to the number of options");
            assertEquals(button.getText(), option.displayName, "The button should have the same text as the option");
        }
    }
    @Test
    void Execute_Program_Interactive_Command() {
        //Given the player has cards "Left Right", "Move Forward", "Turn Right"
        // in the register
        Board board = gameController.board;
        Player player1 = board.getCurrentPlayer();
        //Player player2 = board.getPlayer(1);
        CommandCard interactiveCard = new CommandCard(Command.OPTION_LEFT_RIGHT.getOptions().get(0));
        player1.getProgramField(0).setCard(interactiveCard);
        player1.getProgramField(1).setCard(new CommandCard(Command.FORWARD));
        player1.getProgramField(2).setCard(new CommandCard(Command.RIGHT));
        player1.setColor("Green");
        player1.setEnergyReserve(0);
        player1.setSpace(board.getSpace(2, 3));
        player1.setHeading(Heading.SOUTH);
        board.setPhase(Phase.ACTIVATION);
        PlayerView playerView = new PlayerView(gameController, player1);
        gameController.setPlayerView(player1, playerView);

        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setColor("Brown");
        PlayerView playerView2 = new PlayerView(gameController, player2);
        gameController.setPlayerView(player2, playerView2);


        //When the player presses the button "Execute Program" and chooses the first(Turn Left) option
        gameController.executePrograms();

        //Then the game phase changes to PROGRAMMING and player should be at position "3,3" facing South
        assertEquals(Phase.PROGRAMMING, board.getPhase(), "Game phase should change to PROGRAMMING");

        Space expectedSpace = board.getSpace(3, 3);
        assertEquals(expectedSpace, player1.getSpace());

        assertEquals(Heading.SOUTH, player1.getHeading());
        assertFalse(board.isStepMode());

    }


    @Test
    void Execute_Program(){
        // Player has finished programming phase with
        // 3 command cards "Turn Left", "Move Forward", "Turn Right"
        //And Player is at position "2,3" facing South
        Board board = gameController.board;
        Player player = board.getCurrentPlayer();
        player.getProgramField(0).setCard(new CommandCard(Command.LEFT));
        player.getProgramField(1).setCard(new CommandCard(Command.FORWARD));
        player.getProgramField(2).setCard(new CommandCard(Command.RIGHT));
        player.setSpace(board.getSpace(2, 3));
        player.setColor("yellow");
        player.setHeading(Heading.SOUTH);

        PlayerView playerView = new PlayerView(gameController, player);
        gameController.setPlayerView(player, playerView);
        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setColor("Brown");
        PlayerView playerView2 = new PlayerView(gameController, player2);
        gameController.setPlayerView(player2, playerView2);
        board.setPhase(Phase.ACTIVATION);

        //When Player clicks on button "Execute Program"
        gameController.executePrograms();

        //Then Player should be at position "3,3" facing South
        Space expectedSpace = gameController.board.getSpace(3, 3);
        assertEquals(expectedSpace, player.getSpace());

        assertEquals(Heading.SOUTH, player.getHeading());
        assertFalse(board.isStepMode());
    }

}
