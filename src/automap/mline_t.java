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

/** used only in automap */

public class mline_t
{
    public mline_t(){
        this(0,0,0,0);
    }
    
    public int ax,ay,bx,by;

    public mline_t(int ax, int ay, int bx, int by) {
        this.ax = ax;
        this.ay = ay;
        this.bx = bx;
        this.by = by;
    }
    
    public mline_t(double ax, double ay, double bx, double by) {
        this.ax = (int) ax;
        this.ay = (int) ay;
        this.bx = (int) bx;
        this.by = (int) by;
    }
    
    /*
    public mline_t(mpoint_t a, mpoint_t b) {
        this.a = a;
        this.b = b;
    }

    public mline_t(int ax,int ay,int bx,int by) {
        this.a = new mpoint_t(ax,ay);
        this.b = new mpoint_t(bx,by);
    }
        
    public mline_t(double ax,double ay,double bx,double by) {
        this.a = new mpoint_t(ax,ay);
        this.b = new mpoint_t(bx,by);
    }
    
    public mpoint_t a, b;
    public int ax;
    
    public String toString(){
        return a.toString()+" - "+ b.toString();
    }
    */
}
