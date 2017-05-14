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

package rr.parallel;

import static rr.LightsAndColors.LIGHTLEVELS;
import static rr.LightsAndColors.MAXLIGHTSCALE;
import static rr.LightsAndColors.MAXLIGHTZ;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import rr.IDetailAware;
import rr.PlaneDrawer;
import rr.SimpleThings;
import rr.patch_t;
import rr.drawfuns.ColVars;
import rr.drawfuns.DcFlags;
import rr.drawfuns.R_DrawColumnBoom;
import rr.drawfuns.R_DrawColumnBoomLow;
import rr.drawfuns.R_DrawColumnBoomOpt;
import rr.drawfuns.R_DrawColumnBoomOptLow;
import rr.drawfuns.R_DrawFuzzColumn;
import rr.drawfuns.R_DrawFuzzColumnLow;
import rr.drawfuns.R_DrawSpanLow;
import rr.drawfuns.R_DrawSpanUnrolled;
import rr.drawfuns.R_DrawTLColumn;
import rr.drawfuns.R_DrawTranslatedColumn;
import rr.drawfuns.R_DrawTranslatedColumnLow;
import utils.C2JUtils;
import v.DoomVideoRenderer;
import doom.DoomMain;
import doom.player_t;

/**
 * This is Mocha Doom's famous parallel software renderer. It builds on the
 * basic software renderer, but adds specialized handling for drawing segs
 * (walls) and spans (floors) in parallel. There's inherent parallelism between
 * walls and floor, and internal parallelism between walls and between floors.
 * However, visplane limits and openings need to be pre-computed before any
 * actual drawing starts, that's why rendering of walls is stored in "RWI"s or
 * "Render Wall Instructions", and then rendered once they are all in place and
 * the can be parallelized between rendering threads. Rendering of sprites is
 * NOT parallelized yet (and probably not worth it, at this point).
 * 
 * @author admin
 */

public abstract class ParallelRenderer<T, V>
        extends AbstractParallelRenderer<T, V> {

    public ParallelRenderer(DoomMain<T, V> DM, int wallthread,
            int floorthreads, int nummaskedthreads) {
        super(DM, wallthread, floorthreads, nummaskedthreads);
        
        // Register parallel seg drawer with list of RWI subsystems.
        ParallelSegs tmp= new ParallelSegs(this);
        this.MySegs = tmp;
        RWIs= tmp;
        
        this.MyThings =
                new SimpleThings<T,V>(this);
        //this.MyPlanes = new Planes(this);// new ParallelPlanes<T, V>(DM.R);

    }

    /**
     * Default constructor, 1 seg, 1 span and two masked threads.
     * 
     * @param DM
     */
    public ParallelRenderer(DoomMain<T, V> DM) {
        this(DM, 1, 1, 2);
    }



    /**
     * R_RenderView As you can guess, this renders the player view of a
     * particular player object. In practice, it could render the view of any
     * mobj too, provided you adapt the SetupFrame method (where the viewing
     * variables are set).
     * 
     * @throws IOException
     */

    public void RenderPlayerView(player_t player) {

        // Viewing variables are set according to the player's mobj. Interesting
        // hacks like
        // free cameras or monster views can be done.
        SetupFrame(player);

        /*
         * Uncommenting this will result in a very existential experience if
         * (Math.random()>0.999){ thinker_t shit=P.getRandomThinker(); try {
         * mobj_t crap=(mobj_t)shit; player.mo=crap; } catch (ClassCastException
         * e){ } }
         */

        // Clear buffers.
        MyBSP.ClearClipSegs();
        seg_vars.ClearDrawSegs();
        vp_vars.ClearPlanes();
        MySegs.ClearClips();
        VIS.ClearSprites();
        // Check for new console commands.
        DGN.NetUpdate();

        // The head node is the last node output.
        MyBSP.RenderBSPNode(LL.numnodes - 1);

        // System.out.printf("Submitted %d RWIs\n",RWIcount);

        MySegs.CompleteRendering();

        // Check for new console commands.
        DGN.NetUpdate();

        // "Warped floor" fixed, same-height visplane merging fixed.
        MyPlanes.DrawPlanes();

        // Check for new console commands.
        DGN.NetUpdate();

        MySegs.sync();
        MyPlanes.sync();

//            drawsegsbarrier.await();
//            visplanebarrier.await();


        MyThings.DrawMasked();

        // RenderRMIPipeline();
        /*
         * try { maskedbarrier.await(); } catch (Exception e) {
         * e.printStackTrace(); }
         */

        // Check for new console commands.
        DGN.NetUpdate();
    }

    public static final class Indexed
            extends ParallelRenderer<byte[], byte[]> {

        public Indexed(DoomMain<byte[], byte[]> DM, int wallthread,
                int floorthreads, int nummaskedthreads) {
            super(DM, wallthread, floorthreads, nummaskedthreads);

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

 protected void R_InitDrawingFunctions(){            
            
            // Span functions. Common to all renderers unless overriden
            // or unused e.g. parallel renderers ignore them.
            DrawSpan=new R_DrawSpanUnrolled.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
            DrawSpanLow=new R_DrawSpanLow.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
            
            
            // Translated columns are usually sprites-only.
            DrawTranslatedColumn=new R_DrawTranslatedColumn.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawTranslatedColumnLow=new R_DrawTranslatedColumnLow.Indexed(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
          //  DrawTLColumn=new R_DrawTLColumn(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            
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
        
        protected void InitMaskedWorkers() {
            for (int i = 0; i < NUMMASKEDTHREADS; i++) {
                maskedworkers[i] =
                    new MaskedWorker.Indexed(this, i, SCREENWIDTH,
                            SCREENHEIGHT, ylookup, columnofs, NUMMASKEDTHREADS,
                            screen, maskedbarrier, BLURRY_MAP);
                detailaware.add(maskedworkers[i]);
                // "Peg" to sprite manager.
                maskedworkers[i].cacheSpriteManager(SM);
            }
        }

		@Override
		public RenderWallExecutor<byte[], byte[]>[] InitRWIExecutors(
				int num,ColVars<byte[], byte[]>[] RWI) {
				RenderWallExecutor<byte[],byte[]>[] tmp=
						new RenderWallExecutor.Indexed[num];
				
				for (int i=0;i<num;i++)
					tmp[i]=new RenderWallExecutor.Indexed(SCREENWIDTH, SCREENHEIGHT, columnofs, ylookup, screen, RWI, drawsegsbarrier);
			
				return tmp;
		}

    }

    @Override
    protected void InitParallelStuff() {


    	// ...yeah, it works.
    	if (!(RWIs==null)){ 
    		ColVars<T,V>[] RWI=RWIs.getRWI();
    		RenderWallExecutor<T,V>[] RWIExec=InitRWIExecutors(NUMWALLTHREADS,RWI);
    		RWIs.setExecutors(RWIExec);
    		
            for (int i = 0; i < NUMWALLTHREADS; i++) {
            	
                detailaware.add(RWIExec[i]);
            }
    		}
    		
    	
        // CATCH: this must be executed AFTER screen is set, and
        // AFTER we initialize the RWI themselves,
        // before V is set (right?)

    	
        // This actually only creates the necessary arrays and
        // barriers. Things aren't "wired" yet.

        // Using "render wall instruction" subsystem

        // Using masked sprites
       // RMIExec = new RenderMaskedExecutor[NUMMASKEDTHREADS];

        // Using
        //vpw = new Runnable[NUMFLOORTHREADS];
        //maskedworkers = new MaskedWorker.Indexed[NUMMASKEDTHREADS];






        // RWIcount = 0;

        // InitRWISubsystem();
        // InitRMISubsystem();
        // InitPlaneWorkers();
        // InitMaskedWorkers();

        // If using masked threads, set these too.
        TexMan.setSMPVars(NUMMASKEDTHREADS);

    }

    /*
     * private void InitPlaneWorkers(){ for (int i = 0; i < NUMFLOORTHREADS;
     * i++) { vpw[i] = new VisplaneWorker2(i,SCREENWIDTH, SCREENHEIGHT,
     * columnofs, ylookup, screen, visplanebarrier, NUMFLOORTHREADS);
     * //vpw[i].id = i; detailaware.add((IDetailAware) vpw[i]); } }
     */

    @Override
    public void initScaling() {
    	
    	super.initScaling();
    	

        /*
         * TODO: relay to dependent objects. super.initScaling();
         * ColVars<byte[],byte[]> fake = new ColVars<byte[],byte[]>(); RWI =
         * C2JUtils.createArrayOfObjects(fake, SCREENWIDTH * 3); // Be MUCH more
         * generous with this one. RMI = C2JUtils.createArrayOfObjects(fake,
         * SCREENWIDTH * 6);
         */

    }

    protected abstract void InitMaskedWorkers();

    public static final class HiColor
            extends ParallelRenderer<byte[], short[]> {

        public HiColor(DoomMain<byte[], short[]> DM, int wallthread,
                int floorthreads, int nummaskedthreads) {
            super(DM, wallthread, floorthreads, nummaskedthreads);
            
            // Init light levels
            colormaps.scalelight = new short[LIGHTLEVELS][MAXLIGHTSCALE][];
            colormaps.scalelightfixed = new short[MAXLIGHTSCALE][];
            colormaps.zlight = new short[LIGHTLEVELS][MAXLIGHTZ][];
        }

        protected void InitMaskedWorkers() {
            for (int i = 0; i < NUMMASKEDTHREADS; i++) {
                maskedworkers[i] =
                    new MaskedWorker.HiColor(this, i, SCREENWIDTH,
                            SCREENHEIGHT, ylookup, columnofs, NUMMASKEDTHREADS,
                            screen, maskedbarrier);
                detailaware.add(maskedworkers[i]);
                // "Peg" to sprite manager.
                maskedworkers[i].cacheSpriteManager(SM);
            }
        }

        /**
         * R_InitColormaps This is VERY different for hicolor.
         * 
         * @throws IOException
         */
        protected void InitColormaps()
                throws IOException {

            colormaps.colormaps = V.getColorMaps();
            System.out.println("COLORS15 Colormaps: "
                    + colormaps.colormaps.length);

            // MAES: blurry effect is hardcoded to this colormap.
            // Pointless, since we don't use indexes. Instead, a half-brite
            // processing works just fine.
            BLURRY_MAP = null;// colormaps[0];
        }
        
        protected void R_InitDrawingFunctions(){            
            
            // Span functions. Common to all renderers unless overriden
            // or unused e.g. parallel renderers ignore them.
            DrawSpan=new R_DrawSpanUnrolled.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
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

		@Override
		public RenderWallExecutor<byte[], short[]>[] InitRWIExecutors(
				int num,ColVars<byte[], short[]>[] RWI) {
				RenderWallExecutor<byte[],short[]>[] tmp=
						new RenderWallExecutor.HiColor[num];
				
				for (int i=0;i<num;i++)
					tmp[i]=new RenderWallExecutor.HiColor(SCREENWIDTH, SCREENHEIGHT, columnofs, ylookup, screen, RWI, drawsegsbarrier);
			
				return tmp;
		}

    }

    public static final class TrueColor
            extends ParallelRenderer<byte[], int[]> {

        public TrueColor(DoomMain<byte[], int[]> DM, int wallthread,
                int floorthreads, int nummaskedthreads) {
            super(DM, wallthread, floorthreads, nummaskedthreads);
            
            // Init light levels
            colormaps.scalelight = new int[LIGHTLEVELS][MAXLIGHTSCALE][];
            colormaps.scalelightfixed = new int[MAXLIGHTSCALE][];
            colormaps.zlight = new int[LIGHTLEVELS][MAXLIGHTZ][];
        }
        
  protected void R_InitDrawingFunctions(){            
            
            // Span functions. Common to all renderers unless overriden
            // or unused e.g. parallel renderers ignore them.
            DrawSpan=new R_DrawSpanUnrolled.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
            DrawSpanLow=new R_DrawSpanLow.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,dsvars,screen,I);
            
            
            // Translated columns are usually sprites-only.
            DrawTranslatedColumn=new R_DrawTranslatedColumn.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            DrawTranslatedColumnLow=new R_DrawTranslatedColumnLow.TrueColor(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            //DrawTLColumn=new R_DrawTLColumn(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I);
            
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

        /**
         * R_InitColormaps This is VERY different for hicolor.
         * 
         * @throws IOException
         */
        protected void InitColormaps()
                throws IOException {

            colormaps.colormaps = V.getColorMaps();
            System.out.println("COLORS15 Colormaps: "
                    + colormaps.colormaps.length);

            // MAES: blurry effect is hardcoded to this colormap.
            // Pointless, since we don't use indexes. Instead, a half-brite
            // processing works just fine.
            BLURRY_MAP = null;// colormaps[0];
        }
        
        protected void InitMaskedWorkers() {
            for (int i = 0; i < NUMMASKEDTHREADS; i++) {
                maskedworkers[i] =
                    new MaskedWorker.TrueColor(this,i, SCREENWIDTH, SCREENHEIGHT,
                            ylookup, columnofs, NUMMASKEDTHREADS, screen,
                            maskedbarrier);
                detailaware.add(maskedworkers[i]);
                // "Peg" to sprite manager.
                maskedworkers[i].cacheSpriteManager(SM);
            }
        }
        
		@Override
		public RenderWallExecutor<byte[], int[]>[] InitRWIExecutors(
				int num,ColVars<byte[], int[]>[] RWI) {
			RenderWallExecutor<byte[],int[]>[] tmp=
					new RenderWallExecutor.TrueColor[num];
			
			for (int i=0;i<num;i++)
				tmp[i]=new RenderWallExecutor.TrueColor(SCREENWIDTH, SCREENHEIGHT, columnofs, ylookup, screen, RWI, drawsegsbarrier);
		
			return tmp;
		}

    }

}
