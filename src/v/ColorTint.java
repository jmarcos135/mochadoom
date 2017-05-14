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

public class ColorTint {
    public ColorTint(int r, int g, int b, float tint) {
        super();
        this.r = r;
        this.g = g;
        this.b = b;
        this.tint = tint;
    }

    public int r, g, b;

    public float tint;

    public static final ColorTint[] tints = { new ColorTint(0, 0, 0, .0f), // 0
                                                                           // Normal
            new ColorTint(255, 2, 3, 0.11f), // 1 Unused. 11% red tint of
                                             // RGB(252, 2, 3).
            new ColorTint(255, 0, 0, 0.22f), // 2
            new ColorTint(255, 0, 0, 0.33f), // 3
            new ColorTint(255, 0, 0, 0.44f), // 4
            new ColorTint(255, 0, 0, 0.55f), // 5
            new ColorTint(255, 0, 0, 0.66f), // 6
            new ColorTint(255, 0, 0, 0.77f), // 7
            new ColorTint(255, 0, 0, 0.88f), // 8
            new ColorTint(215, 185, 68, 0.12f), // 9
            new ColorTint(215, 185, 68, 0.25f), // 10
            new ColorTint(215, 185, 68, 0.375f), // 11
            new ColorTint(215, 185, 68, 0.50f), // 12
            new ColorTint(3, 253, 3, 0.125f) // 13

        };
}