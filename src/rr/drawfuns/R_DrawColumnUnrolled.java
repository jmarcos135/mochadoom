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

/**
	 * EI VITTU, this gives a clean 25% boost. Da fack...
	 * 
	 * 
	 * @author admin
	 * 
	 */

	public final class R_DrawColumnUnrolled extends DoomColumnFunction<byte[],short[]> {

		/*
		 * That's shit, doesn't help. private final int
		 * SCREENWIDTH2=SCREENWIDTH*2; private final int
		 * SCREENWIDTH3=SCREENWIDTH*3; private final int
		 * SCREENWIDTH4=SCREENWIDTH*4; private final int
		 * SCREENWIDTH5=SCREENWIDTH*5; private final int
		 * SCREENWIDTH6=SCREENWIDTH*6; private final int
		 * SCREENWIDTH7=SCREENWIDTH*7; private final int
		 * SCREENWIDTH8=SCREENWIDTH*8;
		 */

		public R_DrawColumnUnrolled(int SCREENWIDTH, int SCREENHEIGHT,
	            int[] ylookup, int[] columnofs, ColVars<byte[],short[]> dcvars,
	            short[] screen, IDoomSystem I) {
	        super(SCREENWIDTH, SCREENHEIGHT, ylookup, columnofs, dcvars, screen, I);
	    }

        public void invoke() {
			int count,dest;
			final byte[] source;			
			final short[] colormap;
			final int dc_source_ofs=dcvars.dc_source_ofs;

			// These are all "unsigned". Watch out for bit shifts!
			int frac;
			final int fracstep, fracstep2, fracstep3, fracstep4;

			count = dcvars.dc_yh - dcvars.dc_yl + 1;

			source = dcvars.dc_source;
			// dc_source_ofs+=15; // ???? WHY
			colormap = dcvars.dc_colormap;
			dest = computeScreenDest();

			fracstep = dcvars.dc_iscale << 9;
			frac = (dcvars.dc_texturemid + (dcvars.dc_yl - dcvars.centery) * dcvars.dc_iscale) << 9;

			fracstep2 = fracstep + fracstep;
			fracstep3 = fracstep2 + fracstep;
			fracstep4 = fracstep3 + fracstep;

			while (count > 8) {
				screen[dest] = colormap[0x00FF & source[dc_source_ofs + frac >>> 25]];
				screen[dest + SCREENWIDTH] = colormap[0x00FF & source[dc_source_ofs
						+ (frac + fracstep) >>> 25]];
				screen[dest + SCREENWIDTH * 2] = colormap[0x00FF & source[dc_source_ofs
						+ (frac + fracstep2) >>> 25]];
				screen[dest + SCREENWIDTH * 3] = colormap[0x00FF & source[dc_source_ofs
						+ (frac + fracstep3) >>> 25]];

				frac += fracstep4;

				screen[dest + SCREENWIDTH * 4] = colormap[0x00FF & source[dc_source_ofs
						+ frac >>> 25]];
				screen[dest + SCREENWIDTH * 5] = colormap[0x00FF & source[dc_source_ofs
						+ (frac + fracstep) >>> 25]];
				screen[dest + SCREENWIDTH * 6] = colormap[0x00FF & source[dc_source_ofs
						+ (frac + fracstep2) >>> 25]];
				screen[dest + SCREENWIDTH * 7] = colormap[0x00FF & source[dc_source_ofs
						+ (frac + fracstep3) >>> 25]];

				frac += fracstep4;
				dest += SCREENWIDTH * 8;
				count -= 8;
			}

			while (count > 0) {
				screen[dest] = colormap[0x00FF & source[dc_source_ofs + frac >>> 25]];
				dest += SCREENWIDTH;
				frac += fracstep;
				count--;
			}
		}
	}