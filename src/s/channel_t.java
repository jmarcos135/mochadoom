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

package s;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

import p.mobj_t;
import data.sfxinfo_t;

public class channel_t 
{
	
	public channel_t(){
		sfxinfo=new sfxinfo_t();
	}
    
	/** Currently playing sound. If null, then it's free */
	DoomSound currentSound = null;
	
    sfxinfo_t	sfxinfo;

    // origin of sound (usually a mobj_t).
    mobj_t	origin;

    // handle of the sound being played
    int		handle;
    
    AudioFormat format;
    
	public int sfxVolume;
    
	SourceDataLine auline = null;
}
