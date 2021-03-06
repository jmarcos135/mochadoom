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
// along with Mocha Doom.  If not, see <http://www.gnu.org/licenses/>.

package v;

import static data.Defines.RANGECHECK;

import i.DoomStatusAware;
import i.IDoomSystem;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import doom.DoomStatus;

import m.BBox;

import rr.column_t;
import rr.patch_t;
import utils.C2JUtils;

public abstract class SoftwareVideoRenderer8 extends SoftwareVideoRenderer<byte[],byte[]> {

    public Image getCurrentScreen(){
    	return currentscreen;
    }

    public SoftwareVideoRenderer8(){
        super();
        screens=new byte[5][];
    }

    public SoftwareVideoRenderer8(int w,int h){
        super(w,h);
        screens=new byte[5][];
    }
   
  /** V_DrawPatch
   * Masks a column based masked pic to the screen. 
   *  desttop, dest and source were byte*
   */ 

  public final void DrawPatch
  ( int       x,
  int     y,
  int     scrn,
  patch_t patch ) 
  { 

   column_t   column; 
   int    desttop;
   final  byte[] dest=screens[scrn];
   int        w; 
       
   y -= patch.topoffset; 
   x -= patch.leftoffset; 
  if (RANGECHECK) 
   if (doRangeCheck(x,y,patch,scrn))
   {
     System.err.print("Patch at "+x+","+y+" exceeds LFB\n");
     // No I_Error abort - what is up with TNT.WAD?
     System.err.print("V_DrawPatch: bad patch (ignored)\n");
     return;
   }

   if (scrn==0)
      this.MarkRect (x, y, patch.width, patch.height); 

       
   w = patch.width; 
   desttop = x+this.width*y; 
   // For each column..
   int destPos;
   int ptr=0;
   for (int col=0 ; col<w ; desttop++, col++,x++)
   { 
      // This points at a "column" object.     
      //column = (column_t *)((byte *)patch + LONG(patch->columnofs[col])); 
      column=patch.columns[col];
      // For each post...
      for (int i=0;i<column.posts;i++){
          // Get pointer to post offset.
          ptr=column.postofs[i];
          // Get post delta
          short delta=column.postdeltas[i];
          // We skip delta, len and padding.
          // ptr+=3; NOT NEEDED< pre-skipped at parsing. 
          
          // Skip transparent rows...
          if (delta==0xFF) break;

          destPos = desttop + delta*this.width;  
          
          // These lengths are already correct.
          for (int j=0;j<column.postlen[i];j++){
                 dest[destPos] = column.data[ptr++];
                 destPos += this.width;
          }
      }
   }
      
      
  }

  /** V_DrawPatchSolidScaled
   * Draws a SOLID (non-masked) patch to the screen with integer scaling
   * m and n.
   * Useful for stuff such as help screens, titlepic and status bar. Not 
   * very useful for menus, though.
   * desttop, dest and source were byte
   */ 

  public final void DrawPatchSolidScaled
  ( int       x,
  int     y,int m, int n,
  int     scrn,
  patch_t patch ) 
  { 

   if ((m==1)&&(n==1)){
       DrawPatch(x,y,scrn,patch);
       return;
   }
      
   column_t   column; 
   int    desttop;
   final byte[] dest=screens[scrn];
   int        w; 
       
   y =y*n- patch.topoffset; 
   x =x*m- patch.leftoffset; 
  if (RANGECHECK) 
   if (doRangeCheck(x,y,patch,scrn))
   {
     System.err.print("Patch at "+x+","+y+" exceeds LFB\n");
     // No I_Error abort - what is up with TNT.WAD?
     System.err.print("V_DrawPatch: bad patch (ignored)\n");
     return;
   }

   if (scrn==0)
      this.MarkRect (x, y, patch.width, patch.height); 

       
   w = patch.width; 
   desttop = m*x+this.width*y; 
   // For each column..
   int destPos;
   int ptr=0;
   // x increases by m.
   
   // Some unrolling...
   
   if (m==2) {
       //desttop=2*desttop;
       for (int col=0 ; col<w ; desttop+=2, col++)

       { 

           // This points at a "column" object.     
           //column = (column_t *)((byte *)patch + LONG(patch->columnofs[col])); 
           column=patch.columns[col];
           // For each post...
           for (int i=0;i<column.posts;i++){
               // Get pointer to post offset.
               ptr=column.postofs[i];
               // Get post delta
               short delta=column.postdeltas[i];
               // We skip delta, len and padding.
               //ptr+=3; 

               // Skip transparent rows...
               if (delta==0xFF) break;

               destPos = desttop+ n*delta*this.width;  

               // These lengths are already correct.
               for (int j=0;j<column.postlen[i];j++){
                   dest[destPos] = column.data[ptr++];
                   dest[destPos+1] = dest[destPos];
                   destPos += n*this.width;
               }
           }
       }
   } else  if (m==3) {
      // desttop=3*desttop;
       for (int col=0 ; col<w ; desttop+=3, col++)

       { 

           // This points at a "column" object.     
           //column = (column_t *)((byte *)patch + LONG(patch->columnofs[col])); 
           column=patch.columns[col];
           // For each post...
           for (int i=0;i<column.posts;i++){
               // Get pointer to post offset.
               ptr=column.postofs[i];
               // Get post delta
               short delta=column.postdeltas[i];
               // We skip delta, len and padding.
               //ptr+=3; 

               // Skip transparent rows...
               if (delta==0xFF) break;

               destPos = desttop + n*delta*this.width;  

               // These lengths are already correct.
               for (int j=0;j<column.postlen[i];j++){
                   dest[destPos] = column.data[ptr++];
                   dest[destPos+1] = dest[destPos];
                   dest[destPos+2] = dest[destPos];
                   destPos += n*this.width;    
               }
               
           }
       }
   }else  if (m==4) {
           //desttop=4*desttop;
           for (int col=0 ; col<w ; desttop+=4, col++)

           { 

               // This points at a "column" object.     
               //column = (column_t *)((byte *)patch + LONG(patch->columnofs[col])); 
               column=patch.columns[col];
               // For each post...
               for (int i=0;i<column.posts;i++){
                   // Get pointer to post offset.
                   ptr=column.postofs[i];
                   // Get post delta
                   short delta=column.postdeltas[i];
                   // We skip delta, len and padding.
                   //ptr+=3; 

                   // Skip transparent rows...
                   if (delta==0xFF) break;

                   destPos = desttop + n*delta*this.width;  

                   // These lengths are already correct.
                   for (int j=0;j<column.postlen[i];j++){
                       dest[destPos] = column.data[ptr++];
                       dest[destPos+1] = dest[destPos];
                       dest[destPos+2] = dest[destPos];
                       dest[destPos+3] = dest[destPos];
                       destPos += n*this.width;
                   }
               }
           }
       }
           else  {
              // desttop=m*desttop;
               for (int col=0 ; col<w ; desttop+=m, col++)

               { 

                   // This points at a "column" object.     
                   //column = (column_t *)((byte *)patch + LONG(patch->columnofs[col])); 
                   column=patch.columns[col];
                   // For each post...
                   for (int i=0;i<column.posts;i++){
                       // Get pointer to post offset.
                       ptr=column.postofs[i];
                       // Get post delta
                       short delta=column.postdeltas[i];
                       // We skip delta, len and padding.
                       //ptr+=3; 

                       // Skip transparent rows...
                       if (delta==0xFF) break;

                       destPos = desttop + n*delta*this.width;  

                       // These lengths are already correct.
                       for (int j=0;j<column.postlen[i];j++){
                           for (int k=0;k<m;k++)
                           dest[destPos+k] = column.data[ptr];
                           ptr++;
                           destPos += n*this.width;
                       }
                   }
               }
       
       
   }
   
   
    scaleSolid(m,n, scrn,m*patch.width);  
  }
  

  /** Pretty crude in-place scaling. It's fast, but only works full-screen 
   * Width needs to be specific, height is implied.
   *  */
  
  protected final void scaleSolid(int m, int n, int screen,int width){
      int height=screens[screen].length/width;
      for (int i=0;i<height;i+=n){
           
          for (int j=0;j<n-1;j++){

              System.arraycopy(screens[screen], (i+j)*width, screens[screen],(i+j+1)*width,width);
          }
      }
      
  }

  public void DrawPatchSolidScaled ( int x, int y,  int scrn, IVideoScale vs, patch_t patch ){
      this.DrawPatchSolidScaled(x, y, vs.getScalingX(), vs.getScalingY(),scrn, patch);
  }
  
  /**
   * V_DrawPatchFlipped 
   * Masks a column based masked pic to the screen.
   * Flips horizontally, e.g. to mirror face.
   * 
   * Currently UNUSED, as any occurence to it is also scaled and
   * best served by another function.
   * 
   * 
   */

  public final void DrawPatchFlipped ( int      x,   int        y,    int       scrn,  patch_t  patch ) 
  { 

      column_t    column; 
      int desttop;
      final byte[]  dest=screens[scrn];
      int     w; 
       
      y -= patch.topoffset; 
      x -= patch.leftoffset;
      

      if (RANGECHECK) 
          if (doRangeCheck(x,y,patch,scrn))
          {
      {
          System.err.print("Patch origin "+x+","+y +" exceeds LFB\n" );
          // No I_Error abort - what is up with TNT.WAD?
          I.Error("Bad V_DrawPatch in V_DrawPatchFlipped");
          }
      }
   
      if (scrn==0)
          this.MarkRect (x, y, patch.width, patch.height); 

   // Set x and y coords inside dest array.
    
        
      w = patch.width; 
      desttop = y*this.width+x;     
   // For each column..
      for (int col=0 ; col<w ; desttop++,col++)
      {         

          // This points at a "column" object.     
         // Notice the flipping on this one.
         // column = (column_t *)((byte *)patch + LONG(patch->columnofs[w-1-col]));  
         column=patch.columns[w-1-col];
         
         // For each post...
         //System.out.println("Column"+(w-1-col));
         for (int i=0;i<column.posts;i++){
             // Get pointer to post offset.
             int ptr=column.postofs[i];
             // Get post delta
             int delta=0xFF&column.data[ptr];
             // We skip delta, len and padding.
             //ptr+=3;
             if (delta==0xFF) break;
             int destPos = desttop+delta*this.width;
             //count = column.length; 

             // These lengths are already correct.
             for (int j=0;j<column.postlen[i];j++,   destPos += this.width){
                    dest[destPos] = column.data[ptr+j];
                    // next line
                  
             }
         }
      }
      
  } 
   
/**
 * V_DrawScaledPatch
 * like V_DrawPatch, but scaled 2,3,4 times the original size and position
 * this is used for menu and title screens, with high resolutions
 * 
 *added:05-02-98:
 *default params : scale patch and scale start
 *
 * Iniially implemented for Mocha Doom by _D_ (shamelessly ripped from 
 * Eternity Engine ;-), adapted to scale based on a scaling info object (VSI).
 * 
 * Unless overriden by flags, starting x and y are automatically
 * scaled (implied V_SCALESTART)
 *
 */
@Override
public void DrawScaledPatch(int x, int y, int scrn, IVideoScale VSI, patch_t patch)
{
    int col;
    column_t column;
    int desttop;
    final byte[] dest = screens[scrn&0xFF];
    //byte[] source;

    int dupx, dupy;
    int colfrac, rowfrac;

   // System.out.printf("V_DrawScaledPatch %d %d \n",x,y);
    // draw an hardware converted patch
    /*#ifdef HWRENDER
  if (rendermode != render_soft)
  {
      HWR_DrawPatch((GlidePatch_t *) patch, x, y, scrn);
      return;
  }
#endif*/

    // A very common operation, eliminates the need to pre-divide.
    if (C2JUtils.flags(scrn,V_PREDIVIDE)){
        x/=vs.getScalingX();
        y/=vs.getScalingY();
    }
    
    if (C2JUtils.flags(scrn, V_NOSCALEPATCH))
        dupx = dupy = 1;
    else
    {
        dupx = VSI.getScalingX();
        dupy = VSI.getScalingY();
    }

    // Eliminates.
    // MAES: added this fix so that non-zero patch offsets can be 
    // taken into account, regardless of whether we use pre-scaled
    // coords or not. Only Doomguy's face needs this hack for now.
    
    if (C2JUtils.flags(scrn, V_SCALEOFFSET)) {
    y -= patch.topoffset*dupx;
    x -= patch.leftoffset*dupy;
    } else {
        y -= patch.topoffset;
        x -= patch.leftoffset;
    }
    

    colfrac = dupx;
    rowfrac = dupy;

    //desttop = screens[scrn & 0xFF];
    if (C2JUtils.flags(scrn, V_NOSCALESTART))
        desttop = (y * this.width) + x;
    else
        desttop = (y * dupy * this.width) + (x * dupx) /*+ scaledofs*/;
    //destend = desttop + /*SHORT(*/patch.width/*)*/ * dupx;

    int w = patch.width*dupx;

    int colInc = 1;
    col = 0;
    if (C2JUtils.flags(scrn, V_FLIPPEDPATCH))
    {
        colInc = -1;
        col = w-1;//(/*SHORT(*/patch.width/*)*/ << fixed_t.FRACBITS) + colfrac;
    }
    
    for (; col >= 0 && col<w/*; desttop < destend*/; col += colInc, desttop++)
    {
        //column = (column_t *) ((byte *) patch + LONG(patch.columnofs[col >> FRACBITS]));
        column=patch.columns[col/colfrac];

        int destPos;
        int ptr = 0;
        int ptrOfs;

        //while (column.topdelta != 0xff)
        for (int i=0;i<column.posts;i++){
            {
                ptrOfs = column.postofs[i];//+3;
                ptr = 0;
                short delta = column.postdeltas[i];

                // Skip transparent rows...
                if (delta==0xFF) break;

                destPos = desttop + delta*dupy*this.width;  
                //dest = desttop + column.topdelta * dupy * this.width;

                //ofs = 0;
                //while (count-- > 0)
                for (int j=0;j<column.postlen[i]*dupy;j++)
                {
                    dest[destPos] = column.data[ptrOfs+ptr/rowfrac];
                    destPos += this.width;

                    ptr++;
                    //ofs += rowfrac;
                }

                //column = (column_t *) ((byte *) column + column.length + 4);
            }
        }
    }
}

  @Override
  public final void
  DrawBlock
  ( int       x,
    int       y,
    int       scrn,
    int       width,
    int       height,
    byte[]        src ) 
  { 
      // This is "screens[scrn]"
	  final  byte[]  dest=screens[scrn];  
       
  if (doRangeCheck(x, y, scrn))
      {
      I.Error("Bad V_DrawBlock");
      }

   
      this.MarkRect(x, y, width, height); 
   
      int destPos = /*screens[scrn] +*/ y*this.width +x;
      // MAES: making an assumption here. A BIIIIG one.
      int srcPos=0;
      while ((height--)>0) 
      { 
          //  memcpy (dest, src, width);
      System.arraycopy(src, srcPos, dest, destPos, width);
      srcPos += width; 
      destPos += this.width; 
      } 
  }
  
  @Override
  public final void DrawBlock(int x, int y, int scrn, int width, int height,
          byte[] src,int offset) {
      // This is "screens[scrn]"
      final byte[] dest = screens[scrn];
      
      if (doRangeCheck(x, y, scrn)) {
          I.Error("Bad V_DrawBlock");
      }

      this.MarkRect(x, y, width, height);

      int destPos = /* screens[scrn] + */y * this.width + x;
      // MAES: making an assumption here. A BIIIIG one.
      int srcPos = offset;
      while ((height--) > 0) {
              System.arraycopy(src, srcPos, dest, destPos, width);
          srcPos += width;
          destPos += this.width;
      }
  }

   

  /**
   * V_GetBlock
   * Gets a linear block of pixels from the view buffer.
   */


  public final void
  GetBlock
  ( int       x,
    int       y,
    int       scrn,
    int       width,
    int       height,
    byte[]        dest ) 
  { 
	  final byte[]  src=screens[scrn]; 
       
  if (RANGECHECK){
      if (doRangeCheck(x,y,scrn)){    
      I.Error ("Bad V_DrawBlock");
      }
      
   }
      int srcPos = y*this.width+x;
      int destPos=0;

      while ((height--)>0) 
      { 
      System.arraycopy(src, srcPos, dest, destPos, width);
      //memcpy (dest, src, width); 
      srcPos += width; 
      destPos += this.width; 
      } 
  }
  
  
  /** Replaces DrawPatchCol for bunny scrolled in Finale.
   * 
   * 
   */
  
  @Override
  public final void
  DrawPatchColScaled
  ( int       x, patch_t  patch,int col, 
          IVideoScale vs, int       screen )
  {
      column_t   column;
      int   source;
      final byte[]   dest;
      int   desttop;
      final int scale=vs.getScalingX();
      
      column = patch.columns[col];
      desttop = x*scale; // Scale X position.
      dest=screens[screen];
      // step through the posts in a column
      
      
      for (int i=0;i<column.posts;i++){
          // Get pointer to post offset.
          source=column.postofs[i];
          // Get post delta
          short delta=column.postdeltas[i];
          // We skip delta, len and padding.
          //source+=3;  NOT NEEDED ANYMORE, skipped by parsing.
          
          // Skip transparent rows...
          if (delta==0xFF) break;

            
          
          // Replicate each column scale times vertically,
          // with spaced pixels.
          final int startsource=source;
          for (int kl=0;kl<scale;kl++){
              int destPos = desttop + (delta+kl)*SCREENWIDTH;
          for (int j=0;j<column.postlen[i];j++){
              final byte data=column.data[source++];
              // replicate each column's pixel horizontally and vertically.
                  for (int k=0;k<scale;k++)
                  dest[destPos+k] =data; 
                 destPos += scale*SCREENWIDTH;
              }
          source=startsource;
          }
      }
  }

  public void setScreen(int index, int width, int height){
      this.screens[index]=new byte[width*height];
  }
  
  public final void takeScreenShot(int screen, String imagefile, IndexColorModel icm) throws IOException {
      
      BufferedImage b=new BufferedImage(this.getWidth(),this.getHeight(), BufferedImage.TYPE_BYTE_INDEXED, icm);
      
      
      int[] tmp=new int[this.screens[screen].length];
      for (int i=0;i<this.screens[screen].length;i++){
          tmp[i]=this.screens[screen][i];
      }
      
      b.getRaster().setPixels(0, 0, this.getWidth(),this.getHeight(), tmp);
      
      File outputFile =
          new File(
              imagefile+".png");
      ImageIO.write(b, "PNG", outputFile);
      
  }
  
  /*
  public final boolean isRasterNull(int screen){
      for (int i=0;i<screens[screen].length;i++){
          if (screens[screen][i]!=0) return false;
      }
      return true;
  } */
  
  public void setCurrentScreen(int screen){
	  this.usescreen=screen;
  }
  
  public void update(){
	// Override only if there's something else to be done, e.g. map palette to truecolor buffer  
  }
  
  public void report(BufferedImage[] b){
	    System.out.println("Main video buffer "+screens[0]);
	    for (int i=0;i<b.length;i++){
	    System.out.println(((Object)b[i].getRaster()).toString()+" "+b[i].getRaster().hashCode()+" "+((DataBufferByte)(b[i].getRaster().getDataBuffer())).getData());
	    }
	}
  
/** Internal method for setting up palettes (and gamma tables)
 * 
 */

public void createPalettes(byte[] paldata, short[][] gammadata, final int palettes, final int colors, final int stride,final int gammalevels){
	
	// Sanity check on supplied data length. If there is not enough data to create the specified palettes,
	// their number will be limited.
	
	if (paldata!=null) 	// As many as are likely contained
		maxpalettes=paldata.length/(colors*stride);
	else
		maxpalettes=0; // Do some default action on null palette.

	if (gammadata!=null) 	// As many as are likely contained
		maxgammas=gammadata.length;
	else
		maxgammas=0; // Do some default action on null gamma tables.
	
	if (maxgammas==0){
		gammadata=GammaTables.gammatables;
		maxgammas=GammaTables.gammatables.length;
	}
	

	// Enough data for all palettes. 
	// Enough data for all palettes. 
	if (maxpalettes>0 && maxgammas>0)
			specificPaletteCreation(paldata,gammadata,palettes,colors,stride,gammalevels);
		 else 
			 paletteRecovery();
    	  
      }
    
/** Override this in extending classes to perform specific actions depending on the
 *  type of renderer. It's better not to assign a default action, nor make assumptions
 *  on the underlying types of actual palettes
 * 
 * @param paldata
 * @param gammadata
 * @param palettes
 * @param colors
 * @param stride
 * @param gammalevels
 */

protected abstract void specificPaletteCreation(byte[] paldata,
		short[][] gammadata, 
		final int palettes, 
		final int colors,
		final int stride,
		final int gammalevels);

protected int lastcolor=-1;
protected byte[] scanline;

/**
 * Clear automap frame buffer or fi
 * MAES: optimized for efficiency, seen the lack of a proper "memset" in Java.
 * 
 */

@Override
public final void FillRect(int color,int screen, int x,int y,int width, int height) {
    
    if  (RANGECHECK) {
        if (x<0
                ||x+width >this.width
                || y<0
                || y+height>SCREENHEIGHT 
                || screen>4)
        {
            I.Error ("Bad V_FillRect");
        }
    }
    
    byte[] arr=screens[screen];
    
    // Do a "per scanline" copy. 
    int fromIndex=x+y*SCREENWIDTH;
    int toIndex=x+(y+height-1)*SCREENWIDTH;
    
    // First scanline.
    for (int i=0;i<width;i++)
    	arr[fromIndex+i]=(byte) color;
    
    for (;fromIndex<toIndex;fromIndex+=SCREENWIDTH){
    		System.arraycopy(arr,fromIndex,arr,fromIndex+SCREENWIDTH,width);
        }
}

/**
 * V_Fillrect
 */
@Override
public void FillRect(int srcx, int srcy, int width, int height, int destscrn) {
    // These are pointers inside an array.
    final byte[] dest = screens[destscrn];

    if (RANGECHECK) {
        if (srcx < 0 || srcx + width > this.width || srcy < 0
                || srcy + height > SCREENHEIGHT || destscrn > 4) {
            I.Error("Bad V_FillRect");
        }
    }
    this.MarkRect(srcx, srcy, width, height);

    // MAES: these were pointers to a specific position inside the screen.
    int srcPos = this.width * srcy + srcx;

    for (; height > 0; height--) {
        for (int i = 0; i < width; i++) {
            dest[srcPos + i] = 0;
        }
        // memcpy (dest, src, width);
        srcPos += this.width;
    }

}

/** Should return colormaps, if you ever move their management in here.
 * 
 */

public final byte[][] getColorMaps(){
    return null;
}


}
