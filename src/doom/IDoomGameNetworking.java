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

package doom;

import java.io.IOException;

/** Doom is actually tied to its networking module.
 *  Therefore, no matter where and how you implement it, these functions
 *  need to be callable from within many modules.
 *  
 *  This is the so called "game networking" which is internal and game-specific,
 *  and not system networking which deals with the low level sockets and packet 
 *  stuff. You'll need DoomSystemNetworking for that one.
 *  
 * @author Velktron
 *
 */

public interface IDoomGameNetworking {
	
	public void TryRunTics() throws IOException;
	
	/**
	 * NetUpdate
	 * Builds ticcmds for console player,
	 * sends out a packet
	 * @throws IOException 
	 */

	public void NetUpdate ();
	
	public doomcom_t getDoomCom();
	
	public void setDoomCom(doomcom_t doomcom);
	
	public int getTicdup();

	public void setTicdup(int ticdup);

}
