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

package p;

import m.fixed_t;

public interface DoomPlayer {
	
	public fixed_t
	AimLineAttack
	( mobj_t	t1,
	  int	angle,
	  fixed_t	distance );

	public void
	LineAttack
	( mobj_t	t1,
	  int	angle,
	  fixed_t	distance,
	  fixed_t	slope,
	  int		damage );

	void
	RadiusAttack
	( mobj_t	spot,
	  mobj_t	source,
	  int		damage );
	
	void
	TouchSpecialThing
	( mobj_t	special,
	  mobj_t	toucher );

	void
	DamageMobj
	( mobj_t	target,
	  mobj_t	inflictor,
	  mobj_t	source,
	  int		damage );
	}
