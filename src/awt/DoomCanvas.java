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

package awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class DoomCanvas extends Canvas {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public void paint(Graphics g){
        
        g.setColor(new Color(.3f, .4f, .5f, .6f));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
