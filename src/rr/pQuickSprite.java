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
public class pQuickSprite{

    public static final void sort(vissprite_t[] c){
        int i,j,left = 0,right = c.length - 1,stack_pointer = -1;
        int[] stack = new int[128];
        vissprite_t swap,temp;
        while(true){
            if(right - left <= 7){
                for(j=left+1;j<=right;j++){
                    swap = c[j];
                    i = j-1;
                    while(i>=left && c[i].scale>swap.scale)
                        c[i+1] = c[i--];
                    c[i+1] = swap;
                }
                if(stack_pointer == -1)
                    break;
                right = stack[stack_pointer--];
                left = stack[stack_pointer--];
            }else{
                int median = (left + right) >> 1;
                i = left + 1;
                j = right;
                swap = c[median]; c[median] = c[i]; c[i] = swap;
                /* make sure: c[left] <= c[left+1] <= c[right] */
                if(c[left].scale > c[right].scale){
                    swap = c[left]; c[left] = c[right]; c[right] = swap;
                }if(c[i].scale>c[right].scale ){
                    swap = c[i]; c[i] = c[right]; c[right] = swap;
                }if(c[left].scale>c[i].scale){
                    swap = c[left]; c[left] = c[i]; c[i] = swap;
                }
                temp = c[i];
                while(true){
                    do i++; while(c[i].scale<temp.scale);
                    do j--; while(c[j].scale>temp.scale);
                    if(j < i)
                        break;
                    swap = c[i]; c[i] = c[j]; c[j] = swap;
                }
                c[left + 1] = c[j];
                c[j] = temp;
                if(right-i+1 >= j-left){
                    stack[++stack_pointer] = i;
                    stack[++stack_pointer] = right;
                    right = j-1;
                }else{
                    stack[++stack_pointer] = left;
                    stack[++stack_pointer] = j-1;
                    left = i;
                }
            }
        }
    }
}
    