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

package rr;

/** A sprite manager does everything but drawing the sprites. It creates lists
 *  of sprites-per-sector, sorts them, and stuff like that.
 *  that gory visibiliy
 *  
 * @author velkton
 *
 * @param <V>
 */


public interface IVisSpriteManagement<V> extends ILimitResettable {
    
    void AddSprites(sector_t sec);
    
    /** Cache the sprite manager, if possible */
    
    void cacheSpriteManager(ISpriteManager SM);
    
    void SortVisSprites();

    int getNumVisSprites();

    vissprite_t<V>[] getVisSprites();

    void ClearSprites();
    
    void updateStatus(RendererState<?,V> R);
    
}
