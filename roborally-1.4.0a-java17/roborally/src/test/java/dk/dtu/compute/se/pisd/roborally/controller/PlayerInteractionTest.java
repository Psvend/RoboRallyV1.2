package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.view.PlayerView;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class PlayerInteractionTest {
    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;
    private PlayerView playerView;

    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }

    @Given("the game is in ACTIVATION phase and the player has {string} card in the current register")
    public void the_game_is_in_activation_phase_and_the_player_has_card_in_the_current_register(String string) {
        Board board = gameController.board;
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);
        board.setCurrentPlayer(player1);
        board.setPhase(Phase.ACTIVATION);

        throw new io.cucumber.java.PendingException();
    }
    @When("the player presses the button {string}")
    public void the_player_presses_the_button(String string) {
        GridPane programPane = playerView.programPane.getChildren();
        Button button = playerView.getButton("");
        button.fire();

        throw new io.cucumber.java.PendingException();
    }
    @Then("the game phase changes to PLAYER_INTERACTION and options buttons: {string} and {string} are displayed")
    public void the_game_phase_changes_to_player_interaction_and_options_buttons_and_are_displayed(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }






}
