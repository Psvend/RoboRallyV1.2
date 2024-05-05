Feature: Move Player on a game board
    As a player
    I want to move my figure on a game board
    So that I can advance in the game

    Scenario: Player moves to specific position
        Given a players figure at position "0,0" facing East
        When the player clicks on the position "5,6"
        Then the figure is at position "5,6" facing East

    Scenario: Player moves forward
        Given a players figure at position "0,0" facing South
        When the figure moves forward
        Then the figure is at position "0,1" facing South

    Scenario: Player moves Fast Forward
        Given a players figure at position "3,3" facing North
        When the figure moves Fast Forward
        Then the figure is at position "3,1" facing North

    Scenario: Player turns right
        Given a players figure at position "0,0" facing South
        When the figure turns right
        Then the figure is at position "0,0" facing West

    Scenario: Player turns left
        Given a players figure at position "0,0" facing South
        When the figure turns left
        Then the figure is at position "0,0" facing East




