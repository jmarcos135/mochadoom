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

package doom;

import java.nio.ByteBuffer;

import utils.C2JUtils;
import w.DoomBuffer;

public class doomdata_t implements IDatagramSerializable {

    public static final int DOOMDATALEN=8+data.Defines.BACKUPTICS*ticcmd_t.TICCMDLEN;
    
     // High bit is retransmit request.
     /** MAES: was "unsigned" */
     public int        checksum; 
     
     /* CAREFUL!!! Those "bytes" are actually unsigned
      * 
      */
     
     /** Only valid if NCMD_RETRANSMIT. */     
     public byte        retransmitfrom;
     
     public byte        starttic;
     public byte        player;
     public byte        numtics;
     public ticcmd_t[]        cmds;
     
    public doomdata_t(){
        cmds=new ticcmd_t[data.Defines.BACKUPTICS];
        C2JUtils.initArrayOfObjects(cmds);
        // Enough space for its own header + the ticcmds;
        buffer=new byte[DOOMDATALEN];
        // This "pegs" the ByteBuffer to this particular array.
        // Separate updates are not necessary.
        bbuf=ByteBuffer.wrap(buffer);
    }
    
    // Used for datagram serialization.
    private byte[] buffer;
    private ByteBuffer bbuf;

    
    
    @Override
    public byte[] pack() {        
        
        bbuf.rewind();
        
        // Why making it harder?
        bbuf.putInt(checksum);
        bbuf.put(retransmitfrom);
        bbuf.put(starttic);
        bbuf.put(player);
        bbuf.put(numtics);
        
        // FIXME: it's probably more efficient to use System.arraycopy ? 
        // Or are the packets too small anyway? At most we'll be sending "doomdata_t's"
        
        for (int i=0;i<cmds.length;i++){
            bbuf.put(cmds[i].pack());
        }
        
        return bbuf.array();
    
    }

    @Override
    public void pack(byte[] buf, int offset) {
        
        // No need to make it harder...just pack it and slap it in.
        byte[] tmp=this.pack();
        System.arraycopy(tmp, 0, buf, offset, tmp.length);        
    }

    @Override
    public void unpack(byte[] buf) {
        unpack(buf,0);
    }

    @Override
    public void unpack(byte[] buf, int offset) {
        checksum=DoomBuffer.getBEInt(buf);
        offset=+4;
        retransmitfrom=buf[offset++];
        starttic=buf[offset++];
        player=buf[offset++];
        numtics=buf[offset++];
        
        for (int i=0;i<cmds.length;i++){
            cmds[i].unpack(buf,offset);
            offset+=ticcmd_t.TICCMDLEN;
            }
        
    }
    
    public void selfUnpack(){
        unpack(this.buffer);
    }
    
    public void copyFrom(doomdata_t source) {        
        this.checksum=source.checksum;
        this.numtics=source.numtics;
        this.player=source.player;
        this.retransmitfrom=source.retransmitfrom;
        this.starttic=source.starttic;
        
        // MAES: this was buggy as hell, and didn't work at all, which
        // in turn prevented other subsystems such as speed throttling and
        // networking to work.
        // 
        // This should be enough to alter the ByteBuffer too.
        //System.arraycopy(source.cached(), 0, this.buffer, 0, DOOMDATALEN);
        // This should set all fields
        //selfUnpack();        
        }
    
    @Override
     public byte[] cached(){
         return this.buffer;
     }
    
    StringBuilder sb=new StringBuilder();
    public String toString(){
        sb.setLength(0);
        sb.append("doomdata_t ");
        sb.append(retransmitfrom);
        sb.append(" starttic ");
        sb.append(starttic);
        sb.append(" player ");
        sb.append(player);
        sb.append(" numtics ");
        sb.append(numtics);
        return sb.toString();
        
    }

 }
