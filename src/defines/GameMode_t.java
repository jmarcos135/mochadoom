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

package defines;


/** Game mode handling - identify IWAD version
  to handle IWAD dependend animations etc.
  */
public enum GameMode_t 
{
shareware,    // DOOM 1 shareware, E1, M9
registered,   // DOOM 1 registered, E3, M27
commercial,   // DOOM 2 retail, E1 M34
// DOOM 2 german edition not handled
retail,   // DOOM 1 retail, E4, M36
pack_tnt, // TNT mission pack
pack_plut,    // Plutonia pack
pack_xbla,    // XBLA Doom. How you got hold of it, I don't care :-p
indetermined  // Well, no IWAD found.  
} ;
