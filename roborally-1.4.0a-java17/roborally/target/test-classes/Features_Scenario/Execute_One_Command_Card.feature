Feature: Execute One Command Card
    As a player
    I want to execute One  Command Card at the time
    So that I can see all my moves on a board

    Scenario: Player  executes one  Command Card
        Given Player has "Moves Forward" card in a current register
              And Player is at position "0,0" facing South
        When player clicks on button "Execute Register"
        Then "Moves Forward" card is executed
              And player is at position "0,1" facing South