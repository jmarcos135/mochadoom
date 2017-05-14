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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import w.CacheableDoomObject;

/**
 * Texture definition.
 * Each texture is composed of one or more patches,
 * with patches being lumps stored in the WAD.
 * The lumps are referenced by number, and patched
 * into the rectangular texture space using origin
 * and possibly other attributes. 
 */

public class mappatch_t implements CacheableDoomObject {
     public short   originx;
     public short   originy;
     public short   patch;
     public short   stepdir;
     public short   colormap;
     
    @Override
    public void unpack(ByteBuffer buf)
            throws IOException {
        buf.order(ByteOrder.LITTLE_ENDIAN);
        originx=buf.getShort();
        originy=buf.getShort();
        patch=buf.getShort();
        stepdir=buf.getShort();
        colormap=buf.getShort();        
    }

    public static final int size() {
        return 10;
    }
     
     
 };
