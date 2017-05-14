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

package data;

import defines.statenum_t;
import data.sounds.sfxenum_t;

public class mobjinfo_t {
   
        public mobjinfo_t(int doomednum, statenum_t spawnstate, int spawnhealth,
                statenum_t seestate, sfxenum_t seesound, int reactiontime,
                sfxenum_t attacksound, statenum_t painstate,
                int painchance, sfxenum_t painsound,
                statenum_t meleestate, statenum_t missilestate,
                statenum_t deathstate, statenum_t xdeathstate,
                sfxenum_t deathsound, int speed, int radius, int height,
                int mass, int damage, sfxenum_t activesound, long flags,
                statenum_t raisestate) {
            super();
            this.doomednum = doomednum;
            this.spawnstate = spawnstate;
            this.spawnhealth = spawnhealth;
            this.seestate = seestate;
            this.seesound = seesound;
            this.reactiontime = reactiontime;
            this.attacksound = attacksound;
            this.painstate = painstate;
            this.painchance = painchance;
            this.painsound = painsound;
            this.meleestate = meleestate;
            this.missilestate = missilestate;
            this.deathstate = deathstate;
            this.xdeathstate = xdeathstate;
            this.deathsound = deathsound;
            this.speed = speed;
            this.radius = radius;
            this.height = height;
            this.mass = mass;
            this.damage = damage;
            this.activesound = activesound;
            this.flags = flags;
            this.raisestate = raisestate;
        }
        
        public int doomednum;
        public statenum_t spawnstate;
        public int spawnhealth;
        public statenum_t seestate;
        public sfxenum_t seesound;
        public int reactiontime;
        public sfxenum_t attacksound;
        public statenum_t painstate;
        public int painchance;
        public sfxenum_t painsound;
        public statenum_t meleestate;
        public statenum_t missilestate;
        public statenum_t deathstate;
        public statenum_t xdeathstate;
        public sfxenum_t deathsound;
        public int speed;
        public int radius;
        public int height;
        public int mass;
        public int damage;
        public sfxenum_t activesound;
        public long flags;
        public statenum_t raisestate;
    }
