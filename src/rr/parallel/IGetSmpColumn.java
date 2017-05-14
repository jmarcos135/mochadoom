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

package rr.parallel;

import rr.column_t;

/** An interface used to ease the use of the GetCachedColumn by part
 *  of parallelized renderers.
 *  
 * @author Maes
 *
 */

/**
 * Special version of GetColumn meant to be called concurrently by different
 * seg rendering threads, identfiex by index. This serves to avoid stomping
 * on mutual cached textures and causing crashes.
 * 
 */

public interface IGetSmpColumn {

	column_t GetSmpColumn(int tex, int col,int id);

}
