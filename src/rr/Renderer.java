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

import static data.Tables.FINEANGLES;
import static m.fixed_t.FRACUNIT;
import rr.drawfuns.ColFuncs;
import rr.drawfuns.ColVars;
import rr.drawfuns.SpanVars;
import i.DoomStatusAware;
import i.IDoomSystem;
import v.IVideoScaleAware;
import w.IWadLoader;
import doom.player_t;

public interface Renderer<T,V> extends IVideoScaleAware,DoomStatusAware{
	


	/** Fineangles in the SCREENWIDTH wide window. */
	public static final int FIELDOFVIEW = FINEANGLES / 4;
	
	public static final int MINZ = (FRACUNIT * 4);

	
	public static final int FUZZTABLE = 50;
	
	/**
	 * killough: viewangleoffset is a legacy from the pre-v1.2 days, when Doom
	 * had Left/Mid/Right viewing. +/-ANG90 offsets were placed here on each
	 * node, by d_net.c, to set up a L/M/R session.
	 */

	public static final long viewangleoffset = 0;
	
	
	public void Init();
	
	public void RenderPlayerView(player_t player);
	
	public void ExecuteSetViewSize();
	
	public void FillBackScreen();
	
	public void DrawViewBorder();
	
	public void SetViewSize(int size, int detaillevel);
	
	public void VideoErase(int offset, int width);
	
	public long PointToAngle2(int x1, int y1, int x2, int y2);
	
	public void PreCacheThinkers();
	
	public int getValidCount();
	
	public void increaseValidCount(int amount);

	public boolean isFullHeight();

	public void resetLimits();

	public boolean getSetSizeNeeded();
	
	public boolean isFullScreen();

	// Isolation methods
	
	public TextureManager<T> getTextureManager();
	
	public PlaneDrawer<T,V> getPlaneDrawer();

    public ViewVars getView();

    public SpanVars<T, V> getDSVars();

    public LightsAndColors<V> getColorMap();

    public IDoomSystem getDoomSystem();
    
    public IWadLoader getWadLoader();

    /** Use this to "peg" visplane drawers (even parallel ones) to
     *  the same set of visplane variables.
     *  
     * @return
     */
    
    public Visplanes getVPVars();

    public SegVars getSegVars();

    public ISpriteManager getSpriteManager();

    public BSPVars getBSPVars();

    public IVisSpriteManagement<V> getVisSpriteManager();

    public ColFuncs<T,V> getColFuncsHi();
    public ColFuncs<T,V> getColFuncsLow();

    public ColVars<T, V> getMaskedDCVars();
    
	
	//public subsector_t PointInSubsector(int x, int y);

}
