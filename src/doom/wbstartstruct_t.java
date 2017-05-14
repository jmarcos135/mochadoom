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

import static data.Limits.MAXPLAYERS;
import utils.C2JUtils;

public class wbstartstruct_t implements Cloneable{

        public wbstartstruct_t(){
            plyr=new wbplayerstruct_t[MAXPLAYERS];
            C2JUtils.initArrayOfObjects(plyr, wbplayerstruct_t.class);
        }
    
        public int      epsd;   // episode # (0-2)

        // if true, splash the secret level
        public boolean  didsecret;
        
        // previous and next levels, origin 0
        public int      last;
        public int      next;   
        
        public int      maxkills;
        public int      maxitems;
        public int      maxsecret;
        public int      maxfrags;

        /** the par time */
        public int      partime;
        
        /** index of this player in game */
        public int      pnum;   
        /** meant to be treated as a "struct", therefore assignments should be deep copies */
        public wbplayerstruct_t[]   plyr;
        
        public wbstartstruct_t clone(){
            wbstartstruct_t cl=null;
            try {
                cl=(wbstartstruct_t)super.clone();
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            /*cl.epsd=this.epsd;
            cl.didsecret=this.didsecret;
            cl.last=this.last;
            cl.next=this.next;
            cl.maxfrags=this.maxfrags;
            cl.maxitems=this.maxitems;
            cl.maxsecret=this.maxsecret;
            cl.maxkills=this.maxkills;
            cl.partime=this.partime;
            cl.pnum=this.pnum;*/
            for (int i=0;i<cl.plyr.length;i++){
                cl.plyr[i]=this.plyr[i].clone();    
            }
            //cl.plyr=this.plyr.clone();
            
            return cl;
            
        }
        
    } 
