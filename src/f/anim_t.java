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

package f;

import w.animenum_t;
import rr.patch_t;

//
//Animation.
//There is another anim_t used in p_spec.
//

public class anim_t {

        public anim_t(animenum_t type, int period, int nanims, point_t loc,
            int data1, int data2, patch_t[] p, int nexttic, int lastdrawn,
            int ctr, int state) {
        this.type = type;
        this.period = period;
        this.nanims = nanims;
        this.loc = loc;
        this.data1 = data1;
        this.data2 = data2;
        this.p = p;
        this.nexttic = nexttic;
        this.lastdrawn = lastdrawn;
        this.ctr = ctr;
        this.state = state;
    }
        // Partial constructor, only 4 first fields.
        public anim_t(animenum_t animAlways, int period, int nanims, point_t loc
               ) {
            this.type = animAlways;
            this.period = period;
            this.nanims = nanims;
            this.loc = loc;
        }
        
        // Partial constructor, only 5 first fields.
        public anim_t(animenum_t type, int period, int nanims, point_t loc, int data1
               ) {
            this.type = type;
            this.period = period;
            this.nanims = nanims;
            this.loc = loc;
            this.data1=data1;
        }
        
        public animenum_t  type;

        // period in tics between animations
        public int     period;

        // number of animation frames
        public int     nanims;

        // location of animation
        point_t loc;

        // ALWAYS: n/a,
        // RANDOM: period deviation (<256),
        // LEVEL: level
        public int     data1;

        // ALWAYS: n/a,
        // RANDOM: random base period,
        // LEVEL: n/a
        public int     data2; 

        // actual graphics for frames of animations
        //Maes: was pointer to array
        public patch_t[] p= new patch_t[3];

        // following must be initialized to zero before use!

        // next value of bcnt (used in conjunction with period)
        public int     nexttic;

        // last drawn animation frame
        public int     lastdrawn;

        // next frame number to animate
        public int     ctr;
        
        // used by RANDOM and LEVEL when animating
        public int     state;  

    } 
