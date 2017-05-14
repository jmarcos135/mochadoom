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

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import rr.SectorAction;
import w.DoomIO;
import w.IReadableDoomObject;

public class vldoor_t extends SectorAction implements IReadableDoomObject{
        
        public vldoor_e    type;
        /** fixed_t */
        public int topheight, speed;

        /** 1 = up, 0 = waiting at top, -1 = down */
        public int direction;
        
        /** tics to wait at the top */
        public int             topwait;
        
        /**(keep in case a door going down is reset)
           when it reaches 0, start going down */        
        public int             topcountdown;
        
        @Override
        public void read(DataInputStream f) throws IOException{

            super.read(f); // Call thinker reader first            
            type=vldoor_e.values()[DoomIO.readLEInt(f)];
            super.sectorid=DoomIO.readLEInt(f); // Sector index (or pointer?)
            topheight=DoomIO.readLEInt(f);
            speed=DoomIO.readLEInt(f);
            direction=DoomIO.readLEInt(f);
            topwait=DoomIO.readLEInt(f);
            topcountdown=DoomIO.readLEInt(f);            
            }
        
        @Override
        public void pack(ByteBuffer b) throws IOException{
            super.pack(b); //12            
            b.putInt(type.ordinal()); // 16
            b.putInt(super.sectorid); // 20
            b.putInt(topheight); // 24
            b.putInt(speed); //28
            b.putInt(direction); // 32
            b.putInt(topwait); //36
            b.putInt(topcountdown); //40
        }
        
    }