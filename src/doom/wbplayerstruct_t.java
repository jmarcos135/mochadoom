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

package doom;


//
// INTERMISSION
// Structure passed e.g. to WI_Start(wb)
//

public class wbplayerstruct_t implements Cloneable{
    
    public wbplayerstruct_t(){
        frags=new int[4];
    }
     public boolean in; // whether the player is in game
     
     /** Player stats, kills, collected items etc. */
     public int     skills;
     public int     sitems;
     public int     ssecret;
     public int     stime; 
     public int[]   frags;
     /** current score on entry, modified on return */
     public int     score;
   
     public wbplayerstruct_t clone(){
         wbplayerstruct_t r=null;
        try {
            r = (wbplayerstruct_t)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
         /*r.in=this.in;
         r.skills=this.skills;
         r.sitems=this.sitems;
         r.ssecret=this.ssecret;
         r.stime=this.stime; */
         System.arraycopy(this.frags, 0, r.frags,0,r.frags.length);
         // r.score=this.score;
         
         
        return r;
         
     }
     
 }
