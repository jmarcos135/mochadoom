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

package i;

public final class Strings {
	public static final String MOCHA_DOOM_TITLE="Mocha Doom Alpha 1.6";	
	
	public static final String MODIFIED_GAME=
		("===========================================================================\n"+
		 "ATTENTION:  This version of DOOM has been modified.  If you would like to\n"+
         "get a copy of the original game, call 1-800-IDGAMES or see the readme file.\n"+
         "        You will not receive technical support for modified games.\n"+
         "                      press enter to continue\n"+
         "===========================================================================\n");
	
	public static final String MODIFIED_GAME_TITLE="Modified game alert";
	
	public static final String MODIFIED_GAME_DIALOG=
		
		("<html><center>"+
		"===========================================================================<br>"+
		 "ATTENTION:  This version of DOOM has been modified.  If you would like to<br>"+
         "get a copy of the original game, call 1-800-IDGAMES or see the readme file.<br>"+
         "        You will not receive technical support for modified games.<br>"+
         "                      press OK to continue<br>"+
         "===========================================================================<br>"+
         "</center></html>");
	
	public static final String LEVEL_FAILURE_TITLE="Level loading failure";
	
	public static final String LEVEL_FAILURE_CAUSE=
		
		("<html><center>"+
		"Level loading failed!<br>"+
		 "Press OK to end this game without exiting, or cancel to quit Doom."+
         "</center></html>");
	
}
