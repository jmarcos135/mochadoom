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

package m;

import doom.DoomStatus;
import doom.IDoomGame;
import hu.HU;
import i.DoomStatusAware;
import i.IDoomSystem;
import rr.RendererState;
import s.IDoomSound;
import timing.ITicker;
import v.DoomVideoRenderer;
import v.IVideoScaleAware;
import w.IWadLoader;

public abstract class AbstractDoomMenu
        implements IDoomMenu {

    ////////////////////// CONTEXT ///////////////////
    
    DoomStatus DM;
    IDoomGame DG;
    IWadLoader W;
    DoomVideoRenderer V;
    HU HU;
    RendererState R;
    IDoomSystem I;
    IDoomSound S;
    ITicker TICK;
    
    @Override
    public void updateStatus(DoomStatus DS) {
           this.DM=DS.DM;
           this.DG=DS.DG;
            this.V=DM.V;
            this.W=DM.W;
            this.HU=DM.HU;
            this.I=DM.I;
            this.S=DM.S;
            this.R=(RendererState) DM.R;
            this.TICK=DM.TICK;
        
    }
    
}
