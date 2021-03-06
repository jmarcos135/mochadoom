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

package awt;

import i.InputListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import javax.swing.JPanel;

import v.DoomVideoRenderer;
import doom.DoomMain;
import doom.event_t;

/** A simple Doom display & keyboard driver for AWT.
 *  Uses a Canvas for painting and implements some
 *  of the IVideo methods.
 * 
 *  Uses 8-bit buffered images and switchable IndexColorModels.
 *  
 *  It's really basic, but allows testing and user interaction.
 *  Heavily based on the original LinuxDoom X11 implementation, and
 *  is similar in goals: just a functional, reference implementation
 *  to build upon whatever fancy extensions you like.
 *  
 *  The only "hitch" is that this implementation expects to be 
 *  initialized upon a BufferedRenderer with multiple images per 
 *  screen buffer in order to perform the palette switching trick.
 *  
 *  The images themselves don't have to be "BufferedImage",
 *  and in theory it could be possible to use e.g. MemoryImageSource
 *  for the same purpose . Oh well.
 *    
 *  
 * 
 * @author Velktron
 *
 */
public class SwingDoom extends DoomFrame<byte[]> {


    private static final long serialVersionUID = 3118508722502652276L;

    

		JPanel drawhere;

		/** Gimme some raw palette RGB data.
		 *  I will do the rest
		 *  
		 *  (hint: read this from the PLAYPAL
		 *   lump in the IWAD!!!).
		 * 
		 */
     
        @SuppressWarnings("unchecked")
		public SwingDoom(DoomMain DM, DoomVideoRenderer<?> V) {
        	super(DM,(DoomVideoRenderer<byte[]>) V);
        	drawhere=new JPanel();
        	}
        
        Point center;
        Rectangle rect;

        
        public String processEvents(){
            StringBuffer tmp=new StringBuffer();
            event_t event;
            while ( (event=InputListener.nextEvent()) != null ) {
                tmp.append(event.type.ordinal()+"\n");
            }
            return tmp.toString();
        }
        
        
        
        
        // This stuff should NOT get through in keyboard events.
        protected final int UNACCEPTABLE_MODIFIERS=(int) (InputEvent.ALT_GRAPH_DOWN_MASK+
        										 InputEvent.META_DOWN_MASK+
        										 InputEvent.META_MASK+
        										 InputEvent.WINDOW_EVENT_MASK+
        										 InputEvent.WINDOW_FOCUS_EVENT_MASK);
	

	@Override
	public void FinishUpdate() {
	    int		tics;
	    int		i;
	    
	    // draws little dots on the bottom of the screen
	    /*if (true)
	    {

		i = I.GetTime();
		tics = i - lasttic;
		lasttic = i;
		if (tics > 20) tics = 20;
		if (tics < 1) tics = 1;

		for (i=0 ; i<tics*2 ; i+=2)
		    RAWSCREEN[ (SCREENHEIGHT-1)*SCREENWIDTH + i] = (byte) 0xff;
		for ( ; i<20*2 ; i+=2)
			RAWSCREEN[ (SCREENHEIGHT-1)*SCREENWIDTH + i] = 0x0;
	    
	    } */

	    if (true)
        {

        i = TICK.GetTime();
        tics = i - lasttic;
        lasttic = i;
        if (tics<1) 
            frames++;
        else
        {
        //frames*=35;
        for (i=0 ; i<frames*2 ; i+=2)
            RAWSCREEN[ (height-1)*width + i] = (byte) 0xff;
        for ( ; i<20*2 ; i+=2)
            RAWSCREEN[ (height-1)*width + i] = 0x0;
        frames=0;
        }
        }

	    this.update(null);
	    //this.getInputContext().selectInputMethod(java.util.Locale.US);
		
	}
	
    @Override
    public void ReadScreen(byte[] scr) {
        System.arraycopy(this.RAWSCREEN, 0, scr, 0, RAWSCREEN.length);
        }


}

//$Log: SwingDoom.java,v $
//Revision 1.3  2012/09/24 17:16:23  velktron
//Massive merge between HiColor and HEAD. There's no difference from now on, and development continues on HEAD.
//
//Revision 1.2.2.1  2012/09/17 15:57:07  velktron
//Moved common code to DoomFrame
//
//Revision 1.2  2011/11/01 19:03:10  velktron
//Using screen number constants
//
//Revision 1.1  2011/10/23 18:11:07  velktron
//Split functionality into DoomFrame, gerenic compliance.
//
//Revision 1.12  2011/10/11 13:24:51  velktron
//Major overhaul to work with new renderer interface. Now only available windowing system.
//
//Revision 1.11  2011/08/01 00:59:57  velktron
//Shut up debug messages.
//
//Revision 1.10  2011/07/15 13:57:54  velktron
//Implement VI.ReadScreen as a future good practice.
//
//Revision 1.9  2011/06/23 15:42:38  velktron
//Added modular palette rotation to handle sub-14 cases.
//
//Revision 1.8  2011/06/14 09:54:20  velktron
//Separated palette generation/fixed OldAWTDoom
//
//Revision 1.7  2011/06/08 17:24:59  velktron
//Added support for gamma changes.
//
//Revision 1.6  2011/06/02 14:54:18  velktron
//Old AWTEvents deprecated. MochaEvents now default.
//
//Revision 1.3  2011/06/01 17:42:49  velktron
//Removed stupid nagging.
//
//Revision 1.2  2011/06/01 17:17:24  velktron
//New event system.
//
//Revision 1.1  2011/06/01 17:04:23  velktron
//New event system.
//
//Revision 1.4  2011/05/30 02:25:50  velktron
//Centering and offsetting on expose, proper exiting.
//
//Revision 1.3  2011/05/29 22:15:32  velktron
//Introduced IRandom interface.
//
//Revision 1.2  2011/05/29 20:58:58  velktron
//Added better mouse grabbing method, more reliable, more cross-OS.
//
//Revision 1.1  2011/05/27 13:26:56  velktron
//A slightly better, though not perfect, way to handle input, partially based on_D_'s work.
//