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

/**
 * This is the structure of a map vertex ON DISK: in memory it gets shifted and
 * expanded to fixed_t. Also, on disk it only exists as part of the VERTEXES
 * lump: it is not individually cacheable, even though it implements
 * CacheableDoomObject.
 */

public class mapvertex_t
        implements CacheableDoomObject {

    public mapvertex_t(short x, short y) {
        this.x = x;
        this.y = y;
    }

    public mapvertex_t() {
        this((short) 0, (short) 0);
    }

    public short x;

    public short y;

    public static int sizeOf() {
        return 4;
    }

    @Override
    public void unpack(ByteBuffer buf)
            throws IOException {
        buf.order(ByteOrder.LITTLE_ENDIAN);
        x = buf.getShort();
        y = buf.getShort();
    }

}
