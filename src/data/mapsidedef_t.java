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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import w.CacheableDoomObject;
import w.DoomBuffer;

/**
 * A SideDef, defining the visual appearance of a wall, by setting textures and
 * offsets. ON-DISK.
 */

public class mapsidedef_t implements CacheableDoomObject{

    public mapsidedef_t() {

    }

    public short textureoffset;

    public short rowoffset;

    // 8-char strings.
    public String toptexture;

    public String bottomtexture;

    public String midtexture;

    /** Front sector, towards viewer. */
    public short sector;

    public static int sizeOf() {
        return 30;
    }

    @Override
    public void unpack(ByteBuffer buf)
            throws IOException {
        buf.order(ByteOrder.LITTLE_ENDIAN);
        this.textureoffset = buf.getShort();
        this.rowoffset = buf.getShort();
        this.toptexture=DoomBuffer.getNullTerminatedString(buf,8).toUpperCase();
        this.bottomtexture=DoomBuffer.getNullTerminatedString(buf,8).toUpperCase();
        this.midtexture=DoomBuffer.getNullTerminatedString(buf,8).toUpperCase();
        this.sector = buf.getShort();
        
    }
}
