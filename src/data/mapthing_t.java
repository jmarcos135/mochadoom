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

package data;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import w.CacheableDoomObject;
import w.IPackableDoomObject;
import w.IWritableDoomObject;

/** mapthing_t ... same on disk AND in memory, wow?! */

public class mapthing_t implements CacheableDoomObject,IPackableDoomObject,IWritableDoomObject,Cloneable{
    public short x;

    public short y;

    public short angle;

    public short type;

    public short options;

    public mapthing_t() {
    }

    public mapthing_t(mapthing_t source) {
        this.copyFrom(source);
    }

    public static int sizeOf() {
        return 10;
    }

    @Override
    public void unpack(ByteBuffer buf)
            throws IOException {
        buf.order(ByteOrder.LITTLE_ENDIAN);
        this.x = buf.getShort();
        this.y = buf.getShort();
        this.angle = buf.getShort();
        this.type = buf.getShort();
        this.options = buf.getShort();
        
    }
    
    public void copyFrom(mapthing_t source){

        this.x=source.x;
        this.y=source.y;
        this.angle=source.angle;
        this.options=source.options;
        this.type=source.type;
    }

    @Override
    public void write(DataOutputStream f)
            throws IOException {
        
        // More efficient, avoids duplicating code and
        // handles little endian better.
        iobuffer.position(0);
        iobuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.pack(iobuffer);
        f.write(iobuffer.array());
        
    }

    public void pack(ByteBuffer b) {
        b.order(ByteOrder.LITTLE_ENDIAN);
        b.putShort(x);
        b.putShort(y);
        b.putShort(angle);
        b.putShort(type);
        b.putShort(options);
    }
    
    private static ByteBuffer iobuffer=ByteBuffer.allocate(10);
}