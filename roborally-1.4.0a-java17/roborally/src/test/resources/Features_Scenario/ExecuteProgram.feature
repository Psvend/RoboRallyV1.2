Feature: Execute Program
    As a player
    I want to be able to execute all my command cards at once
    So that I can advance in the game quickly

    Scenario: Player  executes all command cards at once
        Given Player has finished programming phase with 3 command cards "Turn Left", "Move Forward", "Turn Right"
            And Player is at position "2,3" facing South
        When Player clicks on button "Execute Program"
        Then Player should be at position "3,3" facing South
