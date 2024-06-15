/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import org.jetbrains.annotations.NotNull;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

import java.util.ArrayList;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Player extends Subject {

    final public static int NO_REGISTERS = 5;
    final public static int NO_CARDS = 8;

    final public Board board;

    private String name;
    private String color;

    private Space space;
    private Heading heading = SOUTH;

    private CommandCardField[] program;
    private CommandCardField[] cards;
    private List<Integer> tokens = new ArrayList<>();

    private int energyReserve;   //the players own energy reserve
     


    public Player(@NotNull Board board, String color, @NotNull String name) {
        this.board = board;
        this.name = name;
        this.color = color;
        this.space = null;
        this.energyReserve = 0;

        program = new CommandCardField[NO_REGISTERS];
        for (int i = 0; i < program.length; i++) {
            program[i] = new CommandCardField(this);
        }

        cards = new CommandCardField[NO_CARDS];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new CommandCardField(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        notifyChange();
        if (space != null) {
            space.playerChanged();
        }
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        Space oldSpace = this.space;
        if (space != oldSpace &&
                (space == null || space.board == this.board)) {
            this.space = space;
            if (oldSpace != null) {
                oldSpace.setPlayer(null);
            }
            if (space != null) {
                space.setPlayer(this);
            }
            notifyChange();
        }
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(@NotNull Heading heading) {
        if (heading != this.heading) {
            this.heading = heading;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public CommandCardField getProgramField(int i) {
        return program[i];
    }

    public CommandCardField getCardField(int i) {
        return cards[i];
    }


    /**
     * @author Petrine
     * @return the energy reserve of a player
     */
    public int getEnergyReserve() {
        return this.energyReserve;
    }    



     /**
     * @author Petrine & Louise
     * @return the set energy reserve of a player
     */
    public void setEnergyReserve(Integer i) {
        this.energyReserve = i;
    }

    public List<Integer> getTokens(){
        return tokens;
    }

    public void setTokens(List<Integer> tokens) {
        this.tokens = sortTokens(tokens);
    }

    public List<Integer> sortTokens(List<Integer> tokens) {
        List<Integer> compare = new ArrayList<>();
        for(int i = 0; i <= tokens.size(); i++){
            compare.add(i+1);
        }

        List<Integer> tempTokens = new ArrayList<>();
        for(int k = 0; k<= tokens.size(); k++){
            for(int j = 0; j <= tokens.size(); j++){
                if(tokens.get(j) == (compare.get(k))) {
                    tempTokens.add(tokens.get(j));
                } else {}
            }
        }

        tokens = tempTokens;

        return tokens;
    }
}

