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

package m;

public interface IVariablesManager {
    
    /** Does two things: sets a variable, and then calls setme.
     * This way, immediate updates of a single variables user 
     * are possible, avoiding some of the overhead. 
     * 
     * Sadly, users with too many variables will still incur an
     * O(n) penalty.
     * 
     * @param setme
     * @param name
     * @param value
     */
    
    public void applySetting(IUseVariables setme,String name, String value);

    public DoomSetting getSetting(String name);
    
    public DoomSetting getSetting(Settings name);

    public boolean isSettingLiteral(String name,String value);
    
 /** Creates a new setting, overriding any existing ones.
  *   
  * @param name
  * @param value
  * @param persist
  */
    
    public void putSetting(String name, Object value, boolean persist);
    
    /** Puts a new setting or updates an existing one. In this case,
     * the value of the "persist" field is kept unaltered, so that persistent
     * settings are not lost during updates. 
     * 
     * @param name
     * @param value
     */
    
    public void putSetting(String name, Object value);
    public void putSetting(Settings name, Object value);

    void LoadDefaults(String defaultfile);

    void SaveDefaults(String defaultfile);
    
    String getDefaultFile();
    
}
