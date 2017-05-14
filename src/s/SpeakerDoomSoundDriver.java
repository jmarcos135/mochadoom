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

import w.DoomBuffer;
import doom.DoomStatus;

/** A variation of the Classic Sound Driver, decoding the DP-lumps
 *  instead of the DS. A better way would be to build-in an 
 *  automatic "WAV to SPEAKER" conversion, but that can wait...
 * 
 * @author Maes
 *
 */

public class SpeakerDoomSoundDriver extends ClassicDoomSoundDriver {

    public SpeakerDoomSoundDriver(DoomStatus DS, int numChannels) {
        super(DS, numChannels);
        // TODO Auto-generated constructor stub
    }
	
    /** Rigged so it gets SPEAKER sounds instead of regular ones */
    
    @Override
    protected byte[] getsfx
    ( String         sfxname,
            int[]          len, int index )
    {
        byte[]      sfx;
        byte[]      paddedsfx;
        int                 i;
        int                 size;
        int                 paddedsize;
        String                name;
        int                 sfxlump;

        // Get the sound data from the WAD, allocate lump
        //  in zone memory.
        name=String.format("dp%s", sfxname).toUpperCase();

        // Now, there is a severe problem with the
        //  sound handling, in it is not (yet/anymore)
        //  gamemode aware. That means, sounds from
        //  DOOM II will be requested even with DOOM
        //  shareware.
        // The sound list is wired into sounds.c,
        //  which sets the external variable.
        // I do not do runtime patches to that
        //  variable. Instead, we will use a
        //  default sound for replacement.
        if ( DS.W.CheckNumForName(name) == -1 )
            sfxlump = DS.W.GetNumForName("dppistol");
        else
            sfxlump = DS.W.GetNumForName(name);

        // We must first load and convert it to raw samples.
        
        SpeakerSound SP=(SpeakerSound) DS.W.CacheLumpNum(sfxlump, 0,SpeakerSound.class);
        
        sfx = SP.toRawSample();
        
        size = sfx.length;

        // MAES: A-ha! So that's how they do it.
        // SOund effects are padded to the highest multiple integer of 
        // the mixing buffer's size (with silence)

        paddedsize = ((size-8 + (SAMPLECOUNT-1)) / SAMPLECOUNT) * SAMPLECOUNT;

        // Allocate from zone memory.
        paddedsfx = new byte[paddedsize];

        // Now copy and pad. The first 8 bytes are header info, so we discard them.
        System.arraycopy(sfx,8, paddedsfx, 0,size-8 );
        
        for (i=size-8 ; i<paddedsize ; i++)
            paddedsfx[i] = (byte) 127;

        
        
        // Hmm....silence?
        for (i=size-8 ; i<paddedsize ; i++)
            paddedsfx[i] = (byte) 127;

        // Remove the cached lump.
        DS.W.UnlockLumpNum(sfxlump);

        if (D) System.out.printf("SFX %d size %d padded to %d\n",index,size,paddedsize);
        // Preserve padded length.
        len[index] = paddedsize;

        // Return allocated padded data.
        // So the first 8 bytes are useless?
        return paddedsfx;
    }

}
