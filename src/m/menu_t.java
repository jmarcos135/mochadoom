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

/** General form for a classic, Doom-style menu with a bunch of
 *  items and a drawing routine (menu_t's don't have action callbacks
 *  proper, though).
 * 
 * @author Maes
 *
 */

public class menu_t {
	    public menu_t(int numitems, menu_t prev, menuitem_t[] items,
            DrawRoutine drawroutine, int x, int y, int lastOn) {
	        this.numitems=numitems;
	        this.prevMenu=prev;
	        this.menuitems=items;
	        this.routine=drawroutine;
	        this.x=x;
	        this.y=y;
	        this.lastOn=lastOn;
	        
    }
	    /** # of menu items */	    
        public int		numitems;
        
	    /**  previous menu */
	    public menu_t	prevMenu;

	    /** menu items */
	    public menuitem_t[]		menuitems;	
	    /** draw routine */
	    public DrawRoutine routine;
	    /**  x,y of menu */
	    public int		x,y;
	    /** last item user was on in menu */
	    public int		lastOn;
	} 
