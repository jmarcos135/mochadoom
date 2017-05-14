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

package utils;

/** Half-assed way of finding the OS we're running under, shamelessly 
 * ripped from:
 * 
 *  http://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
 * .
 * This is required, as some things in AWT don't work exactly consistently cross-OS
 * (AWT frame size is the first thing that goes wrong, but also mouse grabbing
 * behavior).
 * 
 * TODO: replace with Apache Commons library?
 *  
 * @author velktron
 *
 */

public class OSValidator{
 
	public static boolean isWindows(){
 
		String os = System.getProperty("os.name").toLowerCase();
		//windows
	    return (os.indexOf( "win" ) >= 0); 
 
	}
 
	public static boolean isMac(){
 
		String os = System.getProperty("os.name").toLowerCase();
		//Mac
	    return (os.indexOf( "mac" ) >= 0); 
 
	}
 
	public static boolean isUnix(){
 
		String os = System.getProperty("os.name").toLowerCase();
		//linux or unix
	    return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);
 
	}
	
	public static boolean isUnknown(){
		return (!isWindows() && !isUnix() && !isMac());
	}
}
