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

import p.mobj_t;
import data.sounds.musicenum_t;
import data.sounds.sfxenum_t;

/** Does nothing. Just allows me to code without
 *  commenting out ALL sound-related code. Hopefully
 *  it will be superseded by a real sound driver one day.
 * 
 * @author Velktron
 *
 */

public class DummySoundDriver implements IDoomSound{

	@Override
	public void Init(int sfxVolume, int musicVolume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StartSound(ISoundOrigin origin, int sound_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StartSound(ISoundOrigin origin, sfxenum_t sound_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StartSoundAtVolume(ISoundOrigin origin, int sound_id, int volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StopSound(ISoundOrigin origin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ChangeMusic(int musicnum, boolean looping) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StopMusic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PauseSound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ResumeSound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateSounds(mobj_t listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetMusicVolume(int volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetSfxVolume(int volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StartMusic(int music_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StartMusic(musicenum_t music_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ChangeMusic(musicenum_t musicnum, boolean looping) {
		// TODO Auto-generated method stub
		
	}

}
