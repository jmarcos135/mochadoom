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

package demo;

import w.IWritableDoomObject;
import defines.skill_t;

public interface IDoomDemo extends IWritableDoomObject{
	
    
    /** Vanilla end demo marker, to append at the end of recorded demos */
    
   public static final int DEMOMARKER =0x80;
   
    /** Get next demo command, in its raw format. Use
     * its own adapters if you need it converted to a 
     * standard ticcmd_t.
     *  
     * @return
     */
    IDemoTicCmd getNextTic();
    
    /** Record a demo command in the IDoomDemo's native format.
     * Use the IDemoTicCmd's objects adaptors to convert it.
     * 
     * @param tic
     */
    void putTic(IDemoTicCmd tic);

    int getVersion();

    void setVersion(int version);

    skill_t getSkill();

    void setSkill(skill_t skill);

    int getEpisode();

    void setEpisode(int episode);

    int getMap();

    void setMap(int map);

    boolean isDeathmatch();

    void setDeathmatch(boolean deathmatch);

    boolean isRespawnparm();
    
    void setRespawnparm(boolean respawnparm);

    boolean isFastparm();

    void setFastparm(boolean fastparm);
    
    boolean isNomonsters();

    void setNomonsters(boolean nomonsters);

    int getConsoleplayer();

    void setConsoleplayer(int consoleplayer);

    boolean[] getPlayeringame();

    void setPlayeringame(boolean[] playeringame);

    void resetDemo();

    


}
