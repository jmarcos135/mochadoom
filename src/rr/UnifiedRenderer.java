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

import static rr.LightsAndColors.*;
import java.io.IOException;
import java.util.ArrayList;

import rr.drawfuns.ColVars;
import rr.drawfuns.R_DrawColumnBoom;
import rr.drawfuns.R_DrawColumnBoomLow;
import rr.drawfuns.R_DrawColumnBoomOpt;
import rr.drawfuns.R_DrawColumnBoomOptLow;
import rr.drawfuns.R_DrawFuzzColumn;
import rr.drawfuns.R_DrawFuzzColumnLow;
import rr.drawfuns.R_DrawSpan;
import rr.drawfuns.R_DrawSpanLow;
import rr.drawfuns.R_DrawTLColumn;
import rr.drawfuns.R_DrawTranslatedColumn;
import rr.drawfuns.R_DrawTranslatedColumnLow;
import rr.drawfuns.SpanVars;

import v.DoomVideoRenderer;
import doom.DoomMain;
import doom.DoomStatus;

public abstract class UnifiedRenderer<T, V>
        extends RendererState<T,V> {

    public UnifiedRenderer(DoomStatus<T,V> DS) {
        super(DS);
        this.MySegs=new Segs(this);
    }


    /** A very simple Seg (Wall) drawer, which just completes abstract SegDrawer
     *  by calling the final column functions.
     *  
     *  TODO: move out of RendererState.
     *  
     * @author velktron
     *
     */
    
    protected final class Segs
            extends SegDrawer {

        public Segs(Renderer<?, ?> R) {
            super(R);
        }

        /** For serial version, just complete the call */
        @Override
        protected final void CompleteColumn() {
            colfunc.main.invoke();
        }

    }

    ////////////////// The actual rendering calls ///////////////////////

   

    public static final class HiColor
            extends UnifiedRenderer<byte[],short[]> {

        public HiColor(DoomStatus<byte[],short[]> DM) {            
            super(DM);
            
            // Init any video-output dependant stuff            
            

            
            // Init light levels
            colormaps.scalelight = new short[LIGHTLEVELS][MAXLIGHTSCALE][];
            colormaps.scalelightfixed = new short[MAXLIGHTSCALE][];
            colormaps.zlight = new short[LIGHTLEVELS][MAXLIGHTZ][];
            
            completeInit();
        }

        /**
         * R_InitColormaps This is VERY different for hicolor.
         * 
         * @throws IOException
         */
        protected void InitColormaps()
                throws IOException {

            colormaps.colormaps = V.getColorMaps();
            System.out.println("COLORS15 Colormaps: " + colormaps.colormaps.length);

            // MAES: blurry effect is hardcoded to this colormap.
            // Pointless, since we don't use indexes. Instead, a half-brite
            // processing works just fine.
            BLURRY_MAP = null;// colormaps[0];
        }
        
        /** Initializes the various drawing functions. They are all "pegged" to the
         *  same dcvars/dsvars object. Any initializations of e.g. parallel renderers
         *  and their supporting subsystems should occur here. 
         */
        @Override
        protected void R_InitDrawingFunctions(){
            
            
            // Span functions. Common to all renderers unless overriden
            // or unused e.g. parallel renderers ignore them.
            DrawSpan=new R_DrawSpan.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
            DrawSpanLow=new R_DrawSpanLow.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
            
            
            // Translated columns are usually sprites-only.
            DrawTranslatedColumn=new R_DrawTranslatedColumn.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawTranslatedColumnLow=new R_DrawTranslatedColumnLow.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawTLColumn=new R_DrawTLColumn(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            
            // Fuzzy columns. These are also masked.
            DrawFuzzColumn=new R_DrawFuzzColumn.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawFuzzColumnLow=new R_DrawFuzzColumnLow.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            
            // Regular draw for solid columns/walls. Full optimizations.
            DrawColumn=new R_DrawColumnBoomOpt.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dcvars,screen,I);
            DrawColumnLow=new R_DrawColumnBoomOptLow.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dcvars,screen,I);
            
            // Non-optimized stuff for masked.
            DrawColumnMasked=new R_DrawColumnBoom.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawColumnMaskedLow=new R_DrawColumnBoomLow.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            
            // Player uses masked
            DrawColumnPlayer=DrawColumnMasked; // Player normally uses masked.
            
            // Skies use their own. This is done in order not to stomp parallel threads.
            
            DrawColumnSkies=new R_DrawColumnBoomOpt.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,skydcvars,screen,I);
            DrawColumnSkiesLow=new R_DrawColumnBoomOptLow.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,skydcvars,screen,I);
            
            super.R_InitDrawingFunctions();
        }

    }       
                       
            public static final class Indexed
            extends UnifiedRenderer<byte[],byte[]> {

        public Indexed(DoomStatus<byte[],byte[]> DM) {            
            super(DM);
            
            // Init light levels
            colormaps.scalelight = new byte[LIGHTLEVELS][MAXLIGHTSCALE][];
            colormaps.scalelightfixed = new byte[MAXLIGHTSCALE][];
            colormaps.zlight = new byte[LIGHTLEVELS][MAXLIGHTZ][];
            
            completeInit();
        }

        /**
         * R_InitColormaps
         * 
         * @throws IOException
         */
        protected void InitColormaps() throws IOException {
            int lump, length;

            // Load in the light tables,
            // 256 byte align tables.
            lump = W.GetNumForName("COLORMAP");
            length = W.LumpLength(lump) + 256;
            colormaps.colormaps = new byte[(length / 256)][256];
            System.out.println("Colormaps: " + colormaps.colormaps.length);

            byte[] tmp = new byte[length];
            W.ReadLump(lump,tmp);

            for (int i = 0; i < colormaps.colormaps.length; i++) {
                System.arraycopy(tmp, i * 256, colormaps.colormaps[i], 0, 256);
            }
            
            // MAES: blurry effect is hardcoded to this colormap.
            BLURRY_MAP=colormaps.colormaps[6];
            // colormaps = (byte *)( ((int)colormaps + 255)&~0xff);     

            
        }
        
        /** Initializes the various drawing functions. They are all "pegged" to the
         *  same dcvars/dsvars object. Any initializations of e.g. parallel renderers
         *  and their supporting subsystems should occur here. 
         */
        
        @Override
        protected void R_InitDrawingFunctions(){
            
            // Span functions. Common to all renderers unless overriden
            // or unused e.g. parallel renderers ignore them.
            DrawSpan=new R_DrawSpan.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
            DrawSpanLow=new R_DrawSpanLow.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
            
            
            // Translated columns are usually sprites-only.
            DrawTranslatedColumn=new R_DrawTranslatedColumn.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawTranslatedColumnLow=new R_DrawTranslatedColumnLow.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            //DrawTLColumn=new R_DrawTLColumn(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            
            // Fuzzy columns. These are also masked.
            DrawFuzzColumn=new R_DrawFuzzColumn.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I,BLURRY_MAP);
            DrawFuzzColumnLow=new R_DrawFuzzColumnLow.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I,BLURRY_MAP);
            
            // Regular draw for solid columns/walls. Full optimizations.
            DrawColumn=new R_DrawColumnBoomOpt.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dcvars,screen,I);
            DrawColumnLow=new R_DrawColumnBoomOptLow.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dcvars,screen,I);
            
            // Non-optimized stuff for masked.
            DrawColumnMasked=new R_DrawColumnBoom.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawColumnMaskedLow=new R_DrawColumnBoomLow.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            
            // Player uses masked
            DrawColumnPlayer=DrawColumnMasked; // Player normally uses masked.
            
            // Skies use their own. This is done in order not to stomp parallel threads.
            
            DrawColumnSkies=new R_DrawColumnBoomOpt.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,skydcvars,screen,I);
            DrawColumnSkiesLow=new R_DrawColumnBoomOptLow.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,skydcvars,screen,I);
            super.R_InitDrawingFunctions();
        }

    }       
          
            public static final class TrueColor
            extends UnifiedRenderer<byte[],int[]> {

        public TrueColor(DoomStatus<byte[],int[]> DM) {            
            super(DM);
            
            // Init light levels
            colormaps.scalelight = new int[LIGHTLEVELS][MAXLIGHTSCALE][];
            colormaps.scalelightfixed = new int[MAXLIGHTSCALE][];
            colormaps.zlight = new int[LIGHTLEVELS][MAXLIGHTZ][];
            
            completeInit();
        }

        /**
         * R_InitColormaps This is VERY different for hicolor.
         * 
         * @throws IOException
         */
        protected void InitColormaps()
                throws IOException {

            colormaps.colormaps = V.getColorMaps();
            System.out.println("COLORS32 Colormaps: " + colormaps.colormaps.length);
            BLURRY_MAP = null;

        }
        
        /** Initializes the various drawing functions. They are all "pegged" to the
         *  same dcvars/dsvars object. Any initializations of e.g. parallel renderers
         *  and their supporting subsystems should occur here. 
         */
        
        @Override
        protected void R_InitDrawingFunctions(){
            
            // Span functions. Common to all renderers unless overriden
            // or unused e.g. parallel renderers ignore them.
            DrawSpan=new R_DrawSpan.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
            DrawSpanLow=new R_DrawSpanLow.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
            
            
            // Translated columns are usually sprites-only.
            DrawTranslatedColumn=new R_DrawTranslatedColumn.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawTranslatedColumnLow=new R_DrawTranslatedColumnLow.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            //DrawTLColumn=new R_DrawTLColumn.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            
            // Fuzzy columns. These are also masked.
            DrawFuzzColumn=new R_DrawFuzzColumn.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawFuzzColumnLow=new R_DrawFuzzColumnLow.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            
            // Regular draw for solid columns/walls. Full optimizations.
            DrawColumn=new R_DrawColumnBoomOpt.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dcvars,screen,I);
            DrawColumnLow=new R_DrawColumnBoomOptLow.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dcvars,screen,I);
            
            // Non-optimized stuff for masked.
            DrawColumnMasked=new R_DrawColumnBoom.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawColumnMaskedLow=new R_DrawColumnBoomLow.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            
            // Player uses masked
            DrawColumnPlayer=DrawColumnMasked; // Player normally uses masked.
            
            // Skies use their own. This is done in order not to stomp parallel threads.
            
            DrawColumnSkies=new R_DrawColumnBoomOpt.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,skydcvars,screen,I);
            DrawColumnSkiesLow=new R_DrawColumnBoomOptLow.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,skydcvars,screen,I);
            super.R_InitDrawingFunctions();
        }

    }       

}
