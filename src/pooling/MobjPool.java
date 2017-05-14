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

import doom.think_t;
import p.Actions;
import p.mobj_t;
import s.AudioChunk;

/* The idea is to reuse mobjs...however in practice that 
 * doesn't work out so well, with everything "freezing" after 
 * a while */

public class MobjPool extends ObjectPool<mobj_t>
{

	Actions A;
	
    public MobjPool(Actions A)    
    {
    	// A reasonable time limit for map objects?
    	super(1000L);
    	this.A=A;
    }

    protected mobj_t create()
    {
        /*
        for (int i=0;i<8192;i++){
            locked.push(new mobj_t(A));
        }
        
        return locked.pop();*/
        
        return new mobj_t(A);
    }

    public void expire(mobj_t o)
    {
        o.function=think_t.NOP;
    }

    public boolean validate(mobj_t o)
    {
        return (o.function==think_t.NOP);
    }

}
