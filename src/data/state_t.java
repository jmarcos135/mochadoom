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

import p.ActionType1;
import p.ActionType2;
import p.ActionTypeSS;
import doom.think_t;
import defines.statenum_t;
import static data.Defines.TIC_MUL;

public class state_t {

	public state_t(){
		
	}
       
        public state_t(spritenum_t sprite, int frame, int tics, think_t action,
            statenum_t nextstate, int misc1, int misc2) {
        this.sprite = sprite;
        this.frame = frame;
        this.tics = tics*TIC_MUL;
        this.action = action;
        this.nextstate = nextstate;
        this.misc1 = misc1;
        this.misc2 = misc2;
        }
        
        public spritenum_t   sprite;
        /** The frame should indicate which one of the frames available in the 
         *  available spritenum should be used. This can also be flagged with
         *  0x8000 indicating bright sprites.
         */
 
        public int          frame;
        public int          tics;
        //TODO: proper implementation of (*action)
        // MAES: was actionp_t... which is typedeffed to think_t anyway,
        // and this is the only place it's invoked explicitly.
        /** OK...this is the most infamous part of Doom to implement in Java.
         *  We can't have proper "function pointers" in java without either losing a LOT
         *  of speed (through reflection) or cluttering syntax and heap significantly
         *  (callback objects, which also need to be aware of context).
         *  Therefore, I decided to implement an "action dispatcher".
         *  This a
         *  
         */
        public think_t         action;
        public ActionType1 acp1;
        public ActionType2 acp2;
        public ActionTypeSS acpss;
        
        public statenum_t            nextstate;
        public int misc1, misc2;
        /** relative index in state array. Needed sometimes. */
        public int id;
		
        
        public String toString(){
            sb.setLength(0);
            sb.append(this.getClass().getName());
            sb.append(" sprite ");
            sb.append(this.sprite.name());
            sb.append(" frame ");
            sb.append(this.frame);

            return sb.toString();
            
        }
        
        protected static StringBuilder sb=new StringBuilder();

        /*@Override
        public void read(DoomFile f)
                throws IOException {
            this.sprite = spritenum_t.values()[f.readLEInt()];
            this.frame = f.readLEInt();
            this.tics = f.readLong();
            this.action = think_t.values()[f.readInt()];
            this.nextstate = statenum_t.values()[f.readInt()];
            this.misc1 = f.readInt();
            this.misc2 = f.readInt();
            
        } */
}
