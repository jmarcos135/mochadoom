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

public class doomcom_t {
	
		public doomcom_t(){
			this.data=new doomdata_t();
			
		}

        // Supposed to be DOOMCOM_ID?
        // Maes: was "long", but they intend 32-bit "int" here. Hurray for C's consistency!
        public int        id;
        
        // DOOM executes an int to execute commands.
        public short       intnum;     
        // Communication between DOOM and the driver.
        // Is CMD_SEND or CMD_GET.
        public short       command;
        // Is dest for send, set by get (-1 = no packet).
        public short       remotenode;
        
        // Number of bytes in doomdata to be sent
        public short       datalength;

        // Info common to all nodes.
        // Console is allways node 0.
        public short       numnodes;
        // Flag: 1 = no duplication, 2-5 = dup for slow nets.
        public short       ticdup;
        // Flag: 1 = send a backup tic in every packet.
        public short       extratics;
        // Flag: 1 = deathmatch.
        public short       deathmatch;
        // Flag: -1 = new game, 0-5 = load savegame
        public short       savegame;
        public short       episode;    // 1-3
        public short       map;        // 1-9
        public short       skill;      // 1-5

        // Info specific to this node.
        public short       consoleplayer;
        public short       numplayers;
        
        // These are related to the 3-display mode,
        //  in which two drones looking left and right
        //  were used to render two additional views
        //  on two additional computers.
        // Probably not operational anymore.
        // 1 = left, 0 = center, -1 = right
        public short       angleoffset;
        // 1 = drone
        public short       drone;      

        // The packet data to be sent.
        public doomdata_t      data;
        
    }
