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

package rr;

import static m.fixed_t.FRACBITS;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import p.Resettable;

import w.CacheableDoomObject;

/** This is the vertex structure used IN MEMORY with fixed-point arithmetic.
 *  It's DIFFERENT than the one used on disk, which has 16-bit signed shorts.
 *  However, it must be parsed. 
 *
 */

public class vertex_t  implements CacheableDoomObject, Resettable{

    public vertex_t(){
        
    }
    /** treat as (fixed_t) */
    public  int x,y;
    
    
    /** Notice how we auto-expand to fixed_t */
    @Override
    public void unpack(ByteBuffer buf)
            throws IOException {
        buf.order(ByteOrder.LITTLE_ENDIAN);
        this.x=buf.getShort()<<FRACBITS;
        this.y=buf.getShort()<<FRACBITS;
        
    }

    @Override
    public void reset() {
        x=0; y=0;        
    }


    public static int sizeOf() {
        return 4;
    }
    
}