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

package p;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import rr.SectorAction;
import rr.sector_t;
import w.DoomIO;
import w.IReadableDoomObject;

public class plat_t extends SectorAction implements IReadableDoomObject{
        
        public sector_t   sector;
        /** fixed_t */
        public int speed,low,high;
        int     wait;
        int     count;
        public plat_e  status;
        public plat_e  oldstatus;
        boolean crush;
        public int     tag;
        public plattype_e  type;
        
        public plat_t (){
        	// These must never be null so they get the lowest ordinal value.
        	// by default.
        	this.status=plat_e.up;
        	this.oldstatus=plat_e.up;
        }
        
        @Override
        public void read(DataInputStream f) throws IOException{

            super.read(f); // Call thinker reader first            
            super.sectorid=DoomIO.readLEInt(f); // Sector index
            speed=DoomIO.readLEInt(f);
            low=DoomIO.readLEInt(f);
            high=DoomIO.readLEInt(f);
            wait=DoomIO.readLEInt(f);
            count=DoomIO.readLEInt(f);
            status=plat_e.values()[DoomIO.readLEInt(f)];
            oldstatus=plat_e.values()[DoomIO.readLEInt(f)];
            System.out.println(status);
            System.out.println(oldstatus);
            crush=DoomIO.readIntBoolean(f);
            tag=DoomIO.readLEInt(f);
            type=plattype_e.values()[DoomIO.readLEInt(f)];        
            }
        
        @Override
        public void pack(ByteBuffer b) throws IOException{
            super.pack(b); //12            
            b.putInt(super.sectorid); // 16
            b.putInt(speed);//20
            b.putInt(low); // 24
            b.putInt(high); //28
            b.putInt(wait); //32
            b.putInt(count); //36
            b.putInt(status.ordinal()); //40
            b.putInt(oldstatus.ordinal()); //44
            System.out.println(status);
            System.out.println(oldstatus);
            b.putInt(crush?1:0); // 48
            b.putInt(tag); // 52
            b.putInt(type.ordinal()); // 56
        }
        
        public vldoor_t asVlDoor(sector_t[] sectors){
        	/*
        	typedef struct
        	{
        	    thinker_t	thinker;
        	    vldoor_e	type;
        	    sector_t*	sector;
        	    fixed_t	topheight;
        	    fixed_t	speed;

        	    // 1 = up, 0 = waiting at top, -1 = down
        	    int             direction;
        	    
        	    // tics to wait at the top
        	    int             topwait;
        	    // (keep in case a door going down is reset)
        	    // when it reaches 0, start going down
        	    int             topcountdown;
        	    
        	} vldoor_t;
        	*/
        	
        	vldoor_t tmp=new vldoor_t();
        	tmp.next=this.next;
        	tmp.prev=this.prev;
        	tmp.function=this.function;
        	tmp.type=vldoor_e.values()[sector.id%vldoor_e.VALUES];
        	tmp.sector=sectors[this.speed%sectors.length];
        	tmp.topheight=this.low;
        	tmp.speed=this.high;
        	tmp.direction=this.wait;
        	tmp.topwait=this.count;
        	tmp.topcountdown=this.status.ordinal();

        	
        	return tmp;
        }
        
    } 