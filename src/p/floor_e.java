// This file is part of Mocha Doom.
// Copyright (C) 1993-1996  id Software, Inc.
// Copyright (C) 2010-2013  Victor Epitropou
// Copyright (C) 2016-2017  Alexandre-Xavier Labonté-Lamoureux
//
// Mocha Doom is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Mocha Doom is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along withMocha Doom.  If not, see <http://www.gnu.org/licenses/>.

package p;

public enum floor_e {
    // lower floor to highest surrounding floor
    lowerFloor,
    
    // lower floor to lowest surrounding floor
    lowerFloorToLowest,
    
    // lower floor to highest surrounding floor VERY FAST
    turboLower,
    
    // raise floor to lowest surrounding CEILING
    raiseFloor,
    
    // raise floor to next highest surrounding floor
    raiseFloorToNearest,

    // raise floor to shortest height texture around it
    raiseToTexture,
    
    // lower floor to lowest surrounding floor
    //  and change floorpic
    lowerAndChange,
  
    raiseFloor24,
    raiseFloor24AndChange,
    raiseFloorCrush,

     // raise to next highest floor, turbo-speed
    raiseFloorTurbo,       
    donutRaise,
    raiseFloor512
    
}
