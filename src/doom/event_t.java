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

// Event structure.

public class event_t {
	
	public event_t(){
	}

	   public event_t(event_t t){
	        this.type = t.type;
	        this.data1 = t.data1;
	        this.data2 = t.data2;
	        this.data3 = t.data3;
	    }

	
    public event_t(evtype_t type, int data) {
        this.type = type;
        this.data1 = data;
    }

    public event_t(char c) {
        this.type = evtype_t.ev_keydown;
        this.data1 = c;
    }

    public event_t(evtype_t type, int data1, int data2, int data3) {
        this.type = type;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
    }
    
    public void setFrom(event_t t){
        this.type = t.type;
        this.data1 = t.data1;
        this.data2 = t.data2;
        this.data3 = t.data3;
    }

    public evtype_t type;

    /** keys / mouse/joystick buttons, or wheel rotation amount */
    public int data1;

    /** mouse/joystick x move */
    public int data2;

    /** mouse/joystick y move */
    public int data3;

};
