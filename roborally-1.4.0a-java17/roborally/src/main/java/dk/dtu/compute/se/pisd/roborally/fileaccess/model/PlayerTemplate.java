package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Benjamin
 * @param PlayerTemplate
 * this class is a template for the player and to save the player in the json file.
 */


public class PlayerTemplate {
    public String name;
    public String color;

    public int spaceX;
    public int spaceY;
    private String heading;

    public int energyReserve;
    private List<CommandCardFieldTemplate> program= new ArrayList<>();
    private List<CommandCardFieldTemplate> cards =new ArrayList<>();
    private List<Integer> tokens;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSpaceX() {
        return spaceX;
    }

    public void setSpaceX(int spaceX) {
        this.spaceX = spaceX;
    }

    public int getSpaceY() {
        return spaceY;
    }

    public void setSpaceY(int spaceY) {
        this.spaceY = spaceY;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<CommandCardFieldTemplate> getProgram() {
        return program;
    }

    public void setProgram(List<CommandCardFieldTemplate> program) {
        this.program = program;
    }

    public List<CommandCardFieldTemplate> getCards() {
        return cards;
    }

    public void setCards(List<CommandCardFieldTemplate> cards) {
        this.cards = cards;
    }
    public int getEnergyReserve() {
        return energyReserve;
    }
    public void setEnergyReserve(int energyReserve) {
        this.energyReserve = energyReserve;
    }

    public List<Integer> getTokens(){
        return tokens;
    }

    public void setTokens(List<Integer> tokens){
        this.tokens = tokens;
        Collections.sort(this.tokens);
    }

    public PlayerTemplate(){

    }
}
