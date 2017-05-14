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

package automap;

public class fline_t
{
 
    /*
     * public fline_t(){
        a=new fpoint_t();
        b=new fpoint_t();
    }
    
    public fline_t(fpoint_t a, fpoint_t b){
        this.a=a;
        this.b=b;
    }
*/    
    public fline_t(int ax, int ay, int bx, int by){
        this.ay=ay;
        this.ax=ax;
        this.by=by;
        this.bx=bx;
    }
    
    public fline_t() {
        // TODO Auto-generated constructor stub
    }

    public int ax,ay,bx,by;
    /*
    public fpoint_t a, b;

    public void reset() {
        this.a.x=0;
        this.a.y=0;
        this.b.x=0;
        this.b.y=0;
        
    }*/
}
