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


/** A maptexturedef_t describes a rectangular texture,
 *  which is composed of one or more mappatch_t structures
 *  that arrange graphic patches.
 *  
 *  This is the in-memory format, which is similar to maptexture_t (which is on-disk).
 *  
 * @author Maes
 *
 */

public class texture_t {
    /** Keep name for switch changing, etc. */
    public String    name;        
    public short   width;
    public short   height;
    
    // All the patches[patchcount]
    //  are drawn back to front into the cached texture.
    public short   patchcount;
    public texpatch_t[]  patches;     
    
    /** Unmarshalling at its best! */
    
    public void copyFromMapTexture(maptexture_t mt){
        this.name=new String(mt.name);
        this.width=mt.width;
        this.height=mt.height;
        this.patchcount=mt.patchcount;
        this.patches=new texpatch_t[patchcount];
        
        for (int i=0;i<patchcount;i++){
            patches[i]=new texpatch_t();
            patches[i].copyFromMapPatch(mt.patches[i]);
        }
    }
    
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append(name);
        sb.append(" Height ");
        sb.append(height);
        sb.append(" Width ");
        sb.append(width);
        sb.append(" Patchcount ");
        sb.append(patchcount);
        return sb.toString();
            
        }    
}