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

public interface IUseVariables {
    
    
    /** Register a variable manager with this module.
     * 
     * @param manager
     */
    public void registerVariableManager(IVariablesManager manager);
    
    
    /** Apply listener-specific variables, asking the manager for them.
     *  Every listener should concern itself with its own variables/settings.
     *  
     *  This method should be called by the manager on every registered
     *  listener. Each listener then "knows" which settings it must update.
     *  
     *  Good for block updates, but maybe a more lightweight mechanism should
     *  be provided, e.g. being able to update just one setting for a listener.
     * 
     */
    public void update();
    
    /** If the variables user makes too many changes, it may be better to 
     * communicate them back to the manager in-block. This shouldn't be needed,
     * if everywhere a certain setting has to be modified tis done through the
     * manager.
     */
    
    public void commit();


}
