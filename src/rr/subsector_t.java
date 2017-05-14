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

import p.Resettable;

/**
 * 
 * A SubSector. References a Sector. Basically, this is a list of LineSegs,
 * indicating the visible walls that define (all or some) sides of a convex BSP
 * leaf.
 * 
 * @author admin
 */
public class subsector_t implements Resettable{

	public subsector_t() {
		this(null,  0,  0);
	} 

	public subsector_t(sector_t sector, int numlines, int firstline) {
		this.sector = sector;
		this.numlines = numlines;
		this.firstline = firstline;
	}

	// Maes: single pointer
	public sector_t sector;
	// e6y: support for extended nodes
	// 'int' instead of 'short'
	public int numlines;
	public int firstline;
	
	public String toString(){
		sb.setLength(0);
		sb.append("Subsector");
		sb.append('\t');
		sb.append("Sector: ");
		sb.append(sector);
		sb.append('\t');
		sb.append("numlines ");
		sb.append(numlines);
		sb.append('\t');
		sb.append("firstline ");
		sb.append(firstline);
		return sb.toString();
		
		
	}
	
	private static StringBuilder sb=new StringBuilder();

    @Override
    public void reset() {
        sector=null;
        firstline=numlines=0;        
    }
	

}
