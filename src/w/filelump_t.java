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

package w;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/** filelumps are on-disk structures. lumpinfos are almost the same, but are memory only.
 * 
 * @author Maes
 *
 */

public class filelump_t  implements IReadableDoomObject, IWritableDoomObject {
    public long         filepos;
    public long         size; // Is INT 32-bit in file!
    public String        name; // Whatever appears inside the wadfile
    public String actualname; // Sanitized name, e.g. after compression markers
        
    public boolean big_endian=false; // E.g. Jaguar
    public boolean compressed=false; // Compressed lump
    
    public void read(DataInputStream f) throws IOException{
        // MAES: Byte Buffers actually make it convenient changing byte order on-the-fly.
        // But RandomAccessFiles (and inputsteams) don't :-S

        if (!big_endian){
        filepos=DoomIO.readUnsignedLEInt(f);
        size=DoomIO.readUnsignedLEInt(f);

        } else {
            filepos=f.readInt();
            size=f.readInt();

        }
        
        // Names used in the reading subsystem should be upper case,
        // but check for compressed status first
        name=DoomIO.readNullTerminatedString(f,8);
        
       
        char[] stuff= name.toCharArray();
        
        // It's a compressed lump
        if (stuff[0] > 0x7F) {
            this.compressed=true;
            stuff[0]&=0x7F; 
        }
        
        actualname=new String(stuff).toUpperCase();
        
        
    }

    public static int sizeof(){
        return (4+4+8);
    }

    @Override
    public void write(DataOutputStream dos)
            throws IOException {
        if (!big_endian){
            DoomIO.writeLEInt(dos, (int) filepos);
            DoomIO.writeLEInt(dos, (int) size);
        } else {
                dos.writeInt((int) filepos);
                dos.writeInt((int) size);
        }
        DoomIO.writeString(dos, name, 8);
        
    }
    
}