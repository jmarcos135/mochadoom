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

/** Prototype for 
 * 
 * @author velktron
 *
 * @param <T>
 */

public abstract class DoomColumnFunction<T,V> implements ColumnFunction<T,V>{
    
    protected final boolean RANGECHECK=false;
    protected final int SCREENWIDTH;
    protected final int SCREENHEIGHT;
    protected ColVars<T,V> dcvars;
    protected final V screen;
    protected final IDoomSystem I;
    protected final int[] ylookup;
    protected final int[] columnofs;
    protected T BLURRY_MAP;
    protected int flags;
    
    public DoomColumnFunction(int sCREENWIDTH, int sCREENHEIGHT,int[] ylookup,
            int[] columnofs, ColVars<T,V> dcvars, V screen,IDoomSystem I) {
        SCREENWIDTH = sCREENWIDTH;
        SCREENHEIGHT = sCREENHEIGHT;
        this.ylookup=ylookup;
        this.columnofs=columnofs;
        this.dcvars = dcvars;
        this.screen = screen;
        this.I=I;
        this.BLURRY_MAP=null;
    }
    
    public DoomColumnFunction(int sCREENWIDTH, int sCREENHEIGHT,int[] ylookup,
            int[] columnofs, ColVars<T,V> dcvars, V screen,IDoomSystem I,T BLURRY_MAP) {
        SCREENWIDTH = sCREENWIDTH;
        SCREENHEIGHT = sCREENHEIGHT;
        this.ylookup=ylookup;
        this.columnofs=columnofs;
        this.dcvars = dcvars;
        this.screen = screen;
        this.I=I;
        this.BLURRY_MAP=BLURRY_MAP;
    }

    protected final void performRangeCheck(){
        if (dcvars.dc_x >= SCREENWIDTH || dcvars.dc_yl < 0 || dcvars.dc_yh >= SCREENHEIGHT)
            I.Error("R_DrawColumn: %d to %d at %d", dcvars.dc_yl, dcvars.dc_yh, dcvars.dc_x);
    }
    
    /**
     * 
     * Use ylookup LUT to avoid multiply with ScreenWidth.
     * Use columnofs LUT for subwindows?
     * 
     * @return Framebuffer destination address.
     */
    
    protected final int computeScreenDest() {
        return ylookup[dcvars.dc_yl] + columnofs[dcvars.dc_x];
    }

    protected final int blockyDest1() {
        return ylookup[dcvars.dc_yl] + columnofs[dcvars.dc_x<<1];
    }

    protected final int blockyDest2() {
        return  ylookup[dcvars.dc_yl] + columnofs[(dcvars.dc_x<<1)+1];
    }

    @Override
    public final void invoke(ColVars<T,V> dcvars) {
        this.dcvars=dcvars;
        invoke();
    }
    
    public final int getFlags(){
        return this.flags;
    }
    
}
