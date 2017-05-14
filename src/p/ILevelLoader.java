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

import java.io.IOException;

import i.DoomStatusAware;
import rr.subsector_t;
import defines.skill_t;

public interface ILevelLoader extends DoomStatusAware {

	// Lump order in a map WAD: each map needs a couple of lumps
    // to provide a complete scene geometry description.

    public static final int ML_LABEL = 0;

    /** A separator, name, ExMx or MAPxx */
    public static final int ML_THINGS = 1;

    /** Monsters, items.. */
    public static final int ML_LINEDEFS = 2;

    /** LineDefs, from editing */
    public static final int ML_SIDEDEFS = 3;

    /** SideDefs, from editing */
    public static final int ML_VERTEXES = 4;

    /** Vertices, edited and BSP splits generated */
    public static final int ML_SEGS = 5;

    /** LineSegs, from LineDefs split by BSP */
    public static final int ML_SSECTORS = 6;

    /** SubSectors, list of LineSegs */
    public static final int ML_NODES = 7;

    /** BSP nodes */
    public static final int ML_SECTORS = 8;

    /** Sectors, from editing */
    public static final int ML_REJECT = 9;

    /** LUT, sector-sector visibility */
    public static final int ML_BLOCKMAP = 10;
    
    // Maintain single and multi player starting spots.
    public static final int MAX_DEATHMATCH_STARTS  = 10;

    /** Expected lump names for verification */
    public static final String[] LABELS={"MAPNAME","THINGS","LINEDEFS","SIDEDEFS",
                                        "VERTEXES","SEGS","SSECTORS","NODES",
                                        "SECTORS","REJECT","BLOCKMAP"};

    /** P_SetupLevel 
     * 
     * @param episode
     * @param map
     * @param playermask
     * @param skill
     * @throws IOException 
     */
	void SetupLevel(int episode, int map, int playermask, skill_t skill) throws IOException;

	/**
	 * P_SetThingPosition Links a thing into both a block and a subsector based
	 * on it's x y. Sets thing.subsector properly
	 *
	 * 
	 * @param thing
	 */
	void SetThingPosition(mobj_t thing);

	  /**
	   * R_PointInSubsector
	   * 
	   * MAES: it makes more sense to have this here.
	   * 
	   * @param x fixed
	   * @param y fixed
	   * 
	   */
	
	subsector_t PointInSubsector(int x, int y);
        
	
}
