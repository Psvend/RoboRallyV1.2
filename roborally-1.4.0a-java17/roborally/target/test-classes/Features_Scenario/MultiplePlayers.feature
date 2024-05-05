Feature: Multiple Players Game
    As a player
    I want to be able to play games with multiple players
    So that I can play with my friends

    Scenario: Two players play a game
        Given the "Player number" window is open
        When I choose the number "2" in the number field
            And I click the "Okay" button
        Then I should see a game board with two players

    Scenario: Three players play a game
        Given the "Player number" window is open
        When I choose the number "3" in the number field
            And I click the "Okay" button
        Then I should see a game board with three players

    Scenario: Four players play a game
        Given the "Player number" window is open
        When I choose the number "4" in the number field
            And I click the "Okay" button
        Then I should see a game board with four players

    Scenario: Five players play a game
        Given the "Player number" window is open
        When I choose the number "5" in the number field
            And I click the "Okay" button
        Then I should see a game board with five players

    Scenario: Six players play a game
        Given the "Player number" window is open
        When I choose the number "6" in the number field
            And I click the "Okay" button
        Then I should see a game board with six players