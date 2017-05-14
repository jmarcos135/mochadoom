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

/** Sprites are patches with a special naming convention
 * so they can be recognized by R_InitSprites.
 * The base name is NNNNFx or NNNNFxFx, with
 * x indicating the rotation, x = 0, 1-7.
 * The sprite and frame specified by a thing_t
 * is range checked at run time.
 * A sprite is a patch_t that is assumed to represent
 * a three dimensional object and may have multiple
 * rotations pre drawn.
 * Horizontal flipping is used to save space,
 * thus NNNNF2F5 defines a mirrored patch.
 * Some sprites will only have one picture used
 * for all views: NNNNF0
 */
public class spriteframe_t implements Cloneable{

 public spriteframe_t(){
     lump=new int[8];
     flip=new byte[8];
 }

 /** If false use 0 for any position.
  * Note: as eight entries are available,
  * we might as well insert the same name eight times. 
  * 
  * FIXME: this is used as a tri-state.
  * 0= false
  * 1= true
  * -1= cleared/indeterminate, which should not evaluate to either true or false.
  * */
 public int rotate;

 /** Lump to use for view angles 0-7. */
 public int[]    lump;

 /** Flip bit (1 = flip) to use for view angles 0-7. */
 public byte[]    flip;
 
 public spriteframe_t clone(){
     spriteframe_t response=new spriteframe_t();
     response.rotate=rotate;     
     System.arraycopy(this.lump, 0, response.lump, 0, lump.length);
     System.arraycopy(this.flip, 0, response.flip, 0, flip.length);
     return response;
     
 }
 

}
