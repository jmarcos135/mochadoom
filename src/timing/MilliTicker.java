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

package timing;

import static data.Defines.TICRATE;

public class MilliTicker
        implements ITicker {

    /**
     * I_GetTime
     * returns time in 1/70th second tics
     */
   
    @Override    public int GetTime ()
    {
        long    tp;
        //struct timezone   tzp;
        int         newtics;
        
        tp=System.currentTimeMillis();
        if (basetime==0)
        basetime = tp;
        newtics = (int) (((tp-basetime)*TICRATE)/1000);
        return newtics;
    }
    
    protected volatile long basetime=0;
    protected volatile int oldtics=0;
    protected volatile int discrepancies;
    
}
