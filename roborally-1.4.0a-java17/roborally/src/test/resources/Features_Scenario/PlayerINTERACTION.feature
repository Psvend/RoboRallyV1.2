Feature: Player can now choose
    As a player,
    I want to be able to choose my moves,
    so that I can play and advance in the game.

Scenario: Player can choose moves
    Given the game is in ACTIVATION phase and the player has "Turn LEFT_RIGHT" card in the current register
    When the player presses the button "Execute Current Register"
    Then the game phase changes to PLAYER_INTERACTION and options buttons: "Turn Left" and "Turn Right" are displayed




