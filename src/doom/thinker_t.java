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

package doom;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static utils.C2JUtils.pointer;
import w.CacheableDoomObject;
import w.IPackableDoomObject;
import w.IReadableDoomObject;
import p.ActionType1;
import p.ActionType2;
import p.ActionTypeSS;

public class thinker_t implements CacheableDoomObject,IReadableDoomObject,IPackableDoomObject{
   
   public thinker_t prev;
   public thinker_t next;
   public think_t     function;

   /* killough 8/29/98: we maintain thinkers in several equivalence classes,
    * according to various criteria, so as to allow quicker searches.
    */

   /** Next, previous thinkers in same class */
   
   public thinker_t cnext, cprev;

   
   // Thinkers can either have one parameter of type (mobj_t),
   // Or otherwise be sector specials, flickering lights etc.
   // Those are atypical and need special handling.
   public ActionType1     acp1;
   public ActionType2     acp2;
   public ActionTypeSS     acpss;
   
   /** extra fields, to use when archiving/unarchiving for
    * identification. Also in blocklinks, etc.
    */
   public int id,previd, nextid,functionid;
   
   
   
@Override
public void read(DataInputStream f)
        throws IOException {
	readbuffer.position(0);
	readbuffer.order(ByteOrder.LITTLE_ENDIAN);
	f.read(readbuffer.array());
	unpack(readbuffer);
	}

/** This adds 12 bytes */

@Override
public void pack(ByteBuffer b)
        throws IOException {
    // It's possible to reconstruct even by hashcodes.
    // As for the function, that should be implied by the mobj_t type.
	b.order(ByteOrder.LITTLE_ENDIAN);
	b.putInt(pointer(prev));
    b.putInt(pointer(next));
    b.putInt(pointer(function));
    //System.out.printf("Packed thinker %d %d %d\n",pointer(prev),pointer(next),pointer(function));
	}

@Override
public void unpack(ByteBuffer b)
        throws IOException {
    // We are supposed to archive pointers to other thinkers,
    // but they are rather useless once on disk.
	b.order(ByteOrder.LITTLE_ENDIAN);
    previd=b.getInt();
    nextid=b.getInt();
    functionid=b.getInt();
    //System.out.printf("Unpacked thinker %d %d %d\n",pointer(previd),pointer(nextid),pointer(functionid));
	}

 private static ByteBuffer readbuffer=ByteBuffer.allocate(12);

}