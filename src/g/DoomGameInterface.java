
package g;

import defines.*;
import doom.event_t;
import doom.gameaction_t;

//
// $Id: DoomGameInterface.java,v 1.4 2010/12/20 17:15:08 velktron Exp $
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



public interface DoomGameInterface {


//
// GAME
//
public void DeathMatchSpawnPlayer (int playernum);

public void InitNew (skill_t skill, int episode, int map);

/** Can be called by the startup code or M_Responder.
    A normal game starts at map 1,
    but a warp test can start elsewhere */
public void DeferedInitNew (skill_t skill, int episode, int map);

public void DeferedPlayDemo (String demo);

/** Can be called by the startup code or M_Responder,
  calls P_SetupLevel or W_EnterWorld. */
public void LoadGame (String name);

public void DoLoadGame ();

/** Called by M_Responder. */
public void SaveGame (int slot, String description);

/** Only called by startup code. */
public void RecordDemo (String name);

public void BeginRecording ();

public void PlayDemo (String name);
public void TimeDemo (String name);
public boolean CheckDemoStatus ();

public void ExitLevel ();
public void SecretExitLevel() ;

public void WorldDone() ;

public void Ticker() ;
public boolean Responder (event_t	ev);

public void ScreenShot() ;

public gameaction_t getGameAction();

public void setGameAction(gameaction_t ga);

public boolean getPaused();

public void setPaused(boolean on);
    
}
