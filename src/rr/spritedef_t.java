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

/**
 * A sprite definition:
 * a number of animation frames.
 */

public class spritedef_t {

    /** the very least, primitive fields won't bomb,
     *  and copy constructors can do their job.
     */
    public spritedef_t(){        
    }
    
    public spritedef_t(int numframes){
        this.numframes=numframes;
        this.spriteframes=new spriteframe_t[numframes];        
    }
    
    public spritedef_t(spriteframe_t[] frames){
        this.numframes=frames.length;
        this.spriteframes=new spriteframe_t[numframes];
        // copy shit over...
        for (int i=0;i<numframes;i++){
            spriteframes[i]=frames[i].clone();
        }
    }
    
    /** Use this constructor, as we usually need less than 30 frames 
     *  It will actually clone the frames.
     */
    
    public void copy(spriteframe_t[] from, int maxframes){
        this.numframes=maxframes;
        this.spriteframes=new spriteframe_t[maxframes];
        // copy shit over...
        for (int i=0;i<maxframes;i++){
            spriteframes[i]=from[i].clone();
        }
    }
    
    
 public int         numframes;
 public spriteframe_t[]  spriteframes;

};