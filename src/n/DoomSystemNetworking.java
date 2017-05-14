package n;

// Emacs style mode select   -*- C++ -*- 
//-----------------------------------------------------------------------------
//
// $Id: DoomSystemNetworking.java,v 1.1 2010/11/17 23:55:06 velktron Exp $
//
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
//  System specific network interface stuff.
//
//-----------------------------------------------------------------------------

public interface DoomSystemNetworking{


// Called by D_DoomMain.

public void InitNetwork ();
public void NetCmd ();


}

//-----------------------------------------------------------------------------
//
// $Log: DoomSystemNetworking.java,v $
// Revision 1.1  2010/11/17 23:55:06  velktron
// Kind of playable/controllable.
//
// Revision 1.1  2010/10/22 16:22:43  velktron
// Renderer works stably enough but a ton of bleeding. Started working on netcode.
//
//
//-----------------------------------------------------------------------------

