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

package rr.parallel;

/** This is all the information needed to draw a particular SEG.
 * It's quite a lot, actually, but much better than in testing
 * versions.
 *  
 */

public class RenderSegInstruction<V> {    
	public int     rw_x,rw_stopx;
	public int toptexture,midtexture,bottomtexture;
	public int  pixhigh,pixlow,pixhighstep,pixlowstep,
	topfrac,    topstep,bottomfrac, bottomstep;	
	public boolean segtextured,markfloor,markceiling;
	public long     rw_centerangle; // angle_t
	/** fixed_t */
	public int     rw_offset,rw_distance,rw_scale,
	rw_scalestep,rw_midtexturemid,rw_toptexturemid,rw_bottomtexturemid;
	public int viewheight;
	V[] walllights;
	public int centery;

}
