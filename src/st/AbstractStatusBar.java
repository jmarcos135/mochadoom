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

package st;

import m.IRandom;
import doom.DoomMain;
import doom.DoomStatus;
import i.DoomVideoInterface;
import i.IDoomSystem;
import rr.Renderer;
import s.IDoomSound;
import v.DoomVideoRenderer;
import v.IVideoScaleAware;
import w.IWadLoader;

public abstract class AbstractStatusBar implements IDoomStatusBar, IVideoScaleAware{

	 // /// STATUS //////////

    protected DoomVideoRenderer<?,?> V;

    protected IWadLoader W;

    protected Renderer<?,?> R;

    protected DoomMain<?,?> DM;

    protected IRandom RND;
    
    protected IDoomSystem I;
    
    protected DoomVideoInterface<?> VI;

    protected IDoomSound S;
    
	@Override
	public void updateStatus(DoomStatus<?,?> DC) {
        this.DM=DC.DM;
        this.V=DC.V;
        this.W=DC.W;
        this.RND=DC.RND;
        this.R= DC.R;
        this.VI=DC.VI;
        this.I=DC.I;
        this.S=DC.S;
    }
	
}
