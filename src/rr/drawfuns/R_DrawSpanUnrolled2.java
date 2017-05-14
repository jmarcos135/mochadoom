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

package rr.drawfuns;

import i.IDoomSystem;

/** An unrolled (4x) rendering loop with full quality */
// public final int dumb=63 * 64;

public final class R_DrawSpanUnrolled2 extends DoomSpanFunction<byte[],short[]> {
    
    
	public R_DrawSpanUnrolled2(int sCREENWIDTH, int sCREENHEIGHT,
            int[] ylookup, int[] columnofs, SpanVars<byte[],short[]> dsvars,
            short[] screen, IDoomSystem I) {
        super(sCREENWIDTH, sCREENHEIGHT, ylookup, columnofs, dsvars, screen, I);
        // TODO Auto-generated constructor stub
    }

    public void invoke() {
	    final byte[] ds_source= dsvars.ds_source;
	    final short[] ds_colormap= dsvars.ds_colormap;
	    final int ds_xstep=dsvars.ds_xstep;
	    final int ds_ystep=dsvars.ds_ystep;
		int f_xfrac; // fixed_t
		int f_yfrac; // fixed_t
		int dest;
		int count;
		int spot;

		// System.out.println("R_DrawSpan: "+ds_x1+" to "+ds_x2+" at "+
		// ds_y);

		if (RANGECHECK) {
		    doRangeCheck();
			// dscount++;
		}

		f_xfrac = dsvars.ds_xfrac;
		f_yfrac = dsvars.ds_yfrac;

		dest = ylookup[dsvars.ds_y] + columnofs[dsvars.ds_x1];

		count = dsvars.ds_x2 - dsvars.ds_x1;
		while (count >= 4) {
			// Current texture index in u,v.
			spot = ((f_yfrac >> (16 - 6)) & (63 * 64))
					+ ((f_xfrac >> 16) & 63);

			// Lookup pixel from flat texture tile,
			// re-index using light/colormap.
			screen[dest++] = ds_colormap[0x00FF & ds_source[spot]];

			// Next step in u,v.
			f_xfrac += ds_xstep;
			f_yfrac += ds_ystep;

			// UNROLL 2
			spot = ((f_yfrac >> (16 - 6)) & (63 * 64))
					+ ((f_xfrac >> 16) & 63);
			screen[dest++] = ds_colormap[0x00FF & ds_source[spot]];
			f_xfrac += ds_xstep;
			f_yfrac += ds_ystep;

			// UNROLL 3
			spot = ((f_yfrac >> (16 - 6)) & (63 * 64))
					+ ((f_xfrac >> 16) & 63);
			screen[dest++] = ds_colormap[0x00FF & ds_source[spot]];
			f_xfrac += ds_xstep;
			f_yfrac += ds_ystep;

			// UNROLL 4
			spot = ((f_yfrac >> (16 - 6)) & (63 * 64))
					+ ((f_xfrac >> 16) & 63);
			screen[dest++] = ds_colormap[0x00FF & ds_source[spot]];
			f_xfrac += ds_xstep;
			f_yfrac += ds_ystep;

			count -= 4;
		}

		while (count > 0) {
			// Current texture index in u,v.
			spot = ((f_yfrac >> (16 - 6)) & (63 * 64))
					+ ((f_xfrac >> 16) & 63);

			// Lookup pixel from flat texture tile,
			// re-index using light/colormap.
			screen[dest++] = ds_colormap[0x00FF & ds_source[spot]];

			// Next step in u,v.
			f_xfrac += ds_xstep;
			f_yfrac += ds_ystep;
			count--;
		}

	}
}
