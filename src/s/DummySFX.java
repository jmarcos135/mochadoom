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

import data.sfxinfo_t;

public class DummySFX implements ISoundDriver {

	@Override
	public boolean InitSound() {
		// Dummy is super-reliable ;-)
		return true;
	}

	@Override
	public void UpdateSound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void SubmitSound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ShutdownSound() {
		// TODO Auto-generated method stub

	}

	@Override
	public int GetSfxLumpNum(sfxinfo_t sfxinfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int StartSound(int id, int vol, int sep, int pitch, int priority) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void StopSound(int handle) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean SoundIsPlaying(int handle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void UpdateSoundParams(int handle, int vol, int sep, int pitch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void SetChannels(int numChannels) {
		// TODO Auto-generated method stub
		
	}

}
