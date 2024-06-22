package dk.dtu.compute.se.pisd.roborally.controller;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.EAST;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.NORTH;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.WEST;
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


public class BoardElementsTest extends ApplicationTest {
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
    void Wall_Test1(){
        Board board = gameController.board;

        //We set Player 1 to face the wall at 1,1 and test if the wall block every movement commandcard going that direction.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setHeading(SOUTH);
        player1.setColor("red");
        player1.getProgramField(0).setCard(new CommandCard(Command.FORWARD));
        player1.getProgramField(1).setCard(new CommandCard(Command.MOVE_TWO));
        player1.getProgramField(2).setCard(new CommandCard(Command.MOVE_THREE));
        player1.getProgramField(3).setCard(new CommandCard(Command.FAST_FORWARD));
        player1.setEnergyReserve(0);

        //We do the same deal with player2 and try from the other side.
        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setSpace(board.getSpace(0, 1));
        player2.setColor("green");
        player2.setHeading(NORTH);
        player2.getProgramField(0).setCard(new CommandCard(Command.FORWARD));
        player2.getProgramField(1).setCard(new CommandCard(Command.MOVE_TWO));
        player2.getProgramField(2).setCard(new CommandCard(Command.MOVE_THREE));
        player2.getProgramField(3).setCard(new CommandCard(Command.FAST_FORWARD));
        player2.setEnergyReserve(0);

        PlayerView playerView1 = new PlayerView(gameController, player1);
        PlayerView playerView2 = new PlayerView(gameController, player2);
        gameController.setPlayerView(player1, playerView1);
        gameController.setPlayerView(player2, playerView2);

        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executePrograms();

        Assert.assertEquals(board.getSpace(0, 0), player1.getSpace());
        Assert.assertEquals(board.getSpace(0, 1), player2.getSpace());
    }

    @Test
    void Wall_Test2(){
        Board board = gameController.board;

        //We set Player 1 to face away from the wall at 1,1 and test if the wall block backwards movement.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setColor("red");
        player1.setHeading(NORTH);
        player1.getProgramField(0).setCard(new CommandCard(Command.BACKWARD));
        player1.setEnergyReserve(0);

        //We do the same deal with player2 and try from the other side.
        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setSpace(board.getSpace(0, 1));
        player2.setColor("green");
        player2.setHeading(SOUTH);
        player2.getCardField(0).setCard(new CommandCard(Command.BACKWARD));
        player2.setEnergyReserve(0);

        PlayerView playerView1 = new PlayerView(gameController, player1);
        PlayerView playerView2 = new PlayerView(gameController, player2);
        gameController.setPlayerView(player1, playerView1);
        gameController.setPlayerView(player2, playerView2);


        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executePrograms();

        Assert.assertEquals(board.getSpace(0, 0), player1.getSpace());
        Assert.assertEquals(board.getSpace(0, 1), player2.getSpace());
    }

    @Test
    void Pitfall_Test1(){
        Board board = gameController.board;

        //We set Player 1 to face away from the pitfall at 0,3
        //and test, if the pitfall moves the player to respawnPoint, if the player moves backwards.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setSpace(board.getSpace(0, 2));
        player1.setHeading(NORTH);
        player1.getProgramField(0).setCard(new CommandCard(Command.BACKWARD));
        player1.setEnergyReserve(0);

        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executeStep();

        Assert.assertEquals(gameController.findRespawnPoint(), player1.getSpace());
    }

    @Test
    void Pitfall_Test2(){
        Board board = gameController.board;

        //We set Player 1 to face towards from the pitfall at 0,3
        //and test, if the pitfall moves the player to respawnPoint and cancels movement, regardless of how many spaces the player should move.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setSpace(board.getSpace(0, 4));
        player1.setHeading(NORTH);
        player1.getProgramField(0).setCard(new CommandCard(Command.MOVE_THREE));
        player1.setEnergyReserve(0);

        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executeStep();

        Assert.assertEquals(gameController.findRespawnPoint(), player1.getSpace());
    }

    @Test
    void ConveyorBelt_Test(){
        Board board = gameController.board;

        //We set Player 1 on the circular conveyorBelt series at 4,4.
        //We will test whether the different conveyorBelts will push the player as expected test, if every register is executed.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setSpace(board.getSpace(4, 4));
        player1.setColor("red");
        player1.setHeading(SOUTH);
        player1.setEnergyReserve(0);

        //We find Player 2 on the circular conveyorBelt series at 1,1, it is already placed here at the start.
        //We will test whether the different conveyorBelts will push the player as expected test, if every register is executed.
        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setColor("green");
        player2.setHeading(NORTH);
        player2.setEnergyReserve(0);

        //Needed to executePrograms()
        PlayerView playerView1 = new PlayerView(gameController, player1);
        PlayerView playerView2 = new PlayerView(gameController, player2);
        gameController.setPlayerView(player1, playerView1);
        gameController.setPlayerView(player2, playerView2);

        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executePrograms();

        Assert.assertEquals(board.getSpace(1, 2), player1.getSpace());
        Assert.assertEquals(board.getSpace(2, 5), player2.getSpace());
    }

    @Test
    void GearSpace_Test(){
        Board board = gameController.board;

        //We set Player 1 on the circular conveyorBelt series at 4,4.
        //We will test whether the different conveyorBelts will push the player as expected test, if every register is executed.
        Player player1 = board.getSpace(0,0).getPlayer();
        player1.setSpace(board.getSpace(7, 0));
        player1.setColor("red");
        player1.setHeading(SOUTH);
        player1.setEnergyReserve(0);

        //We find Player 2 on the circular conveyorBelt series at 1,1, it is already placed here at the start.
        //We will test whether the different conveyorBelts will push the player as expected test, if every register is executed.
        Player player2 = board.getSpace(1, 1).getPlayer();
        player2.setSpace(board.getSpace(7, 6));
        player2.setColor("green");
        player2.setHeading(NORTH);
        player2.setEnergyReserve(0);

        //Needed to executePrograms()
        PlayerView playerView1 = new PlayerView(gameController, player1);
        PlayerView playerView2 = new PlayerView(gameController, player2);
        gameController.setPlayerView(player1, playerView1);
        gameController.setPlayerView(player2, playerView2);

        //We activate the programming cards.
        board.setPhase(ACTIVATION);
        gameController.executePrograms();

        Heading expectedHeadingPlayer1 = EAST;
        Heading expectedHeadingPlayer2 = EAST;

        Assert.assertEquals(expectedHeadingPlayer1, player1.getHeading());
        Assert.assertEquals(expectedHeadingPlayer2, player2.getHeading());  
    }

    @Test
    void EnergySpace_Test(){

    }   
}
