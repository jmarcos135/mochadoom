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

package i;

import doom.ticcmd_t;

public interface IDoomSystem {

	public void AllocLow(int length);

	public void BeginRead();

	public void EndRead();

	public void WaitVBL(int count);

	public byte[] ZoneBase(int size);

	public int GetHeapSize();

	public void Tactile(int on, int off, int total);

	public void Quit();

	public ticcmd_t BaseTiccmd();

	public void Error(String error, Object ... args);

	void Error(String error);
	
	void Init();
	
	/** Generate a blocking alert with the intention of continuing or aborting
	 * a certain game-altering action. E.g. loading PWADs, or upon critical 
	 * level loading failures. This can be either a popup panel or console 
	 * message.
	 *  
	 * @param cause Provide a clear string explaining why the alert was generated
	 * @return true if we should continue, false if an alternate action should be taken.
	 */
	boolean GenerateAlert(String title,String cause);


}
