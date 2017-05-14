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

package doom;

import java.util.Hashtable;

import utils.C2JUtils;

import defines.GameMode_t;

public class DoomVersions {

	public final static Hashtable<String,GameMode_t> VersionChecker=new Hashtable<String,GameMode_t>();
	
	static {
		VersionChecker.put("doom.wad",GameMode_t.registered);
		VersionChecker.put("doom2.wad",GameMode_t.commercial);
		VersionChecker.put("udoom.wad",GameMode_t.retail);
		VersionChecker.put("tnt.wad",GameMode_t.pack_tnt);
		VersionChecker.put("plutonia.wad",GameMode_t.pack_plut);
		VersionChecker.put("doom1.wad",GameMode_t.shareware);
		VersionChecker.put("xbla.wad",GameMode_t.pack_xbla);
	}
	
	public DoomVersions(){
		
	}
	
	/** Try all versions in given doommwaddir
	 * 
	 */
	
	public void tryThemAll(String doomwaddir){
		 // Commercial.
        doom2wad = (doomwaddir+ "/doom2.wad");

        // Retail.
        doomuwad = (doomwaddir+ "/doomu.wad");    

        // Registered.
        doomwad = (doomwaddir+ "/doom.wad");

        // Shareware.
        doom1wad = (doomwaddir+ "/doom1.wad");

        // Bug, dear Shawn.
        // Insufficient malloc, caused spurious realloc errors.
        plutoniawad = (doomwaddir+ "/plutonia.wad");

        tntwad = (doomwaddir+ "/tnt.wad");

        xblawad = (doomwaddir+ "/xbla.wad");
        
        // French stuff.
        doom2fwad=(doomwaddir+ "/doom2f.wad");
	}
	
	public String 
	doom1wad,
    doomwad,
    doomuwad,
    doom2wad,
    doom2fwad,
    plutoniawad,    
    tntwad,
    xblawad;
	
	/** Try only one IWAD. 
	 * 
	 * @param iwad
	 * @return
	 */

	public GameMode_t tryOnlyOne(String iwad, String doomwaddir) {
		
		// Is it a known and valid version?
		GameMode_t tmp=VersionChecker.get(iwad.toLowerCase());
		if (tmp!=null) {
		// Can we read it?
		if (C2JUtils.testReadAccess(doomwaddir+iwad))
			return tmp; // Yes, so communicate the gamemode back.
		}
		
		// It's either invalid or we can't read it.
		// Fuck that.
		return null;
	}
	
	
}
