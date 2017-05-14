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
// along with Mocha Doom.  If not, see <http://www.gnu.org/licenses/>.

package w;

import java.io.IOException;
import java.nio.ByteBuffer;

/** A container allowing for caching of arrays of CacheableDoomObjects 
 *  
 *  It's a massive improvement over the older system, allowing for proper 
 *  caching and auto-unpacking of arrays of CacheableDoomObjects and much 
 *  cleaner code throughout.
 *  
 *  The container itself is a CacheableDoomObject....can you feel the
 *  abuse? ;-)
 * 
 */

public class CacheableDoomObjectContainer<T extends CacheableDoomObject> implements CacheableDoomObject {
	
	private T[] stuff;
	
	public CacheableDoomObjectContainer(T[] stuff){
		this.stuff=stuff;
	}
	
	public T[] getStuff(){
		return stuff;
	}
	
	@Override
	public void unpack(ByteBuffer buf) throws IOException {
		for (int i = 0; i < stuff.length; i++) {
				stuff[i].unpack(buf);
			}
		}
	
	/** Statically usable method
	 * 
	 * @param buf
	 * @param stuff
	 * @throws IOException
	 */
	
	public static void unpack(ByteBuffer buf, CacheableDoomObject[] stuff) throws IOException{
	       for (int i = 0; i < stuff.length; i++) {
               stuff[i].unpack(buf);
           }
	}

}
