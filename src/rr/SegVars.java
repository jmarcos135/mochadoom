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

import data.Limits;
import utils.C2JUtils;

public class SegVars {
	// /// FROM BSP /////////
	
    public int MAXDRAWSEGS = Limits.MAXDRAWSEGS;

	/** pointer to drawsegs */
    public int ds_p;

    public drawseg_t[] drawsegs;

    public short[] maskedtexturecol;
    public int pmaskedtexturecol = 0;


	/**
	 * R_ClearDrawSegs
	 * 
	 * The drawseg list is reset by pointing back at 0.
	 * 
	 */
	public void ClearDrawSegs() {
		ds_p = 0;
	}

	public final void ResizeDrawsegs() {
		drawsegs = C2JUtils.resize(drawsegs[0], drawsegs, drawsegs.length*2);
	}
}
