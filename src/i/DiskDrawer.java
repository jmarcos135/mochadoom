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

package i;

import doom.DoomStatus;
import rr.patch_t;
import v.DoomVideoRenderer;
import v.IVideoScale;
import w.IWadLoader;

public class DiskDrawer implements IDiskDrawer,DoomStatusAware {

	private patch_t disk;
	private IWadLoader W;
	private DoomVideoRenderer<?,?> V;
	private IVideoScale VS;
	private int timer=0;
	private String diskname;
	
	public static final String STDISK="STDISK";
	public static final String STCDROM="STCDROM";
	
	public DiskDrawer(DoomStatus<?,?> DM,String icon){		
		this.updateStatus(DM);
		this.diskname=icon;
	}

	@Override
	public void Init(){
		this.disk=W.CachePatchName(diskname);
	}
	
	@Override
	public void Drawer() {
		if (timer>0){
			if (timer%2==0)
		V.DrawScaledPatch(304,184,DoomVideoRenderer.SCREEN_FG,VS, disk);
		}
		if (timer>=0)
			timer--;
	}

	@Override
	public void updateStatus(DoomStatus<?,?> DC) {
		this.W=DC.W;
		this.V=DC.V;		
	    }

	@Override
	public void setVideoScale(IVideoScale vs) {
		this.VS = vs;
	}

	@Override
	public void initScaling() {

	}

	@Override
	public void setReading(int reading) {
		timer=reading;
	}

	@Override
	public boolean isReading() {
		return timer>0;
	}

	@Override
	public boolean justDoneReading() {
		return timer==0;
	}
	
}
