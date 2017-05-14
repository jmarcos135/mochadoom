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

import java.util.Stack;

import p.mobj_t;

/** A convenient object pooling class, derived from the stock ObjectPool.
 *  
 *  It's about 50% faster than calling new, and MUCH faster than ObjectPool
 *  because it doesn't do that bullshit object cleanup every so often.
 * 
 */


public abstract class ObjectQueuePool<K>
{

	private static final boolean D=false;
	
    public ObjectQueuePool(long expirationTime)
    {
        locked = new Stack<K>();
        
    }

    protected abstract K create();

    public abstract boolean validate(K obj);

    public abstract void expire(K obj);

    public void drain(){
        locked.clear();
        }
    
    public K checkOut()
    {
        
        K t;
        if(!locked.isEmpty())
        {
            return locked.pop(); 

        }

        t = create();
        return t;
    }

    public void checkIn(K t)
    {
    	if (D) if (t instanceof mobj_t)
    	System.out.printf("Object %s returned to the pool\n",t.toString());
        locked.push(t);
    }

    protected Stack<K> locked;
   // private Hashtable<K,Long> unlocked;
}
