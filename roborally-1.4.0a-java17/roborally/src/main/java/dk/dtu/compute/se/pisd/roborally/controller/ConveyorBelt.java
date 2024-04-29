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
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.controller.GameController.ImpossibleMoveException;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class ConveyorBelt extends FieldAction {

  private Heading heading;
  private int beltType;
  private Space space;
  private GameController gameController;

  public Heading getHeading() {
    return heading;
  }

  public void setHeading(Heading heading) {
    this.heading = heading;
  }

  public int getBeltType(){
    return beltType;
  }

  public void setBeltType(int beltType) {
      this.beltType = beltType;
  }

  @Override
  public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
    this.space = space;
    this.gameController = gameController;
    
    Space target = this.gameController.board.getNeighbour(this.space, getHeading());
    
    if (space.getPlayer() != null) {
      if(getBeltType()==1){
        try {
          this.gameController.moveToSpace(this.space.getPlayer(), this.space, this.getHeading());
          return true;
        } catch (ImpossibleMoveException e) {
          e.printStackTrace();
          return false;
        }
      } else if (getBeltType()==2){
          try {
            this.gameController.moveToSpace(this.space.getPlayer(), target, this.getHeading());
            return true;
          } catch (ImpossibleMoveException e) {
            e.printStackTrace();
            return false;
          }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
}
