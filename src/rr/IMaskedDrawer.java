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
import v.IVideoScaleAware;

/** Draws any masked stuff -sprites, textures, or special 3D floors */

public interface IMaskedDrawer<T,V>
        extends IVideoScaleAware,IDetailAware {

    public static final int BASEYCENTER = 100;
    
    /** Cache the sprite manager, if possible */

    void cacheSpriteManager(ISpriteManager SM);

    void DrawMasked();

    void setPspriteIscale(int i);

    void setPspriteScale(int i);

    /**
     * For serial masked drawer, just complete the column function. For
     * parallel version, store rendering instructions and execute later on.
     * HINT: you need to discern between masked and non-masked draws.
     */

    void completeColumn();
}
