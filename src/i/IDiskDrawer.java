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

import v.IVideoScaleAware;

public interface IDiskDrawer extends IDrawer,IVideoScaleAware{
	
	/** Set a timeout (in tics) for displaying the disk icon 
	 * 
	 * @param timeout
	 */
	
	void setReading(int reading);
	
	/** Disk displayer is currently active
	 * 
	 * @return
	 */
	boolean isReading();
	
	/** Only call after the Wadloader is instantiated and
	 *  initialized itself.
	 * 
	 */
	void Init();
	
	/**Status only valid after the last tic has been drawn.
	 * Use to know when to redraw status bar.
	 * 
	 * @return
	 */
	boolean justDoneReading();
    	
}
