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

package n;

import i.DoomStatusAware;
import i.IDoomSystem;
import doom.DoomMain;
import doom.DoomStatus;
import doom.IDoomGameNetworking;
import doom.NetConsts;
import doom.doomcom_t;

/** Does nothing.
 *  Allows running single-player games without an actual network.
 *  Hopefully, it will be replaced by a real UDP-based driver one day.
 *  
 * @author Velktron
 *
 */

public class DummyNetworkDriver implements NetConsts,DoomSystemNetworking, DoomStatusAware{

	////////////// STATUS ///////////

	IDoomSystem I;
	DoomMain DM;
	IDoomGameNetworking DGN;

	public DummyNetworkDriver(DoomStatus DC){
		updateStatus(DC);
	}

	@Override
	public void InitNetwork() {
		doomcom_t doomcom =new doomcom_t();
		doomcom.id=DOOMCOM_ID;
		doomcom.ticdup=1;

		// single player game
		DM.netgame = false;
		doomcom.id = DOOMCOM_ID;
		doomcom.numplayers = doomcom.numnodes = 1;
		doomcom.deathmatch = 0;
		doomcom.consoleplayer = 0;
		DGN.setDoomCom(doomcom);
	}

	@Override
	public void NetCmd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStatus(DoomStatus DC) {
		this.DM=DC.DM;
		this.DGN=DC.DM;
	}

}
