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

import static m.fixed_t.MAPFRACUNIT;
import static data.Defines.TIC_MUL;

public final class ChaseDirections {

    public static final int DI_EAST = 0;

    public static final int DI_NORTHEAST = 1;

    public static final int DI_NORTH = 2;

    public static final int DI_NORTHWEST = 3;

    public static final int DI_WEST = 4;

    public static final int DI_SOUTHWEST = 5;

    public static final int DI_SOUTH = 6;

    public static final int DI_SOUTHEAST = 7;

    public static final int DI_NODIR = 8;

    public static final int NUMDIR = 9;
    
    //
    // P_NewChaseDir related LUT.
    //
    public final static int opposite[] =
        { DI_WEST, DI_SOUTHWEST, DI_SOUTH, DI_SOUTHEAST, DI_EAST, DI_NORTHEAST,
                DI_NORTH, DI_NORTHWEST, DI_NODIR };

    public final static int diags[] =
        { DI_NORTHWEST, DI_NORTHEAST, DI_SOUTHWEST, DI_SOUTHEAST };

    public final static int[] xspeed =
        { MAPFRACUNIT, 47000/TIC_MUL, 0, -47000/TIC_MUL, -MAPFRACUNIT, -47000/TIC_MUL, 0, 47000/TIC_MUL }; // all
                                                                     // fixed

    public final static int[] yspeed =
        { 0, 47000/TIC_MUL, MAPFRACUNIT, 47000/TIC_MUL, 0, -47000/TIC_MUL, -MAPFRACUNIT, -47000/TIC_MUL }; // all
                                                                     // fixed
    
}
