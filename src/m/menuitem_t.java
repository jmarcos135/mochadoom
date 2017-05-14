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

public class menuitem_t {

	    public menuitem_t(int status, String name, MenuRoutine routine, char alphaKey) {
	    	this.status=status;
	    	this.name=name;
	    	this.routine= routine;
	    	this.alphaKey=alphaKey;
	}	

		public menuitem_t(int status, String name, MenuRoutine routine) {
		     this.status=status;
	            this.name=name;
	            this.routine= routine;
        }

        /** 0 = no cursor here, 1 = ok, 2 = arrows ok */
	    int	status;
	    
	    String	name;
	    
	    // choice = menu item #.
	    // if status = 2,
	    //   choice=0:leftarrow,1:rightarrow
	    // MAES: OK... to probably we need some sort of "MenuRoutine" class for this one.
	    // void	(*routine)(int choice);
	    MenuRoutine routine;
	    
	    /** hotkey in menu */
	    char	alphaKey;			
	} 