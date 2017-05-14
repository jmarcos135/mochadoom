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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import w.CacheableDoomObject;
import w.DoomBuffer;
//
// P_SWITCH
//

public class switchlist_t
        implements CacheableDoomObject {
    
    public switchlist_t(){
        
    }
    
    // Were char[9]
    public String name1;

    public String name2;

    public short episode;

    public switchlist_t(String name1, String name2, int episode) {
        super();
        this.name1 = name1;
        this.name2 = name2;
        this.episode = (short) episode;
    }

    @Override
    public void unpack(ByteBuffer buf)
            throws IOException {
        // Like most Doom structs...
        buf.order(ByteOrder.LITTLE_ENDIAN);
        this.name1 = DoomBuffer.getNullTerminatedString(buf, 9);
        this.name2 = DoomBuffer.getNullTerminatedString(buf, 9);
        this.episode = buf.getShort();
    }

    public final static int size() {
        return 20;
    }

    public String toString() {
        return String.format("%s %s %d", name1, name2, episode);
    }
}