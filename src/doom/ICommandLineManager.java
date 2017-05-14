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

/** A shiny new and enterprisey (yeah right) interface for
 *  command-line handling. No more argv/argc nastiness!
 *  
 * @author velktron
 *
 */

public interface ICommandLineManager {

    public abstract String getArgv(int index);

    public abstract int getArgc();

    /**
     * M_CheckParm Checks for the given parameter in the program's command line
     * arguments. Returns the argument number (1 to argc-1) or 0 if not present
     * 
     * OK, now WHY ON EARTH was this be defined in m_menu.c?
     * 
     * MAES: this needs to be modified for Java, or else bump myargc one element up.
     * 
     */

    public abstract int CheckParm(String check);

    void FindResponseFile();

    public abstract void setArgv(int index, String string);

	boolean CheckParmBool(String check);

}