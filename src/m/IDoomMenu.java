package m;

import v.IVideoScaleAware;
import i.DoomStatusAware;
import doom.DoomStatus;
import doom.event_t;

// Emacs style mode select -*- C++ -*-
// -----------------------------------------------------------------------------
//
// $Id: IDoomMenu.java,v 1.5 2011/09/29 15:16:23 velktron Exp $
//
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
//
// DESCRIPTION:
// Menu widget stuff, episode selection and such.
//    
// -----------------------------------------------------------------------------

/**
 * 
 */

public interface IDoomMenu extends IVideoScaleAware, DoomStatusAware{

    //
    // MENUS
    //

    /**
     * Called by main loop, saves config file and calls I_Quit when user exits.
     * Even when the menu is not displayed, this can resize the view and change
     * game parameters. Does all the real work of the menu interaction.
     */
    public boolean Responder(event_t ev);

    /**
     * Called by main loop, only used for menu (skull cursor) animation.
     */
    public void Ticker();

    /**
     * Called by main loop, draws the menus directly into the screen buffer.
     */
    public void Drawer();

    /**
     * Called by D_DoomMain, loads the config file.
     */
    public void Init();

    /**
     * Called by intro code to force menu up upon a keypress, does nothing if
     * menu is already up.
     */
    public void StartControlPanel();

    public boolean getShowMessages();

    public void setShowMessages(boolean val);
    
    public int getScreenBlocks();
    
    public void setScreenBlocks(int val);
    
    public int getDetailLevel();

	void ClearMenus();
}
    