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
package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.*;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.*;

import javax.print.DocFlavor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class LoadBoard {

    private static final String BOARDSFOLDER = "boards";
    private static final String DEFAULTBOARD = "defaultboard";
    private static final String JSON_EXT = "json";
    private static final int DEFAULT_BOARD_WIDTH = 8;
    private static final int DEFAULT_BOARD_HEIGHT = 8;





    public static Board loadBoard(String boardname) {

        if (boardname == null) {
            boardname = DEFAULTBOARD;
        }
        //File filename = new File(boardname);
        ClassLoader classLoader = LoadBoard.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(BOARDSFOLDER + "/" + boardname + "." + JSON_EXT);
        if (inputStream == null) {
            // TODO these constants should be defined somewhere
            return new Board(DEFAULT_BOARD_WIDTH, DEFAULT_BOARD_HEIGHT);
        }

		// In simple cases, we can create a Gson object with new Gson():
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();

		Board result;
		//FileReader fileReader = null;
        JsonReader reader = null;
		try {
			//fileReader = new FileReader(filename);
			reader = gson.newJsonReader(new InputStreamReader(inputStream));
			BoardTemplate template = gson.fromJson(reader, BoardTemplate.class);

			result = new Board(template.width, template.height);
			for (SpaceTemplate spaceTemplate: template.spaces) {
			    Space space = result.getSpace(spaceTemplate.x, spaceTemplate.y);
			    if (space != null) {
                    space.getActions().addAll(spaceTemplate.actions);
                    space.getWalls().addAll(spaceTemplate.walls);
                }
            }
            PlayerTemplate playerTemplate = gson.fromJson(reader, PlayerTemplate.class);
            if (playerTemplate != null) {
                Player newPlayer = new Player(result, playerTemplate.getName(), playerTemplate.getColor());
                if (playerTemplate.getSpaceX() >= 0 && playerTemplate.getSpaceX() < template.width && playerTemplate.getSpaceY() >= 0 && playerTemplate.getSpaceY() < template.height) {
                    Space space = result.getSpace(playerTemplate.getSpaceX(), playerTemplate.getSpaceY());
                    if (space != null) {
                        newPlayer.setSpace(space);
                        result.addPlayer(newPlayer);
                    }
                }
                newPlayer.setHeading(Heading.valueOf(playerTemplate.getHeading()));
                // Assuming CommandCardFieldTemplate is similar to CommandCardField
                for (int i = 0; i < Player.NO_REGISTERS; i++) {
                    CommandCardField field = newPlayer.getProgramField(i);
                    CommandCardFieldTemplate fieldTemplate = playerTemplate.getProgram().get(i);
                    // Populate field with relevant data
                }
                // Similarly for cards
            }
			reader.close();
			return result;
		} catch (IOException e1) {
            if (reader != null) {
                try {
                    reader.close();
                    inputStream = null;
                } catch (IOException e2) {}
            }
            if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e2) {}
			}
		}
		return null;
    }

    public static void saveBoard(Board board, String name) {
        BoardTemplate template = new BoardTemplate();
        template.width = board.width;
        template.height = board.height;

        for (int i=0; i<board.width; i++) {
            for (int j=0; j<board.height; j++) {
                Space space = board.getSpace(i,j);
                if (!space.getWalls().isEmpty() || space.getActions().isEmpty()) {
                    SpaceTemplate spaceTemplate = new SpaceTemplate();
                    spaceTemplate.x = space.x;
                    spaceTemplate.y = space.y;
                    spaceTemplate.actions.addAll(space.getActions());
                    spaceTemplate.walls.addAll(space.getWalls());
                    template.spaces.add(spaceTemplate);
                }
            }
        }
        // Save all players
        int numberOfPlayers = board.getPlayersNumber();
        for(int i = 0; i < numberOfPlayers; i++) {
            Player player = board.getPlayer(i);
            PlayerTemplate playerTemplate = new PlayerTemplate();
            playerTemplate.setName(player.getName());
            playerTemplate.setColor(player.getColor());
            if (player.getSpace() != null) {
                playerTemplate.setSpaceX(player.getSpace().x);
                playerTemplate.setSpaceY(player.getSpace().y);
            }
            playerTemplate.setHeading(player.getHeading().toString());

            // Assuming CommandCardFieldTemplate is similar to CommandCardField
            List<CommandCardFieldTemplate> cardTemplates = new ArrayList<>();
            for (int j = 0; j < Player.NO_CARDS; j++) {
                CommandCardField field = player.getCardField(j);
                CommandCardFieldTemplate fieldTemplate = new CommandCardFieldTemplate();
                // Populate fieldTemplate with relevant data

                CommandCard card = field.getCard(); // Get the CommandCard in this field
                boolean isVisible = field.isVisible(); // Check if the field is visible

                // Assuming CommandCardFieldTemplate has setCard and setVisible methods
                fieldTemplate.setCard(card); // Set a new CommandCard to this field
                fieldTemplate.setVisible(isVisible); // Set the visibility of this field

                cardTemplates.add(fieldTemplate);
            }
            playerTemplate.setCards(cardTemplates);
            // Similarly for cards
            // Save program
            List<CommandCardFieldTemplate> programTemplates = new ArrayList<>();
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                CommandCardFieldTemplate fieldTemplate = new CommandCardFieldTemplate();
                // Populate fieldTemplate with relevant data

                CommandCard card = field.getCard(); // Get the CommandCard in this field
                boolean isVisible = field.isVisible(); // Check if the field is visible

                // Assuming CommandCardFieldTemplate has setCard and setVisible methods
                fieldTemplate.setCard(card); // Set a new CommandCard to this field
                fieldTemplate.setVisible(isVisible); // Set the visibility of this field

                programTemplates.add(fieldTemplate);
            }
            playerTemplate.setProgram(programTemplates);


            template.players.add(playerTemplate);
        }
        Phase currentPhase = board.getPhase();
        PhaseTemplate phaseTemplate = new PhaseTemplate();
        phaseTemplate.setPhase(currentPhase.name());
        template.phases.add(phaseTemplate);

        ClassLoader classLoader = LoadBoard.class.getClassLoader();
        // TODO: this is not very defensive, and will result in a NullPointerException
        //       when the folder "resources" does not exist! But, it does not need
        //       the file "simpleCards.json" to exist!
        String filename =
                classLoader.getResource(BOARDSFOLDER).getPath() + "/" + name + "." + JSON_EXT;

        // In simple cases, we can create a Gson object with new:
        //
        //   Gson gson = new Gson();
        //
        // But, if you need to configure it, it is better to create it from
        // a builder (here, we want to configure the JSON serialisation with
        // a pretty printer):
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>()).
                setPrettyPrinting();
        Gson gson = simpleBuilder.create();

        FileWriter fileWriter = null;
        JsonWriter writer = null;
        try {
            fileWriter = new FileWriter(filename);
            writer = gson.newJsonWriter(fileWriter);
            gson.toJson(template, template.getClass(), writer);
            writer.close();
        } catch (IOException e1) {
            if (writer != null) {
                try {
                    writer.close();
                    fileWriter = null;
                } catch (IOException e2) {}
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e2) {}
            }
        }
    }

}
