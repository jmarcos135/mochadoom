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

import static data.Tables.ANG180;
import static data.Tables.ANG270;
import static data.Tables.ANG90;
import static data.Tables.SlopeDiv;
import static data.Tables.tantoangle;
import utils.C2JUtils;
import doom.player_t;

public class ViewVars {
    
    // Found in draw_c. Only ever used in renderer.
    public int windowx;
    public int windowy;
    public int width;
    public int height;
    
    // MAES: outsiders have no business peeking into this.
    // Or...well..maybe they do. It's only used to center the "pause" X
    // position.
    // TODO: get rid of this?
    public int scaledwidth;
    public int centerx;
    public int centery;
    
    /** Used to determine the view center and projection in view units fixed_t */
    public int centerxfrac, centeryfrac, projection;

    /** fixed_t */
    public int x, y, z;

    // MAES: an exception to strict type safety. These are used only in here,
    // anyway (?) and have no special functions.
    // Plus I must use them as indexes. angle_t
    public long angle;

    /** fixed */
    public int cos, sin;

    public player_t player;

    /** Heretic/freeview stuff? */

    public int lookdir;
    
    // 0 = high, 1 = low. Normally only the menu and the interface can change
    // that.
    public int detailshift;
    
    public int WEAPONADJUST;
    public int BOBADJUST;
	
	/**
	 * constant arrays used for psprite clipping and initializing clipping
	 */
    public short[] negonearray; // MAES: in scaling
    public short[] screenheightarray;// MAES: in scaling
    
    /** Mirrors the one in renderer... */
    public long[] xtoviewangle;
	
    public final void initNegOneArray(int screenwidth){
	       C2JUtils.memset(negonearray, (short)-1,screenwidth);
		}
    
    public final long PointToAngle(int x, int y) {
        // MAES: note how we don't use &BITS32 here. That is because
        // we know that the maximum possible value of tantoangle is angle
        // This way, we are actually working with vectors emanating
        // from our current position.
        x -= this.x;
        y -= this.y;

        if ((x == 0) && (y == 0))
            return 0;

        if (x >= 0) {
            // x >=0
            if (y >= 0) {
                // y>= 0

                if (x > y) {
                    // octant 0
                    return tantoangle[SlopeDiv(y, x)];
                } else {
                    // octant 1
                    return (ANG90 - 1 - tantoangle[SlopeDiv(x, y)]);
                }
            } else {
                // y<0
                y = -y;

                if (x > y) {
                    // octant 8
                    return (-tantoangle[SlopeDiv(y, x)]);
                } else {
                    // octant 7
                    return (ANG270 + tantoangle[SlopeDiv(x, y)]);
                }
            }
        } else {
            // x<0
            x = -x;

            if (y >= 0) {
                // y>= 0
                if (x > y) {
                    // octant 3
                    return (ANG180 - 1 - tantoangle[SlopeDiv(y, x)]);
                } else {
                    // octant 2
                    return (ANG90 + tantoangle[SlopeDiv(x, y)]);
                }
            } else {
                // y<0
                y = -y;

                if (x > y) {
                    // octant 4
                    return (ANG180 + tantoangle[SlopeDiv(y, x)]);
                } else {
                    // octant 5
                    return (ANG270 - 1 - tantoangle[SlopeDiv(x, y)]);
                }
            }
        }
        // This is actually unreachable.
        // return 0;
    }
    
    public final int getViewWindowX(){
        return windowx;
    }

    public final int getViewWindowY(){
        return windowy;
    }
        
    public final int getScaledViewWidth(){
        return scaledwidth;
    }

    public final int getScaledViewHeight() {
        return height;
    }

}
