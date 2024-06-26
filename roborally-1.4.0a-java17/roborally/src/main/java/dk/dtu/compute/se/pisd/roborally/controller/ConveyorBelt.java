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

import dk.dtu.compute.se.pisd.roborally.model.Heading;

/**
 * ...
 *
 * @author Nikolaj
 * Handles the conveyor belt
 */
public class ConveyorBelt{

    private Heading heading;
    private int beltType;
    private String turnBelt = "";
    
    public Heading getHeading() {
      return heading;
    }
  
    public void setHeading(Heading heading) {
      this.heading = heading;
    }

    /**
   * @author Nikolaj
   * @return Int beltType
   * gets beltType. beltType defines whether the player moves one or two spaces, if they're standing on a ConveyorBelt.
   */
    public int getBeltType(){
      return beltType;
    }
  
      /**
   * @author Nikolaj
   * @param beltType
   * sets the beltType.
   */
    public void setBeltType(int beltType) {
        this.beltType = beltType;
    }
  
      /**
   * @author Nikolaj
   * @return String turnBelt
   * gets turnBelt. Mainly used in Spaceview to define a situation, where a different png for the ConveyorBelt is needed. (if the belt turns onto another)
   */
    public String getTurnBelt() {
      return turnBelt;
    }

   /**
    * @author Nikoaj
    * @param turnBelt
    * Sets the turnBelt string. Only 'RIGHT' and 'LEFT' is usable.
    */
    public void setTurnBelt(String turnBelt) {
      this.turnBelt = turnBelt;
    }
  }
