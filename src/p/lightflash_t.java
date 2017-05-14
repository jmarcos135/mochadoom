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

import m.IRandom;
import rr.SectorAction;
import w.DoomIO;

//
// BROKEN LIGHT EFFECT
//

public class lightflash_t extends SectorAction{
	
    private IRandom RND;
    
    public int     count;
    public int     maxlight;
    public int     minlight;
    public int     maxtime;
    public int     mintime;
    
    public lightflash_t(){
    
    }
    
    public lightflash_t(IRandom RND){
        this.RND=RND;
    }
    
    /**
     * T_LightFlash
     * Do flashing lights.
     */
    
    public void LightFlash() {
        if (--count != 0)
            return;

        if (sector.lightlevel == maxlight) {
            sector.lightlevel = (short)minlight;
            count = (RND.P_Random() & mintime) + 1;
        } else {
            sector.lightlevel = (short)maxlight;
            count = (RND.P_Random() & maxtime) + 1;
        }

    }
    
    @Override
    public void read(DataInputStream f) throws IOException{

        super.read(f); // Call thinker reader first            
        super.sectorid=DoomIO.readLEInt(f); // Sector index
        count=DoomIO.readLEInt(f);
        maxlight=DoomIO.readLEInt(f);
        minlight=DoomIO.readLEInt(f);
        maxtime=DoomIO.readLEInt(f);
        mintime=DoomIO.readLEInt(f);
        }
    
    @Override
    public void pack(ByteBuffer b) throws IOException{
        super.pack(b); //12            
        b.putInt(super.sectorid); // 16
        b.putInt(count); //20
        b.putInt(maxlight);//24
        b.putInt(minlight);//28
        b.putInt(maxtime);//32
        b.putInt(mintime);//36
    }
    
}