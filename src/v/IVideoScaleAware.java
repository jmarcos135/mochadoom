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
// along with Mocha Doom.  If not, see <http://www.gnu.org/licenses/>.

package v;

public interface IVideoScaleAware {    
    
    
    /** Set the video scale for a certain object. Setting
     * does NOT (re)initialize an object yet. This is only done
     * by calling the init() method at a safe moment.
     * 
     * @param vs
     */
    public void setVideoScale(IVideoScale vs);
    
    /** Initialize an object according to the current video scale
     * settings. This should adapt multipliers, static constants,
     * etc. and should be set before the object is first used
     * or after a dynamic (if ever implemented) resolution change.
     * 
     * The proposed method is to initialize everything en-bloc
     * before entering the display loop, and after initializing
     * 
     */    
    public void initScaling();

}
