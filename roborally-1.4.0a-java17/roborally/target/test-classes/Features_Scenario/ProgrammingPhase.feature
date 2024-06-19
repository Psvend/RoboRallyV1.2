Feature: Programming Moves
    As a player
    I want to program my moves
    So that I can advance in the game

    Scenario: Player finishes programming phase
        Given a player has filled up the programming slots with Command Cards
        When player clicks on button "Finish Programming"
        Then Activation Phase starts