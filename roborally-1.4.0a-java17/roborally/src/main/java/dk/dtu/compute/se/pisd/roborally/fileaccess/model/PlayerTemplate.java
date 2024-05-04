package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

import dk.dtu.compute.se.pisd.roborally.model.CommandCardField;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import java.util.ArrayList;
import java.util.List;

public class PlayerTemplate {
    public String name;
    public String color;

    public int spaceX;
    public int spaceY;
    private String heading;

    public int energyReserve;
    private List<CommandCardFieldTemplate> program= new ArrayList<>();
    private List<CommandCardFieldTemplate> cards =new ArrayList<>();

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

    public PlayerTemplate(){

    }

}

