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

package savegame;

import defines.skill_t;


/** A Save Game Header should be able to be loaded quickly and return 
 *  some basic info about it (name, version, game time, etc.) in an unified
 *  manner, no matter what actual format you use for saving.
 * 
 * @author admin
 *
 */

public interface IDoomSaveGameHeader {

    String getName();

    void setName(String name);

    skill_t getGameskill();

    void setGameskill(skill_t gameskill);
    
    String getVersion();

    void setVersion(String vcheck);

    int getGameepisode();
    
    void setGameepisode(int gameepisode);

    boolean isProperend();

    void setWrongversion(boolean wrongversion);

    boolean isWrongversion();

    void setLeveltime(int leveltime);

    int getLeveltime();

    void setPlayeringame(boolean[] playeringame);

    boolean[] getPlayeringame();

    void setGamemap(int gamemap);

    int getGamemap();

}
