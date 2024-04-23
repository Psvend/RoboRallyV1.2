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
    private List<CommandCardFieldTemplate> program= new ArrayList<>();
    private List<CommandCardFieldTemplate> cards =new ArrayList<>();


}
