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

import utils.C2JUtils;
import w.CacheableDoomObject;
import w.DoomBuffer;

/** Texture definition.
 *  A DOOM wall texture is a list of patches which are to be combined in a predefined order.
 *  This is the ON-DISK structure, to be read from the TEXTURES1 and TEXTURES2 lumps.
 *  In memory, this becomes texture_t.
 *    
 *  @author MAES
 *
 */

public class maptexture_t implements CacheableDoomObject{
    public String        name;
    public  boolean     masked; 
    public short       width; // was signed byte
    public short       height; // was 
    //void**t        columndirectory;  // OBSOLETE (yeah, but we must read a dummy integer here)
    public short       patchcount;
    public mappatch_t[]  patches;
    
    
    @Override
    public void unpack(ByteBuffer buf)
            throws IOException {
        buf.order(ByteOrder.LITTLE_ENDIAN);
        name=DoomBuffer.getNullTerminatedString(buf,8);
        masked=(buf.getInt()!=0);
        width=buf.getShort();
        height=buf.getShort();
        buf.getInt(); // read a dummy integer for obsolete columndirectory.
        patchcount=buf.getShort();        
        
        // Simple sanity check. Do not attempt reading more patches than there
        // are left in the TEXTURE lump.
        patchcount=(short) Math.min(patchcount,(buf.capacity()-buf.position())/mappatch_t.size());
        
        patches=new mappatch_t[patchcount];
        C2JUtils.initArrayOfObjects(patches,mappatch_t.class);
        DoomBuffer.readObjectArray(buf, patches, patchcount);
        
    }
    
};