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

package boom;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import w.CacheableDoomObject;
import w.DoomBuffer;

public class mapnode_znod_t implements CacheableDoomObject {

    
        public short x;  // Partition line from (x,y) to x+dx,y+dy)
        public short y;
        public short dx;
        public short dy;
        // Bounding box for each child, clip against view frustum.
        public short[][] bbox;
        // If NF_SUBSECTOR its a subsector, else it's a node of another subtree.
        public int[] children;
        
        public mapnode_znod_t(){
            this.bbox = new short[2][4];
            this.children = new int[2];
        }
        
        public static final int sizeOf() {
            return (8 + 16 + 8);
        }

        @Override
        public void unpack(ByteBuffer buf)
                throws IOException {
            buf.order(ByteOrder.LITTLE_ENDIAN);
            this.x = buf.getShort();
            this.y = buf.getShort();
            this.dx = buf.getShort();
            this.dy = buf.getShort();
            DoomBuffer.readShortArray(buf, this.bbox[0], 4);
            DoomBuffer.readShortArray(buf, this.bbox[1], 4);
            DoomBuffer.readIntArray(buf, this.children, 2);
            
        }

}
