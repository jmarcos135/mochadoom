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

import defines.skill_t;

/** Groups functions formerly in d_game, 
 * in case you want to provide a different implementation 
 */

public interface IDoomGame {

	 void ExitLevel ();
	 void WorldDone ();

     boolean CheckDemoStatus();
     
     /** Can be called by the startup code or M_Responder.
     A normal game starts at map 1,
     but a warp test can start elsewhere */
     
     public void DeferedInitNew (skill_t skill, int episode, int map);
     
     /** Can be called by the startup code or M_Responder,
     calls P_SetupLevel or W_EnterWorld. */
   public void LoadGame (String name);

   /** Called by M_Responder. */
   public void SaveGame (int slot, String description);
   
   /** Takes a screenshot *NOW*
    * 
    */
   public void ScreenShot() ;
   
   public void StartTitle();
   
   public gameaction_t getGameAction();

   public void setGameAction(gameaction_t ga);
   
   // public void PlayerReborn(int player);
   
   void DeathMatchSpawnPlayer(int playernum);

}
