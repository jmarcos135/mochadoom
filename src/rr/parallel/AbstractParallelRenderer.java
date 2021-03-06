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

import static data.Tables.finetangent;
import static m.fixed_t.FRACBITS;
import static m.fixed_t.FixedMul;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import data.Tables;
import doom.DoomStatus;

import rr.PlaneDrawer;
import rr.Renderer;
import rr.RendererState;
import rr.drawfuns.ColVars;
import utils.C2JUtils;

/**
 * Features and functionality which is common among parallel renderers
 * 
 * @author velktron
 */

public abstract class AbstractParallelRenderer<T, V>
        extends RendererState<T, V> implements RWI.Init<T, V>{

    public AbstractParallelRenderer(DoomStatus<T, V> DS, int wallthread,
            int floorthreads, int nummaskedthreads) {
        super(DS);
        this.NUMWALLTHREADS = wallthread;
        this.NUMFLOORTHREADS = floorthreads;
        this.NUMMASKEDTHREADS = nummaskedthreads;
        // Prepare the barriers for MAXTHREADS + main thread.
        drawsegsbarrier = new CyclicBarrier(NUMWALLTHREADS + 1);
        visplanebarrier = new CyclicBarrier(NUMFLOORTHREADS + 1);        
        maskedbarrier = new CyclicBarrier(NUMMASKEDTHREADS + 1);
        tp = Executors.newCachedThreadPool();

    }

    public AbstractParallelRenderer(DoomStatus<T, V> DS, int wallthread,
            int floorthreads) {
        super(DS);
        this.NUMWALLTHREADS = wallthread;
        this.NUMFLOORTHREADS = floorthreads;
        this.NUMMASKEDTHREADS = 1;
        // Prepare the barriers for MAXTHREADS + main thread.
        drawsegsbarrier = new CyclicBarrier(NUMWALLTHREADS + 1);
        visplanebarrier = new CyclicBarrier(NUMFLOORTHREADS + 1);        
        maskedbarrier = new CyclicBarrier(NUMMASKEDTHREADS + 1);
        tp = Executors.newCachedThreadPool();
    }

    // //////// PARALLEL OBJECTS /////////////
    protected final int NUMWALLTHREADS;

    protected final int NUMMASKEDTHREADS;

    protected final int NUMFLOORTHREADS;

    protected Executor tp;

    protected Runnable[] vpw;

    protected MaskedWorker<T, V>[] maskedworkers;

    protected CyclicBarrier drawsegsbarrier;

    protected CyclicBarrier visplanebarrier;

    protected CyclicBarrier maskedbarrier;

    protected static final boolean DEBUG = false;

    protected final class ParallelSegs
            extends SegDrawer implements RWI.Get<T,V>{

        public ParallelSegs(Renderer<?, ?> R) {
            super(R);
        }

        /**
         * Parallel version. Since there's so much crap to take into account
         * when rendering, the number of walls to render is unknown a-priori and
         * the BSP trasversal itself is not worth parallelizing, it makes more
         * sense to store "rendering instructions" as quickly as the BSP can be
         * transversed, and then execute those in parallel. Also saves on having
         * to duplicate way too much status.
         */

        @Override
        protected final void CompleteColumn() {

            // Don't wait to go over
            if (RWIcount >= RWI.length) {
                ResizeRWIBuffer();
            }

            // A deep copy is still necessary, as dc
            RWI[RWIcount].copyFrom(dcvars);

            // We only need to point to the next one in the list.
            RWIcount++;
        }

        /**
         * Starts the RenderWallExecutors. Sync is EXTERNAL, however.
         */

        @Override
        public void CompleteRendering() {

            for (int i = 0; i < NUMWALLTHREADS; i++) {

                RWIExec[i].setRange((i * RWIcount) / NUMWALLTHREADS,
                    ((i + 1) * RWIcount) / NUMWALLTHREADS);
                // RWIExec[i].setRange(i%NUMWALLTHREADS,RWIcount,NUMWALLTHREADS);
                tp.execute(RWIExec[i]);
            }

            // System.out.println("RWI count"+RWIcount);
            RWIcount = 0;
        }

        /*
         * Just what are "RWIs"? Stored wall rendering instructions. They can be
         * at most 3*SCREENWIDTH (if there are low, mid and high textures on
         * every column of the screen) Remember to init them and set screen and
         * ylookup for all of them. Their max number is static and work
         * partitioning can be done in any way, as long as you keep track of how
         * many there are in any given frame. This value is stored inside
         * RWIcount. TODO: there are cases when more than 3*SCREENWIDTH
         * instructions need to be stored. therefore we really need a resizeable
         * array here, but ArrayList is way too slow for our needs. Storing a
         * whole wall is not an option, as, once again, a wall may have a
         * variable number of columns and an irregular height profile -> we'd
         * need to look into visplanes ugh...
         */

        protected RenderWallExecutor<T, V>[] RWIExec;

        /** Array of "wall" (actually, column) instructions */

        protected ColVars<T, V>[] RWI;

        /**
         * Increment this as you submit RWIs to the "queue". Remember to reset
         * to 0 when you have drawn everything!
         */

        protected int RWIcount = 0;

        /**
         * Resizes RWI buffer, updates executors. Sorry for the hackish
         * implementation but ArrayList and pretty much everything in
         * Collections is way too slow for what we're trying to accomplish.
         */

        protected final void ResizeRWIBuffer() {
            ColVars<T, V> fake = new ColVars<T, V>();

            // Bye bye, old RWI.
            RWI = C2JUtils.resize(fake, RWI, RWI.length * 2);

            for (int i = 0; i < NUMWALLTHREADS; i++) {
                RWIExec[i].updateRWI(RWI);
            }
            // System.err.println("RWI Buffer resized. Actual capacity " +
            // RWI.length);
        }
        
        /**
         * R_InitRWISubsystem Initialize RWIs and RWI Executors. Pegs them to
         * the RWI, ylookup and screen[0].
         */

        public void initScaling() {
        	super.initScaling();
        	ColVars<T,V> fake=new ColVars<T,V>();
        	RWI=C2JUtils.createArrayOfObjects(fake, 3*SCREENWIDTH);

        }

		@Override
		public ColVars<T, V>[] getRWI() {
			return RWI;
		}

		@Override
		public void setExecutors(RenderWallExecutor<T, V>[] RWIExec) {
			this.RWIExec=RWIExec;
			
		}
		
		public void sync(){
			try {
				drawsegsbarrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

    protected final class ParallelPlanes<T, V>
            extends PlaneDrawer<T, V> {

        protected ParallelPlanes(Renderer<T, V> R) {
            super(R);
        }

        /**
         * R_DrawPlanes At the end of each frame. This also means that visplanes
         * must have been set BEFORE we called this function. Therefore, look
         * for errors behind.
         * 
         * @throws IOException
         */
        public void DrawPlanes() {

            if (RANGECHECK) {
                rangeCheckErrors();
            }

            // vpw[0].setRange(0,lastvisplane/2);
            // vpw[1].setRange(lastvisplane/2,lastvisplane);

            for (int i = 0; i < NUMFLOORTHREADS; i++)
                tp.execute(vpw[i]);
        }

    } // End Plane class

    protected final class ParallelSegs2
            extends SegDrawer {

        /**
         * RenderSeg subsystem. Similar concept to RWI, but stores
         * "Render Seg Instructions" instead. More complex to build, but
         * potentially faster in some situations, as it allows distributing load
         * per-wall, rather than per-screen portion. Requires careful
         * concurrency considerations.
         */
        protected RenderSegInstruction<V>[] RSI;

        /**
         * Increment this as you submit RSIs to the "queue". Remember to reset
         * to 0 when you have drawn everything!
         */
        protected int RSIcount = 0;

        protected RenderSegExecutor<byte[], V>[] RSIExec;

        public ParallelSegs2(Renderer<?, ?> R) {
            super(R);
        }

        @Override
        protected void RenderSegLoop() {
            int angle;
            int yl, top, bottom, yh, mid, texturecolumn = 0;

            // Generate Seg rendering instruction BEFORE the looping start
            // and anything is modified. The loop will be repeated in the
            // threads, but without marking ceilings/floors etc.
            GenerateRSI();

            for (; rw_x < rw_stopx; rw_x++) {
                // mark floor / ceiling areas
                yl = (topfrac + HEIGHTUNIT - 1) >> HEIGHTBITS;

                // no space above wall?
                if (yl < ceilingclip[rw_x] + 1)
                    yl = ceilingclip[rw_x] + 1;

                if (markceiling) {
                    top = ceilingclip[rw_x] + 1;
                    bottom = yl - 1;

                    if (bottom >= floorclip[rw_x])
                        bottom = floorclip[rw_x] - 1;

                    if (top <= bottom) {
                        vp_vars.visplanes[vp_vars.ceilingplane].setTop(rw_x,
                            (char) top);
                        vp_vars.visplanes[vp_vars.ceilingplane].setBottom(rw_x,
                            (char) bottom);
                    }
                }

                yh = bottomfrac >> HEIGHTBITS;

                if (yh >= floorclip[rw_x])
                    yh = floorclip[rw_x] - 1;

                // System.out.printf("Precompute: rw %d yl %d yh %d\n",rw_x,yl,yh);

                // A particular seg has been identified as a floor marker.

                if (markfloor) {
                    top = yh + 1;
                    bottom = floorclip[rw_x] - 1;
                    if (top <= ceilingclip[rw_x])
                        top = ceilingclip[rw_x] + 1;
                    if (top <= bottom) {
                        vp_vars.visplanes[vp_vars.floorplane].setTop(rw_x,
                            (char) top);
                        vp_vars.visplanes[vp_vars.floorplane].setBottom(rw_x,
                            (char) bottom);
                    }
                }

                // texturecolumn and lighting are independent of wall tiers
                if (segtextured) {
                    // calculate texture offset. Still important to do because
                    // of masked

                    angle =
                        Tables.toBAMIndex(rw_centerangle
                                + (int) xtoviewangle[rw_x]);
                    texturecolumn =
                        rw_offset - FixedMul(finetangent[angle], rw_distance);
                    texturecolumn >>= FRACBITS;
                }

                // Don't to any drawing, only compute bounds.
                if (midtexture != 0) {

                    dcvars.dc_source =
                        TexMan.GetCachedColumn(midtexture, texturecolumn);
                    // dc_m=dcvars.dc_source_ofs;
                    // single sided line
                    ceilingclip[rw_x] = (short) view.height;
                    floorclip[rw_x] = -1;
                } else {
                    // two sided line
                    if (toptexture != 0) {
                        // top wall
                        mid = pixhigh >> HEIGHTBITS;
                        pixhigh += pixhighstep;

                        if (mid >= floorclip[rw_x])
                            mid = floorclip[rw_x] - 1;

                        if (mid >= yl) {
                            dcvars.dc_source =
                                TexMan.GetCachedColumn(toptexture,
                                    texturecolumn);
                            ceilingclip[rw_x] = (short) mid;
                        } else
                            ceilingclip[rw_x] = (short) (yl - 1);
                    } else {
                        // no top wall
                        if (markceiling)
                            ceilingclip[rw_x] = (short) (yl - 1);
                    }

                    if (bottomtexture != 0) {
                        // bottom wall
                        mid = (pixlow + HEIGHTUNIT - 1) >> HEIGHTBITS;
                        pixlow += pixlowstep;

                        // no space above wall?
                        if (mid <= ceilingclip[rw_x])
                            mid = ceilingclip[rw_x] + 1;

                        if (mid <= yh) {
                            dcvars.dc_source =
                                TexMan.GetCachedColumn(bottomtexture,
                                    texturecolumn);
                            floorclip[rw_x] = (short) mid;
                        } else
                            floorclip[rw_x] = (short) (yh + 1);
                    } else {
                        // no bottom wall
                        if (markfloor)
                            floorclip[rw_x] = (short) (yh + 1);
                    }

                    if (maskedtexture) {
                        // save texturecol
                        // for backdrawing of masked mid texture
                        seg_vars.maskedtexturecol[seg_vars.pmaskedtexturecol
                                + rw_x] = (short) texturecolumn;
                    }
                }

                rw_scale += rw_scalestep;
                topfrac += topstep;
                bottomfrac += bottomstep;
            }
        }

        protected final void GenerateRSI() {

            if (RSIcount >= RSI.length) {
                ResizeRSIBuffer();
            }

            RenderSegInstruction<V> rsi = RSI[RSIcount];
            rsi.centery = view.centery;
            rsi.bottomfrac = bottomfrac;
            rsi.bottomstep = bottomstep;
            rsi.bottomtexture = bottomtexture;
            rsi.markceiling = markceiling;
            rsi.markfloor = markfloor;
            rsi.midtexture = midtexture;
            rsi.pixhigh = pixhigh;
            rsi.pixhighstep = pixhighstep;
            rsi.pixlow = pixlow;
            rsi.pixlowstep = pixlowstep;
            rsi.rw_bottomtexturemid = rw_bottomtexturemid;
            rsi.rw_centerangle = rw_centerangle;
            rsi.rw_distance = rw_distance;
            rsi.rw_midtexturemid = rw_midtexturemid;
            rsi.rw_offset = rw_offset;
            rsi.rw_scale = rw_scale;
            rsi.rw_scalestep = rw_scalestep;
            rsi.rw_stopx = rw_stopx;
            rsi.rw_toptexturemid = rw_toptexturemid;
            rsi.rw_x = rw_x;
            rsi.segtextured = segtextured;
            rsi.topfrac = topfrac;
            rsi.topstep = topstep;
            rsi.toptexture = toptexture;
            rsi.walllights = colormaps.walllights;
            rsi.viewheight = view.height;
            // rsi.floorplane=floorplane;
            // rsi.ceilingplane=ceilingplane;
            RSIcount++;
        }

        @Override
        protected void CompleteColumn() {
            // TODO Auto-generated method stub

        }

        protected void RenderRSIPipeline() {

            for (int i = 0; i < NUMWALLTHREADS; i++) {
                RSIExec[i].setRSIEnd(RSIcount);
                // RWIExec[i].setRange(i%NUMWALLTHREADS,RWIcount,NUMWALLTHREADS);
                tp.execute(RSIExec[i]);
            }

            // System.out.println("RWI count"+RWIcount);
            RSIcount = 0;
        }

        /**
         * Resizes RWI buffer, updates executors. Sorry for the hackish
         * implementation but ArrayList and pretty much everything in
         * Collections is way too slow for what we're trying to accomplish.
         */

        protected void ResizeRSIBuffer() {

            RenderSegInstruction<V> fake = new RenderSegInstruction<V>();
            // Bye bye, old RSI.
            RSI = C2JUtils.resize(fake, RSI, RSI.length * 2);

            for (int i = 0; i < NUMWALLTHREADS; i++) {
                RSIExec[i].updateRSI(RSI);
            }

            System.out.println("RWI Buffer resized. Actual capacity "
                    + RSI.length);
        }

    }

    protected final class ParallelPlanes2<T, V>
            extends PlaneDrawer<T, V> {

        protected ParallelPlanes2(Renderer<T, V> R) {
            super(R);
        }

        /**
         * R_DrawPlanes At the end of each frame. This also means that visplanes
         * must have been set BEFORE we called this function. Therefore, look
         * for errors behind.
         * 
         * @throws IOException
         */
        public void DrawPlanes() {

            if (RANGECHECK) {
                rangeCheckErrors();
            }

            // vpw[0].setRange(0,lastvisplane/2);
            // vpw[1].setRange(lastvisplane/2,lastvisplane);

            for (int i = 0; i < NUMFLOORTHREADS; i++)
                tp.execute(vpw[i]);
        }

    } // End Plane class

    // / RWI SUBSYSTEM
    // / AKA "Render Wall Instruction": instructions that store only a single
    // column
    // from a wall

    /**
     * R_InitRSISubsystem Initialize RSIs and RSI Executors. Pegs them to the
     * RSI, ylookup and screen[0].
     */

    // protected abstract void InitRSISubsystem();

    /*
     * { // CATCH: this must be executed AFTER screen is set, and // AFTER we
     * initialize the RWI themselves, // before V is set (right?) //offsets=new
     * int[NUMWALLTHREADS]; for (int i=0;i<NUMWALLTHREADS;i++){ RSIExec[i]=new
     * RenderSegExecutor.HiColor( SCREENWIDTH, SCREENHEIGHT, i, screen, this,
     * TexMan, RSI, MySegs.getBLANKCEILINGCLIP(), MySegs.getBLANKFLOORCLIP(),
     * MySegs.getCeilingClip(), MySegs.getFloorClip(), columnofs, xtoviewangle,
     * ylookup, this.visplanes, this.visplanebarrier);
     * RSIExec[i].setVideoScale(this.vs); RSIExec[i].initScaling(); // Each
     * SegExecutor sticks to its own half (or 1/nth) of the screen.
     * RSIExec[i].setScreenRange
     * (i*(SCREENWIDTH/NUMWALLTHREADS),(i+1)*(SCREENWIDTH/NUMWALLTHREADS));
     * detailaware.add(RSIExec[i]); } for (int i=0;i<NUMFLOORTHREADS;i++){
     * vpw[i]=new VisplaneWorker(i,SCREENWIDTH,SCREENHEIGHT,columnofs,ylookup,
     * screen,visplanebarrier,NUMFLOORTHREADS); detailaware.add((IDetailAware)
     * vpw[i]); } }
     */

    /** Creates RMI Executors */

    // protected abstract void InitRMISubsystem();

    /*
     * { for (int i = 0; i < NUMMASKEDTHREADS; i++) { // Each masked executor
     * gets its own set of column functions. RMIExec[i] = new
     * RenderMaskedExecutor(SCREENWIDTH, SCREENHEIGHT, columnofs, ylookup,
     * screen, RMI, maskedbarrier, // Regular masked columns new
     * R_DrawColumnBoom
     * (SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I), new
     * R_DrawColumnBoomLow
     * (SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I), //
     * Fuzzy columns new
     * R_DrawFuzzColumn.HiColor(SCREENWIDTH,SCREENHEIGHT,ylookup
     * ,columnofs,maskedcvars,screen,I), new
     * R_DrawFuzzColumnLow.HiColor(SCREENWIDTH
     * ,SCREENHEIGHT,ylookup,columnofs,maskedcvars,screen,I), // Translated
     * columns new
     * R_DrawTranslatedColumn(SCREENWIDTH,SCREENHEIGHT,ylookup,columnofs
     * ,maskedcvars,screen,I), new
     * R_DrawTranslatedColumnLow(SCREENWIDTH,SCREENHEIGHT
     * ,ylookup,columnofs,maskedcvars,screen,I) ); detailaware.add(RMIExec[i]);
     * } }
     */

    public void Init(){
    	super.Init();
    	InitParallelStuff();
    }
    
    /**
     * Any scaling and implementation-specific stuff to do for parallel stuff
     * should go here. This method is called internally by the public Init().
     * The idea is that the renderer should be up & running after you finally
     * called this.
     */
    protected abstract void InitParallelStuff();
    
    /** Override this in one of the final implementors, if you want it to work */
    
    public RenderWallExecutor<T,V>[] InitRWIExecutors(int num,ColVars<T,V>[] RWI){
    	return null;
    }
    RWI.Get<T,V> RWIs;
    
}
