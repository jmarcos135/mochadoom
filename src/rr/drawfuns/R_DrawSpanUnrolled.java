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

package rr.drawfuns;

import i.IDoomSystem;

/**
 * Drawspan loop unrolled by 4. However it has low rendering quality and bad
 * distortion. However it does actually does give a small speed boost (120
 * -> 130 fps with a Mul of 3.0)
 * 
 */

public abstract class R_DrawSpanUnrolled<T,V> extends DoomSpanFunction<T,V> {


    public R_DrawSpanUnrolled(int sCREENWIDTH, int sCREENHEIGHT, int[] ylookup,
            int[] columnofs, SpanVars<T, V> dsvars, V screen, IDoomSystem I) {
        super(sCREENWIDTH, sCREENHEIGHT, ylookup, columnofs, dsvars, screen, I);
        // TODO Auto-generated constructor stub
    }

   public static final class HiColor extends R_DrawSpanUnrolled<byte[],short[]>{
       
       
       public HiColor(int sCREENWIDTH, int sCREENHEIGHT, int[] ylookup,
            int[] columnofs, SpanVars<byte[], short[]> dsvars, short[] screen,
            IDoomSystem I) {
        super(sCREENWIDTH, sCREENHEIGHT, ylookup, columnofs, dsvars, screen, I);
        // TODO Auto-generated constructor stub
    }

    public void invoke() {
           int position, step;
           final byte[] source;
           final short[] colormap;
           int dest;
           int count;
           int spot;
           int xtemp;
           int ytemp;

           position = ((dsvars.ds_xfrac << 10) & 0xffff0000)
                   | ((dsvars.ds_yfrac >> 6) & 0xffff);
           step = ((dsvars.ds_xstep << 10) & 0xffff0000) | ((dsvars.ds_ystep >> 6) & 0xffff);
           source = dsvars.ds_source;
           colormap = dsvars.ds_colormap;
           dest = ylookup[dsvars.ds_y] + columnofs[dsvars.ds_x1];
           count = dsvars.ds_x2 - dsvars.ds_x1 + 1;
           //int rolls = 0;
           while (count >= 4) {
               ytemp = position >> 4;
               ytemp = ytemp & 0xfc0;
               xtemp = position >>> 26;
               spot = xtemp | ytemp;
               position += step;
               screen[dest] = colormap[0x00FF & source[spot]];
               ytemp = position >> 4;
               ytemp = ytemp & 0xfc0;
               xtemp = position >>> 26;
               spot = xtemp | ytemp;
               position += step;
               screen[dest + 1] = colormap[0x00FF & source[spot]];
               ytemp = position >> 4;
               ytemp = ytemp & 0xfc0;
               xtemp = position >>> 26;
               spot = xtemp | ytemp;
               position += step;
               screen[dest + 2] = colormap[0x00FF & source[spot]];
               ytemp = position >> 4;
               ytemp = ytemp & 0xfc0;
               xtemp = position >>> 26;
               spot = xtemp | ytemp;
               position += step;
               screen[dest + 3] = colormap[0x00FF & source[spot]];
               count -= 4;
               dest += 4;

               // Half-assed attempt to fix precision by forced periodic
               // realignment.

               /*
                * if ((rolls++)%64==0){ position =
                * ((((rolls*4)*ds_xstep+ds_xfrac) << 10) & 0xffff0000) |
                * ((((rolls*4)*ds_ystep+ds_yfrac) >> 6) & 0xffff); }
                */

           }

           while (count > 0) {
               ytemp = position >> 4;
               ytemp = ytemp & 4032;
               xtemp = position >>> 26;
               spot = xtemp | ytemp;
               position += step;
               screen[dest++] = colormap[0x00FF & source[spot]];
               count--;
           }
       }
       
   }

   
public static final class Indexed extends R_DrawSpanUnrolled<byte[],byte[]>{
       
       
       public Indexed(int sCREENWIDTH, int sCREENHEIGHT, int[] ylookup,
            int[] columnofs, SpanVars<byte[], byte[]> dsvars, byte[] screen,
            IDoomSystem I) {
        super(sCREENWIDTH, sCREENHEIGHT, ylookup, columnofs, dsvars, screen, I);
        // TODO Auto-generated constructor stub
    }

    public void invoke() {
           int position, step;
           final byte[] source;
           final byte[] colormap;
           int dest;
           int count;
           int spot;
           int xtemp;
           int ytemp;

           position = ((dsvars.ds_xfrac << 10) & 0xffff0000)
                   | ((dsvars.ds_yfrac >> 6) & 0xffff);
           step = ((dsvars.ds_xstep << 10) & 0xffff0000) | ((dsvars.ds_ystep >> 6) & 0xffff);
           source = dsvars.ds_source;
           colormap = dsvars.ds_colormap;
           dest = ylookup[dsvars.ds_y] + columnofs[dsvars.ds_x1];
           count = dsvars.ds_x2 - dsvars.ds_x1 + 1;
           //int rolls = 0;
           while (count >= 4) {
               ytemp = position >> 4;
               ytemp = ytemp & 0xfc0;
               xtemp = position >>> 26;
               spot = xtemp | ytemp;
               position += step;
               screen[dest] = colormap[0x00FF & source[spot]];
               ytemp = position >> 4;
               ytemp = ytemp & 0xfc0;
               xtemp = position >>> 26;
               spot = xtemp | ytemp;
               position += step;
               screen[dest + 1] = colormap[0x00FF & source[spot]];
               ytemp = position >> 4;
               ytemp = ytemp & 0xfc0;
               xtemp = position >>> 26;
               spot = xtemp | ytemp;
               position += step;
               screen[dest + 2] = colormap[0x00FF & source[spot]];
               ytemp = position >> 4;
               ytemp = ytemp & 0xfc0;
               xtemp = position >>> 26;
               spot = xtemp | ytemp;
               position += step;
               screen[dest + 3] = colormap[0x00FF & source[spot]];
               count -= 4;
               dest += 4;

               // Half-assed attempt to fix precision by forced periodic
               // realignment.

               /*
                * if ((rolls++)%64==0){ position =
                * ((((rolls*4)*ds_xstep+ds_xfrac) << 10) & 0xffff0000) |
                * ((((rolls*4)*ds_ystep+ds_yfrac) >> 6) & 0xffff); }
                */

           }

           while (count > 0) {
               ytemp = position >> 4;
               ytemp = ytemp & 4032;
               xtemp = position >>> 26;
               spot = xtemp | ytemp;
               position += step;
               screen[dest++] = colormap[0x00FF & source[spot]];
               count--;
           }
       }
       
   }

public static final class TrueColor extends R_DrawSpanUnrolled<byte[],int[]>{
    
    
    public TrueColor(int sCREENWIDTH, int sCREENHEIGHT, int[] ylookup,
         int[] columnofs, SpanVars<byte[], int[]> dsvars, int[] screen,
         IDoomSystem I) {
     super(sCREENWIDTH, sCREENHEIGHT, ylookup, columnofs, dsvars, screen, I);
 }

 public void invoke() {
        int position, step;
        final byte[] source;
        final int[] colormap;
        int dest;
        int count;
        int spot;
        int xtemp;
        int ytemp;

        position = ((dsvars.ds_xfrac << 10) & 0xffff0000)
                | ((dsvars.ds_yfrac >> 6) & 0xffff);
        step = ((dsvars.ds_xstep << 10) & 0xffff0000) | ((dsvars.ds_ystep >> 6) & 0xffff);
        source = dsvars.ds_source;
        colormap = dsvars.ds_colormap;
        dest = ylookup[dsvars.ds_y] + columnofs[dsvars.ds_x1];
        count = dsvars.ds_x2 - dsvars.ds_x1 + 1;
        //int rolls = 0;
        while (count >= 4) {
            ytemp = position >> 4;
            ytemp = ytemp & 0xfc0;
            xtemp = position >>> 26;
            spot = xtemp | ytemp;
            position += step;
            screen[dest] = colormap[0x00FF & source[spot]];
            ytemp = position >> 4;
            ytemp = ytemp & 0xfc0;
            xtemp = position >>> 26;
            spot = xtemp | ytemp;
            position += step;
            screen[dest + 1] = colormap[0x00FF & source[spot]];
            ytemp = position >> 4;
            ytemp = ytemp & 0xfc0;
            xtemp = position >>> 26;
            spot = xtemp | ytemp;
            position += step;
            screen[dest + 2] = colormap[0x00FF & source[spot]];
            ytemp = position >> 4;
            ytemp = ytemp & 0xfc0;
            xtemp = position >>> 26;
            spot = xtemp | ytemp;
            position += step;
            screen[dest + 3] = colormap[0x00FF & source[spot]];
            count -= 4;
            dest += 4;

            // Half-assed attempt to fix precision by forced periodic
            // realignment.

            /*
             * if ((rolls++)%64==0){ position =
             * ((((rolls*4)*ds_xstep+ds_xfrac) << 10) & 0xffff0000) |
             * ((((rolls*4)*ds_ystep+ds_yfrac) >> 6) & 0xffff); }
             */

        }

        while (count > 0) {
            ytemp = position >> 4;
            ytemp = ytemp & 4032;
            xtemp = position >>> 26;
            spot = xtemp | ytemp;
            position += step;
            screen[dest++] = colormap[0x00FF & source[spot]];
            count--;
        }
    }
    
}
}
