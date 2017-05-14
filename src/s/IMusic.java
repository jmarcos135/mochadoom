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

//
//  MUSIC I/O
//

public interface IMusic {

	void InitMusic();
	void ShutdownMusic();
	// Volume.
	void SetMusicVolume(int volume);
	/** PAUSE game handling. */
	void PauseSong(int handle);
	void ResumeSong(int handle);
	
	/** Registers a song handle to song data. 
	 *  This should handle any conversions from MUS/MIDI/OPL/etc.
	 * 
	 * */
	int RegisterSong(byte[] data);
	
	
	/** Called by anything that wishes to start music.
	   plays a song, and when the song is done,
	  starts playing it again in an endless loop.
     Horrible thing to do, considering. */
	
	void
	PlaySong
	( int		handle,
	  boolean		looping );
	
	/** Stops a song over 3 seconds. */
	void StopSong(int handle);
	
	/** See above (register), then think backwards */
	void UnRegisterSong(int handle);
}
