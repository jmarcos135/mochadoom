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

import v.IVideoScale;
import doom.DoomStatus;
import doom.event_t;

/** A dummy menu, useful for testers that do need a defined
 *  menu object.
 * 
 * @author Maes
 *
 */

public class DummyMenu
        extends AbstractDoomMenu {

    @Override
    public void setVideoScale(IVideoScale vs) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initScaling() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean Responder(event_t ev) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void Ticker() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Drawer() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void StartControlPanel() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getShowMessages() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setShowMessages(boolean val) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getScreenBlocks() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setScreenBlocks(int val) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getDetailLevel() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void updateStatus(DoomStatus DS) {
        // TODO Auto-generated method stub

    }

	@Override
	public void ClearMenus() {
		// TODO Auto-generated method stub
		
	}

}
