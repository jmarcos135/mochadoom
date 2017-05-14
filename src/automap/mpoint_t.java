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

package automap;

import m.fixed_t;

public class mpoint_t
{
  public mpoint_t(fixed_t x, fixed_t y) {
        this.x = x.val;
        this.y = y.val;
    }

  public mpoint_t(int x, int y) {
      this.x = x;
      this.y = y;
  }

  public mpoint_t(double x, double y) {
      this.x = (int) x;
      this.y = (int) y;
  }
  
  public mpoint_t(){
      this.x=0;
      this.y=0;
  }
  
  /** fixed_t */
  public int x,y;
  
  public String toString(){
      return (Integer.toHexString(x)+" , "+Integer.toHexString(y));
  }
};
