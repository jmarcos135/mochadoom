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

public class wadheader_t implements IReadableDoomObject, IWritableDoomObject {
    public String type;
    public int numentries;
    public int tablepos;
    
    public boolean big_endian=false;
    
    public void read(DataInputStream f) throws IOException{

        type=DoomIO.readNullTerminatedString(f,4);
        
        if (!big_endian){
        numentries=(int) DoomIO.readUnsignedLEInt(f);
        tablepos=(int) DoomIO.readUnsignedLEInt(f);

        } else {
            numentries=f.readInt();
            tablepos=f.readInt();
        }
        
    }

    public static int sizeof(){
        return 16;
    }

    @Override
    public void write(DataOutputStream dos)
            throws IOException {
        DoomIO.writeString(dos, type, 4);
        
        if (!big_endian){
            DoomIO.writeLEInt(dos, (int) numentries);
            DoomIO.writeLEInt(dos, (int) tablepos);
        } else {
                dos.writeInt((int) numentries);
                dos.writeInt((int) tablepos);
        }
        
        
    }

}
