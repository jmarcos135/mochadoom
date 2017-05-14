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

package p;

import static m.fixed_t.*;
import rr.line_t;
import static utils.C2JUtils.eval;
//
// P_MAPUTL
//

public class divline_t {

    /** fixed_t */
     public int x, y, dx, dy;
     

     /**
      *P_PointOnDivlineSide
      *Returns 0 or 1. (false or true)
      *@param x fixed
      *@param y fixed
      *@param divline_t
      */
     public boolean
     PointOnDivlineSide
     ( int   x,
     int   y
     )
     {
    	 
    	 
    	 // Using Killough's version.
    	  return
    	    (dx==0) ? x <= this.x ? dy > 0 : dy < 0 :
    	    (dy==0) ? y <= this.y ? dx < 0 : dx > 0 :
    	    (dy^dx^(x -= this.x)^(y -= this.y)) < 0 ? (dy^x) < 0 :
    	    FixedMul(y>>8, this.dx>>8) >= FixedMul(this.dy>>8, x>>8);
    	    /*
    	    int PUREFUNC P_PointOnDivlineSide(fixed_t x, fixed_t y, const divline_t *line)
    	    {
    	      return
    	        !line->dx ? x <= line->x ? line->dy > 0 : line->dy < 0 :
    	        !line->dy ? y <= line->y ? line->dx < 0 : line->dx > 0 :
    	        (line->dy^line->dx^(x -= line->x)^(y -= line->y)) < 0 ? (line->dy^x) < 0 :
    	        FixedMul(y>>8, line->dx>>8) >= FixedMul(line->dy>>8, x>>8);
    	    }*/
    	    
    /*
      int dx;
      int dy;
      int left;
      int right;
      
      if (this.dx==0)
      {
      if (x <= this.x)
          return this.dy > 0;
      
      return this.dy < 0;
      }
      if (this.dy==0)
      {
      if (y <= this.y)
          return this.dx < 0;

      return this.dx > 0;
      }
      
      dx = (x - this.x);
      dy = (y - this.y);
      
      // try to quickly decide by looking at sign bits
      if ( ((this.dy ^ this.dx ^ dx ^ dy)&0x80000000) !=0)
      {
      if (((this.dy ^ dx) & 0x80000000) !=0)
          return true;       // (left is negative)
      return false;
      }
      
      left = FixedMul ( this.dy>>8, dx>>8 );
      right = FixedMul ( dy>>8 , this.dx>>8 );
      
      if (right < left)
      return false;       // front side
      return true;           // back side
      */
     }



     //
     //P_MakeDivline
     //
     public void
     MakeDivline
     ( line_t   li)
     {
      this.x = li.v1x;
      this.y = li.v1y;
      this.dx = li.dx;
      this.dy = li.dy;
     }

     public divline_t(line_t   li)
     {
      this.x = li.v1x;
      this.y = li.v1y;
      this.dx = li.dx;
      this.dy = li.dy;
     }

     public divline_t() {
		// TODO Auto-generated constructor stub
	}



	/**
 	  * P_DivlineSide
 	  * Returns side 0 (front), 1 (back), or 2 (on).
 	 */
 	public int
 	DivlineSide
 	( int	x,
 	  int	y)
 	{
 	    
 	   int left,right;
 	    // Boom-style code. Da fack.
 	   // [Maes:] it is MUCH more corrent than the linuxdoom one, for whatever reason.
 	    
 	   return
 	  (this.dx==0) ? x == this.x ? 2 : x <= this.x ? eval(this.dy > 0) : eval(this.dy < 0) :
 	  (this.dy==0) ? (olddemo ? x : y) == this.y ? 2 : y <= this.y ? eval(this.dx < 0) : eval(this.dx > 0) :
 	  (this.dy==0) ? y == this.y ? 2 : y <= this.y ? eval(this.dx < 0) : eval(this.dx > 0) :
 	  (right = ((y - this.y) >> FRACBITS) * (this.dx >> FRACBITS)) <
 	  (left  = ((x - this.x) >> FRACBITS) * (this.dy >> FRACBITS)) ? 0 :
 	  right == left ? 2 : 1;
 	  
 	  /*  
 	    
 	    int	left,right,dx,dy;

 	    if (this.dx==0)
 	    {
 	    if (x==this.x)
 	        return 2;
 	    
 	    if (x <= this.x)
 	        return eval(this.dy > 0);

 	    return eval(this.y < 0);
 	    }
 	    
 	    if (this.dy==0)
 	    {
 	    if (x==this.y)
 	        return 2;

 	    if (y <= this.y)
 	        return eval(this.dx < 0);

 	    return eval(this.dx > 0);
 	    }
 	    
 	    dx = (x - this.x);
 	    dy = (y - this.y);

 	    left =  (this.dy>>FRACBITS) * (dx>>FRACBITS);
 	    right = (dy>>FRACBITS) * (this.dx>>FRACBITS);
 	    
 	    if (right < left)
 	    return 0;   // front side
 	    
 	    if (left == right)
 	    return 2;
 	    return 1;       // back side
 	    */
 	}
 	
 	private static final boolean olddemo = true;
     
     
 }
