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

package f;

import doom.DoomStatus;
import m.IRandom;
import v.DoomVideoRenderer;
import v.IVideoScale;
import v.IVideoScaleAware;
import i.DoomStatusAware;
import i.DoomVideoInterface;

public abstract class AbstractWiper<T,V> implements IWiper, DoomStatusAware, IVideoScaleAware{
    
    //
    //                           SCREEN WIPE PACKAGE
    //

    /** These don't seem to be used anywhere */
    
    public static enum wipe
    {
        // simple gradual pixel change for 8-bit only
        // MAES: this transition isn't guaranteed to always terminate
        // see Chocolate Strife develpment. Unused in Doom anyway.
        ColorXForm,
        
        // weird screen melt
        Melt,  

        NUMWIPES
    };
    
    /** when false, stop the wipe */
    protected volatile boolean  go = false;

    protected V    wipe_scr_start;
    protected V    wipe_scr_end;
    protected V    wipe_scr;
    
////////////////////////////VIDEO SCALE STUFF ////////////////////////////////

    protected int SCREENWIDTH;
    protected int SCREENHEIGHT;
    protected int Y_SCALE;
    protected IVideoScale vs;


    @Override
    public void setVideoScale(IVideoScale vs) {
        this.vs=vs;
    }

    @Override
    public void initScaling() {
        this.SCREENHEIGHT=vs.getScreenHeight();
        this.SCREENWIDTH=vs.getScreenWidth();
        this.Y_SCALE=vs.getScalingY();

        // Pre-scale stuff.
    }

    ///////////////////// STATUS /////////////////////

    @SuppressWarnings("unchecked")
    @Override
    public void updateStatus(DoomStatus<?,?> DS){
    this.RND=DS.RND;
    this.V=(DoomVideoRenderer<T, V>) DS.V;
    this.VI=(DoomVideoInterface<V>) DS.VI;
    }
    
    IRandom RND;
    DoomVideoRenderer<T,V> V;
    DoomVideoInterface<V> VI;

    
}
