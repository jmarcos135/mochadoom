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

package rr;


/** A vissprite_t is a thing
 * that will be drawn during a refresh.
 * I.e. a sprite object that is partly visible.
 */

public class vissprite_t<V> implements Comparable<vissprite_t<V>>{

// Doubly linked list.
public vissprite_t<V> prev;
public vissprite_t<V> next;

public int         x1;
public int         x2;

// for line side calculation
public int     gx;
public int     gy;     

// global bottom / top for silhouette clipping
public int     gz;
public int     gzt;

// horizontal position of x1
public int     startfrac;

public int     scale;

// negative if flipped
public int     xiscale;    

public int     texturemid;
public int         patch;

/** for color translation and shadow draw,
 * maxbright frames as well.
 * 
 * Use paired with pcolormap;
 */ 
public V colormap;

/* pointer into colormap
public int pcolormap; */

public long mobjflags;

/** visspites are sorted by scale */

@Override
public final int compareTo(vissprite_t<V> o) {
    // We only really care if it's drawn before. 
    if (this.scale> o.scale) return 1;
    if (this.scale< o.scale) return -1;
    return 0;
}

public String toString(){    
    return ("Effective drawing position x1: "+x1 + " x2: "+ x2 +" scale "+(scale/65535.0) +" iscale "+(xiscale/65535.0));
}

}