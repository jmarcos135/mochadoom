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
import java.util.Arrays;

import utils.C2JUtils;
import w.CacheableDoomObject;

public class DeepBSPNodesV4 implements CacheableDoomObject{

	public static final byte[] DeepBSPHeader={
		'x','N','d','4',0,0,0,0
		};
	
	byte[] header=new byte[8];
	mapnode_v4_t[] nodes;
	int numnodes;
	
	public boolean formatOK(){
		return Arrays.equals(header, DeepBSPHeader);
	}

	public mapnode_v4_t[] getNodes(){
		return nodes;
	}
	
	@Override
	public void unpack(ByteBuffer buf) throws IOException {
		int length=buf.capacity();
		
		// Too short, not even header.
		if (length<8) return;
		
		numnodes=(length-8)/mapnode_v4_t.sizeOf();
		
		if (length<1) return;
		
		buf.get(header); // read header
		
		nodes=C2JUtils.createArrayOfObjects(mapnode_v4_t.class, length);
		
		for (int i=0;i<length;i++){
			nodes[i].unpack(buf);
		}
		
	}
	
	
	

}
