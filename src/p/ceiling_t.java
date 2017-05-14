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

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import rr.SectorAction;
import w.CacheableDoomObject;
import w.IPackableDoomObject;
import w.IReadableDoomObject;

public class ceiling_t extends SectorAction implements CacheableDoomObject,IReadableDoomObject,IPackableDoomObject{

        public ceiling_e   type;
        /** fixed_t */
        public int bottomheight;
        /** fixed_t */
        public int topheight;
        /** fixed_t */
        public int speed;
        boolean crush;

        // 1 = up, 0 = waiting, -1 = down
        public int     direction;

        // ID
        public int     tag;                   
        public int     olddirection;
        
        public ceiling_t(){
        	// Set to the smallest ordinal type.
        	this.type=ceiling_e.lowerToFloor;
        }
        
        // HACK for speed.
        public static final ceiling_e[] values=ceiling_e.values();
        
        @Override
        public void read(DataInputStream f) throws IOException{
        	// Read 48 bytes.
        	readbuffer.position(0);
        	readbuffer.order(ByteOrder.LITTLE_ENDIAN);
        	f.read(readbuffer.array(), 0,48);
        	unpack(readbuffer);        	
            }

        @Override
        public void pack(ByteBuffer b) throws IOException{
        	b.order(ByteOrder.LITTLE_ENDIAN);
            super.pack(b); //12            
            b.putInt(type.ordinal()); // 16            
            b.putInt(super.sectorid); // 20
            b.putInt(bottomheight); 
            b.putInt(topheight); // 28
            b.putInt(speed);
            b.putInt(crush?1:0); 
            b.putInt(direction); // 40
            b.putInt(tag);
            b.putInt(olddirection); //48
        	}

		@Override
		public void unpack(ByteBuffer b) throws IOException {
			b.order(ByteOrder.LITTLE_ENDIAN);
			super.unpack(b); // Call thinker reader first
			type=values[b.getInt()];
            super.sectorid=b.getInt(); // sector pointer.
            bottomheight=b.getInt();
            topheight=b.getInt();
            speed=b.getInt();
            crush=(b.getInt()!=0);
            direction=b.getInt();
            tag=b.getInt();
            olddirection=b.getInt();			
			}
        
		private static ByteBuffer readbuffer=ByteBuffer.allocate(48);
		
        }
