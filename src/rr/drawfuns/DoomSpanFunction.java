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

public abstract class DoomSpanFunction<T,V> implements SpanFunction<T,V> {
    
    protected final boolean RANGECHECK=false;
    protected final int SCREENWIDTH;
    protected final int SCREENHEIGHT;
    protected SpanVars<T,V> dsvars;
    protected final int[] ylookup;
    protected final int[] columnofs;
    protected final V screen;
    protected final IDoomSystem I;
    
    public DoomSpanFunction(int sCREENWIDTH, int sCREENHEIGHT,
            int[] ylookup, int[] columnofs,SpanVars<T,V> dsvars, V screen,IDoomSystem I) {
        SCREENWIDTH = sCREENWIDTH;
        SCREENHEIGHT = sCREENHEIGHT;
        this.ylookup=ylookup;
        this.columnofs=columnofs;
        this.dsvars = dsvars;
        this.screen = screen;
        this.I=I;
    }
    
    protected final void doRangeCheck(){
        if (dsvars.ds_x2 < dsvars.ds_x1 || dsvars.ds_x1 < 0 || dsvars.ds_x2 >= SCREENWIDTH
                || dsvars.ds_y > SCREENHEIGHT) {
            I.Error("R_DrawSpan: %d to %d at %d", dsvars.ds_x1, dsvars.ds_x2, dsvars.ds_y);
        }
    }

    @Override
    public final void invoke(SpanVars<T,V> dsvars) {
        this.dsvars=dsvars;
        invoke();
    }
    
}
