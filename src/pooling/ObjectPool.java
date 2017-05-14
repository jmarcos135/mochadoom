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

import java.util.Enumeration;
import java.util.Hashtable;

import p.mobj_t;

/** A convenient object pooling class. Currently used for AudioChunks, but
 *  could be reused for UI events and other such things. Perhaps reusing it
 *  for mobj_t's is possible, but risky.
 * 
 */


public abstract class ObjectPool<K>
{

	private static final boolean D=false;
	
    public ObjectPool(long expirationTime)
    {
        this.expirationTime = expirationTime;
        locked = new Hashtable<K,Long>();
        unlocked = new Hashtable<K,Long>();
    }

    protected abstract K create();

    public abstract boolean validate(K obj);

    public abstract void expire(K obj);

    public synchronized K checkOut()
    {
        long now = System.currentTimeMillis();
        K t;
        if(unlocked.size() > 0)
        {
            Enumeration<K> e = unlocked.keys();
           // System.out.println((new StringBuilder("Pool size ")).append(unlocked.size()).toString());
            while(e.hasMoreElements()) 
            {
                t = e.nextElement();
                if(now - ((Long)unlocked.get(t)).longValue() > expirationTime)
                {
                	// object has expired
                	if (t instanceof mobj_t)
                	if (D) System.out.printf("Object %s expired\n",t.toString());
                    unlocked.remove(t);
                    expire(t);
                    t = null;
                } else
                {
                    if(validate(t))
                    {
                        unlocked.remove(t);
                        locked.put(t, Long.valueOf(now));
                        if (D) if (t instanceof mobj_t)
                        	System.out.printf("Object %s reused\n",t.toString());
                        return t;
                    }
                    
                    // object failed validation
                    unlocked.remove(t);
                    expire(t);
                    t = null;
                }
            }
        }

        t = create();
        locked.put(t, Long.valueOf(now));
        return t;
    }

    public synchronized void checkIn(K t)
    {
    	if (D) if (t instanceof mobj_t)
    	System.out.printf("Object %s returned to the pool\n",t.toString());
        locked.remove(t);
        unlocked.put(t, Long.valueOf(System.currentTimeMillis()));
    }

    private long expirationTime;
    protected Hashtable<K,Long> locked;
    private Hashtable<K,Long> unlocked;
}
