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

import w.DoomIO;
import w.IPackableDoomObject;
import w.IReadableDoomObject;
import data.state_t;

public class pspdef_t implements IReadableDoomObject,IPackableDoomObject{

    public pspdef_t(){
        state=new state_t();
    }

    /** a NULL state means not active */
    public state_t	state;	
    public int		tics;
    /** fixed_t */
    public int	sx, sy;
    // When read from disk.
    public int readstate;
    
    @Override
    public void read(DataInputStream f) throws IOException {
        //state=data.info.states[f.readLEInt()];
        readstate=DoomIO.readLEInt(f);
        tics=DoomIO.readLEInt(f);
        sx=DoomIO.readLEInt(f);
        sy=DoomIO.readLEInt(f);
    }
    
    @Override
    public void pack(ByteBuffer f) throws IOException {
        if (state==null) f.putInt(0);
        else f.putInt(state.id);
        f.putInt(tics);
        f.putInt(sx);
        f.putInt(sy);
    }

}
