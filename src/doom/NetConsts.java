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

package doom;

public interface NetConsts {

    public static int    NCMD_EXIT=      0x80000000;
    public static int    NCMD_RETRANSMIT     =0x40000000;
    public static int    NCMD_SETUP      =0x20000000;
    public static int   NCMD_KILL    =   0x10000000; // kill game
    public static int    NCMD_CHECKSUM   =   0x0fffffff;

    public static int  DOOMCOM_ID =     0x12345678;


    //Networking and tick handling related. Moved to DEFINES
    //protected static int    BACKUPTICS     = 12;


    // command_t
    public  static short CMD_SEND    = 1;
    public  static short CMD_GET = 2; 
    
}
