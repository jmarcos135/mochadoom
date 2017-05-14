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
// along with Mocha Doom.  If not, see <http://www.gnu.org/licenses/>.

package w;

// CPhipps - defined enum in wider scope
// Ty 08/29/98 - add source field to identify where this lump came from

public enum wad_source_t {
   // CPhipps - define elements in order of 'how new/unusual'
   source_iwad,    // iwad file load 
   source_pre,       // predefined lump
   source_auto_load, // lump auto-loaded by config file
   source_pwad,      // pwad file load
   source_lmp,       // lmp file load
   source_net        // CPhipps
   //e6y
 //  ,source_deh_auto_load
   ,source_deh
   ,source_err

 }
