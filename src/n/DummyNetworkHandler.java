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

import doom.IDoomGameNetworking;
import doom.doomcom_t;

public class DummyNetworkHandler implements IDoomGameNetworking{

    @Override
    public void NetUpdate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void TryRunTics() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public doomcom_t getDoomCom() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDoomCom(doomcom_t doomcom) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getTicdup() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setTicdup(int ticdup) {
        // TODO Auto-generated method stub
        
    }

}
