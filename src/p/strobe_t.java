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

public class strobe_t extends SectorAction{
		
        public int     count;
        public int     minlight;
        public int     maxlight;
        public int     darktime;
        public int     brighttime;
        
        //
        // T_StrobeFlash
        //
        public void StrobeFlash() {
            if (--count != 0)
                return;

            if (sector.lightlevel == minlight) {
                sector.lightlevel = (short) maxlight;
                count = brighttime;
            } else {
                sector.lightlevel = (short) minlight;
                count = darktime;
            }

        }
        
        @Override
        public void read(DataInputStream f) throws IOException{

            super.read(f); // Call thinker reader first            
            super.sectorid=DoomIO.readLEInt(f); // Sector index
            count=DoomIO.readLEInt(f);
            maxlight=DoomIO.readLEInt(f);
            minlight=DoomIO.readLEInt(f);
            darktime=DoomIO.readLEInt(f);
            brighttime=DoomIO.readLEInt(f);       
            }
        
        @Override
        public void pack(ByteBuffer b) throws IOException{
            super.pack(b); //12            
            b.putInt(super.sectorid); // 16
            b.putInt(count); //20
            b.putInt(maxlight);//24
            b.putInt(minlight);//28
            b.putInt(darktime);//32
            b.putInt(brighttime);//36
        }
        
    };