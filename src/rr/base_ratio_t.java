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

public class base_ratio_t {

  public base_ratio_t(int base_width, int base_height, int psprite_offset,
            int multiplier, float gl_ratio) {
        this.base_width = base_width;
        this.base_height = base_height;
        this.psprite_offset = psprite_offset;
        this.multiplier = multiplier;
        this.gl_ratio = (float) (RMUL*gl_ratio);
    }

public int base_width;      // Base width (unused)
  public int base_height;     // Base height (used for wall visibility multiplier)
  public  int psprite_offset;  // Psprite offset (needed for "tallscreen" modes)
  public  int multiplier;      // Width or height multiplier
  public  float gl_ratio;
  
  public static final double RMUL =1.6d/1.333333d;
  
}