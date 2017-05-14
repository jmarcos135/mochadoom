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
import s.degenmobj_t;

public class button_t implements Resettable{

        public line_t line;
        public bwhere_e    where;
        public int     btexture;
        public int     btimer;
        public degenmobj_t soundorg;
        
        public button_t(){	
        	this.btexture=0;
        	this.btimer=0;
        	this.where=bwhere_e.top;
        }
 
        public void reset(){
            this.line=null;
            this.where=bwhere_e.top;
            this.btexture=0;
            this.btimer=0;
            this.soundorg=null;
            
        }

    }