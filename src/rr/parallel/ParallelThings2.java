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

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;

import rr.AbstractThings;
import rr.IMaskedDrawer;
import rr.ISpriteManager;
import rr.IVisSpriteManagement;
import rr.Renderer;
import v.IVideoScale;

/**  Alternate parallel sprite renderer using a split-screen strategy.
 *  For N threads, each thread gets to render only the sprites that are entirely
 *  in its own 1/Nth portion of the screen.
 *  
 *  Sprites that span more than one section, are drawn partially. Each thread
 *  only has to worry with the priority of its own sprites. Similar to the 
 *  split-seg parallel drawer.
 * 
 *  Uses the "masked workers" subsystem, there is no column pipeline: workers
 *  "tap" directly in the sprite sorted table and act accordingly (draw entirely,
 *  draw nothing, draw partially).
 *  
 *  It uses masked workers to perform the actual work, each of which is a complete
 *  Thing Drawer. 
 * 
 * @author velktron
 *
 */

public final class ParallelThings2<T,V>
        implements IMaskedDrawer<T,V> {

    MaskedWorker<T,V>[] maskedworkers;
    CyclicBarrier maskedbarrier;
    Executor tp;
    protected final IVisSpriteManagement<V> VIS;
    
    public ParallelThings2(Renderer<T,V> R) {
        this.VIS=R.getVisSpriteManager();

    }

    @Override
    public void DrawMasked() {

        VIS.SortVisSprites();

        for (int i = 0; i < maskedworkers.length; i++) {
            tp.execute(maskedworkers[i]);
        }

        try {
            maskedbarrier.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void completeColumn() {
        // Does nothing. Dummy.
    }

    @Override
    public void setPspriteScale(int scale) {
        for (int i = 0; i < maskedworkers.length; i++)
            maskedworkers[i].setPspriteScale(scale);
    }

    @Override
    public void setPspriteIscale(int scale) {
        for (int i = 0; i < maskedworkers.length; i++)
            maskedworkers[i].setPspriteIscale(scale);
    }

    @Override
    public void setVideoScale(IVideoScale vs) {
        for (int i = 0; i < maskedworkers.length; i++)
            maskedworkers[i].setVideoScale(vs);
    }

    @Override
    public void initScaling() {
        for (int i = 0; i < maskedworkers.length; i++)
            maskedworkers[i].initScaling();
        
    }

    @Override
    public void setDetail(int detailshift) {
        for (int i = 0; i < maskedworkers.length; i++)
            maskedworkers[i].setDetail(detailshift);
        
    }

    @Override
    public void cacheSpriteManager(ISpriteManager SM) {
        for (int i = 0; i < maskedworkers.length; i++)
            maskedworkers[i].cacheSpriteManager(SM);
        
    }

}
