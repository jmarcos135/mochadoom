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

import m.IRandom;
import rr.SectorAction;

//
// P_LIGHTS
//

public class fireflicker_t extends SectorAction{
	
     private IRandom RND;
    
     public int     count;
     public int     maxlight;
     public int     minlight;
     
     public fireflicker_t(IRandom RND){
         this.RND=RND;
     }
     
     //
     // T_FireFlicker
     //
     public void FireFlicker() {
         int amount;

         if (--count != 0)
             return;

         amount = (RND.P_Random() & 3) * 16;

         if (sector.lightlevel - amount < minlight)
             sector.lightlevel = (short) minlight;
         else
             sector.lightlevel = (short) (maxlight - amount);

         count = 4;
     }
     
 } 
