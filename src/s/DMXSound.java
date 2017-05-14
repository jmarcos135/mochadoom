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

package s;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import w.CacheableDoomObject;

/** An object representation of Doom's sound format */

public class DMXSound implements CacheableDoomObject{

    /** ushort, all Doom samples are "type 3". No idea how  */    
    public int type;
    /** ushort, speed in Hz. */
    public int speed;    
    /** uint */
    public int datasize;

    public byte[] data;
    
    @Override
    public void unpack(ByteBuffer buf)
            throws IOException {
       buf.order(ByteOrder.LITTLE_ENDIAN);
       type=buf.getChar();
       speed=buf.getChar();
		try {
			datasize = buf.getInt();
		} catch (BufferUnderflowException e) {
			datasize = buf.capacity() - buf.position();
		}
       data=new byte[Math.min(buf.remaining(),datasize)];
       buf.get(data);
    }

}
