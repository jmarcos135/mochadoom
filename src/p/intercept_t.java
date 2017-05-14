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

import rr.line_t;

/** An object that carries...interception information, I guess...with either a line
 *  or an object?
 *  
 * @author Velktron
 *
 */

public class intercept_t {
		
		/** most intercepts will belong to a static pool */
	
		public intercept_t() {
		
		}
	
		public intercept_t(int frac, mobj_t thing){
			this.frac=frac;
			this.thing=thing;
			this.isaline=false;
		}
		
		public intercept_t(int frac, line_t line){
			this.frac=frac;
			this.line=line;
			this.isaline=true;
		}
		
		/** fixed_t, along trace line */
        public int frac; 
        public boolean isaline;
        // MAES: this was an union of a mobj_t and a line_t,
        // returned as "d".
        mobj_t thing;
        line_t line;
        
        public Interceptable d(){
            return (isaline)? line: thing;
        }
        
    }
