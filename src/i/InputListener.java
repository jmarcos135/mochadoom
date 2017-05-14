
/** Blatantly ripped off Jake 2. Sieg heil! */

// This file is part of Mocha Doom.
// Copyright (C) 1997-2001  id Software, Inc.
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

package i;

import java.awt.event.*;
import java.util.LinkedList;

import doom.event_t;
import doom.evtype_t;

/**
 * InputListener
 */
public final class InputListener implements KeyListener, MouseListener, 
        MouseMotionListener, ComponentListener, MouseWheelListener {

    // modifications of eventQueue must be thread safe!
    private static LinkedList<event_t> eventQueue = new LinkedList<event_t>();

    static void addEvent(event_t ev) {
        synchronized (eventQueue) {
            eventQueue.addLast(ev);
        }
    }

    public static event_t nextEvent() {
        event_t ev;
        synchronized (eventQueue) {
            ev = (!eventQueue.isEmpty())?(event_t)eventQueue.removeFirst():null;
        }
        return ev;
    }

    public void keyPressed(KeyEvent e) {
        if (!((e.getModifiersEx() & InputEvent.ALT_GRAPH_DOWN_MASK) != 0)) {
            addEvent(new event_t(evtype_t.ev_keydown, e.getKeyCode()));
        }
    }

    public void keyReleased(KeyEvent e) {
        addEvent(new event_t(evtype_t.ev_keyup, e.getKeyCode()));
    }

    public void keyTyped(KeyEvent e) {
        if ((e.getModifiersEx() & InputEvent.ALT_GRAPH_DOWN_MASK) != 0) {
            addEvent(new event_t(evtype_t.ev_keydown, e.getKeyCode()));
            addEvent(new event_t(evtype_t.ev_keyup, e.getKeyCode()));
        }       
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        addEvent(new event_t(evtype_t.ev_mouse, e.getButton()));
    }

    public void mouseReleased(MouseEvent e) {
        addEvent(new event_t(evtype_t.ev_mouse, e.getButton()));
    }

    public void mouseDragged(MouseEvent e) {
        addEvent(new event_t(evtype_t.ev_mouse, e.getButton(),e.getX(),e.getY()));
    }

    public void mouseMoved(MouseEvent e) {
        addEvent(new event_t(evtype_t.ev_mouse, e.getButton(),e.getX(),e.getY()));
    }
    
    public void mouseWheelMoved(MouseWheelEvent e) {
        addEvent(new event_t(evtype_t.ev_mousewheel, e.getWheelRotation()));
    }   

   // Don't listen?
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
       // addEvent(new event_t(event_t.ConfigureNotify, e));
    }

    public void componentResized(ComponentEvent e) {
      //  addEvent(new event_t(event_t.ConfigureNotify, e));
    }

    public void componentShown(ComponentEvent e) {
    //    JOGLKBD.c = e.getComponent();
    //    addEvent(new event_t(event_t.CreateNotify, e));
    }

}

