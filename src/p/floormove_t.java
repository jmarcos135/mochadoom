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

public class floormove_t extends SectorAction implements IReadableDoomObject{
    
    public floormove_t(){
        // MAES HACK: floors are implied to be at least of "lowerFloor" type
        // unless set otherwise, due to implicit zero-enum value.
        this.type=floor_e.lowerFloor;
    }
    
    public floor_e type;
    public boolean crush;

    public int     direction;
    public int     newspecial;
    public short   texture;
    /** fixed_t */
    public int floordestheight;
    /** fixed_t */
    public int speed;

    @Override
    public void read(DataInputStream f) throws IOException{

        super.read(f); // Call thinker reader first            
        type=floor_e.values()[DoomIO.readLEInt(f)];
        crush=DoomIO.readIntBoolean(f);
        super.sectorid=DoomIO.readLEInt(f); // Sector index (or pointer?)
        direction=DoomIO.readLEInt(f);
        newspecial=DoomIO.readLEInt(f);
        texture=DoomIO.readLEShort(f);
        floordestheight=DoomIO.readLEInt(f);
        speed=DoomIO.readLEInt(f);        
        }
   
    @Override
    public void pack(ByteBuffer b) throws IOException{
        super.pack(b); //12            
        b.putInt(type.ordinal()); // 16
        b.putInt(crush?1:0); //20
        b.putInt(super.sectorid); // 24
        b.putInt(direction); // 28
        b.putInt(newspecial); // 32
        b.putShort(texture); // 34
        b.putInt(floordestheight); // 38
        b.putInt(speed); // 42
    }
    
}