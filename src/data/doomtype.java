package data;
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
//
// DESCRIPTION:
//	Simple basic typedefs, isolated here to make it easier
//	 separating modules.
//    


public class doomtype {

// C's "chars" are actually Java signed bytes.
public static byte MAXCHAR =((byte)0x7f);
public static short MAXSHORT=	((short)0x7fff);

// Max pos 32-bit int.
public static int MAXINT=((int)0x7fffffff);	
public static long MAXLONG=((long)0x7fffffff);
public static byte MINCHAR=((byte)0x80);
public static short MINSHORT=((short)0x8000);

// Max negative 32-bit integer.
public static int MININT=((int)0x80000000);
public static long MINLONG=((long)0x80000000);
}