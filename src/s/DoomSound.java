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
import javax.sound.sampled.AudioFormat.Encoding;

import data.sfxinfo_t;

/** A class representing a sample in memory 
 *  Convenient for wrapping/mirroring it regardless of what it represents.
 * */
class DoomSound extends sfxinfo_t {

	/** This audio format is the one used by internal samples (16 bit, 11KHz, Stereo) 
     *  for Clips and AudioLines. Sure, it's not general enough... who cares though?
     */
	public final static AudioFormat DEFAULT_SAMPLES_FORMAT=new AudioFormat(Encoding.PCM_SIGNED, ISoundDriver.SAMPLERATE, 16, 2, 4, ISoundDriver.SAMPLERATE, true);
	
	public final static AudioFormat DEFAULT_DOOM_FORMAT=new AudioFormat(Encoding.PCM_UNSIGNED, ISoundDriver.SAMPLERATE, 8, 1, 1, ISoundDriver.SAMPLERATE, true);
	
	
	public AudioFormat format;
	
	public DoomSound(AudioFormat format) {
		this.format=format;
	}
	
	public DoomSound(){
		this.format=DEFAULT_DOOM_FORMAT;
	}
	
	public DoomSound(sfxinfo_t sfx,AudioFormat format){
		this(format);
		this.data=sfx.data;
		this.pitch=sfx.pitch;
		this.link=sfx.link;
		this.lumpnum=sfx.lumpnum;
		this.name=sfx.name;
		this.priority=sfx.priority;
		this.singularity=sfx.singularity;
		this.usefulness=sfx.usefulness;
		this.volume=sfx.volume;
		}
	
}
