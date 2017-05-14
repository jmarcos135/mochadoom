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

package pooling;

import java.util.Arrays;

public abstract class GenericIntMap<K> {
    protected static final int DEFAULT_CAPACITY = 16;
    
    /** Concrete implementations must allocate patches
     *  
     */
    GenericIntMap() {
    
    	lumps = new int[DEFAULT_CAPACITY];
        // patches = new K[DEFAULT_CAPACITY];
    }
    
    public boolean containsKey(int lump) {
        return indexOf(lump) >= 0;
    }
    
    public K get(int lump) {
        int index = indexOf(lump);
        if (index >= 0) {
            return patches[index];
        } else {
            return null;
        }
    }
    
    public void put(int lump, K patch) {
        int index = indexOf(lump);
        if (index >= 0) {
            patches[index] = patch;
        } else {
            ensureCapacity(numEntries + 1);
            int newIndex = ~index;
            int moveCount = numEntries - newIndex;
            if (moveCount > 0) {
                System.arraycopy(lumps, newIndex, lumps, newIndex+1, moveCount);
                System.arraycopy(patches, newIndex, patches, newIndex+1, moveCount);
            }
            lumps[newIndex] = lump;
            patches[newIndex] = patch;
            ++ numEntries;
        }
    }
    
    protected void ensureCapacity(int cap) {
        while (lumps.length <= cap) {
            lumps =
                Arrays.copyOf(lumps, Math.max(lumps.length * 2, DEFAULT_CAPACITY));
        }
        while (patches.length <= cap) {
            patches =
                Arrays.copyOf(patches, Math.max(patches.length * 2, DEFAULT_CAPACITY));
        }
    }
    protected int indexOf(int lump) {
         return Arrays.binarySearch(lumps, 0, numEntries, lump);
    	//for (int i=0;i<numEntries;i++)
    	//	if (lumps[i]==lump) return i;
    	//
    	//return -1;
    	
    }
    
    
    protected int[] lumps;
    protected int numEntries;
    protected K[] patches;
}
