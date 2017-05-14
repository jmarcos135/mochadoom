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
import defines.*;

//
// PSPRITE ACTIONS for waepons.
// This struct controls the weapon animations.
//
// Each entry is:
//   ammo/amunition type
//  upstate
//  downstate
// readystate
// atkstate, i.e. attack/fire/hit frame
// flashstate, muzzle flash
//

public class weaponinfo_t {

    /*    
    public weaponinfo_t(ammotype_t ammo, int upstate, int downstate,
            int readystate, int atkstate, int flashstate) {
        super();
        this.ammo = ammo;
        this.upstate = upstate;
        this.downstate = downstate;
        this.readystate = readystate;
        this.atkstate = atkstate;
        this.flashstate = flashstate;
    }*/
        public ammotype_t  ammo;
             
        
        public weaponinfo_t(ammotype_t ammo, statenum_t upstate,
                statenum_t downstate, statenum_t readystate,
                statenum_t atkstate, statenum_t flashstate) {
            super();
            this.ammo = ammo;
            this.upstate = upstate;
            this.downstate = downstate;
            this.readystate = readystate;
            this.atkstate = atkstate;
            this.flashstate = flashstate;
        }
        
        public statenum_t     upstate;
        public statenum_t     downstate;
        public statenum_t     readystate;
        public statenum_t     atkstate;
        public statenum_t     flashstate;
        
        
        /*
        public int     upstate;
        public int     downstate;
        public int     readystate;
        public int     atkstate;
        public int     flashstate;
        */
    
}
