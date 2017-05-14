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

package m;

import static g.Keys.*;
import static doom.englsh.*;


/** An anumeration with the most basic default Doom settings their default
 *  values, used if nothing else is available. They are applied first thing, 
 *  and then updated from the .cfg file. 
 *  
 */

public enum Settings {
    mouse_sensitivity("5"),
    sfx_volume("8"),
   music_volume("8"),
   show_messages("1"),
   alwaysrun("0"),	// Always run is OFF
   key_right(KEY_RIGHTARROW),
   key_left(KEY_LEFTARROW),
   key_up('w'),
   key_down('s'),
   key_strafeleft('a'),
   key_straferight('d'),
   key_fire(KEY_CTRL),
   key_use(' '),
   key_strafe(KEY_ALT),
   key_speed(KEY_SHIFT),
   use_mouse(1),
   mouseb_fire(0),
   mouseb_strafe(2),	// AX: Fixed
   mouseb_forward(1),	// AX: Value inverted with the one above
   use_joystick( 0),
   joyb_fire(0),
   joyb_strafe(1),
   joyb_use(3),
   joyb_speed(2),
   screenblocks(10),
   detaillevel(0),
   snd_channels(32),
   usegamma(0),
   mb_used(2),
   chatmacro0(HUSTR_CHATMACRO0 ),
   chatmacro1(HUSTR_CHATMACRO1 ),
   chatmacro2( HUSTR_CHATMACRO2 ),
   chatmacro3(HUSTR_CHATMACRO3 ),
   chatmacro4( HUSTR_CHATMACRO4 ),
   chatmacro5( HUSTR_CHATMACRO5 ),
   chatmacro6(HUSTR_CHATMACRO6 ),
   chatmacro7( HUSTR_CHATMACRO7 ),
   chatmacro8(HUSTR_CHATMACRO8 ),
   chatmacro9( HUSTR_CHATMACRO9 );

         private Settings(String defaultval){
            this.value=defaultval;
        }
         

         private Settings(int defaultval){
            this.value=Integer.toString(defaultval);
        }
    
    public String value;
    
    /** Normally this is default.cfg, might be .doomrc on lunix??? */
    
    public static String basedefault="default.cfg";      

}