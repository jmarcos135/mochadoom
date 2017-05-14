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

import java.io.InputStream;

/*
typedef struct
{
  // WARNING: order of some fields important (see info.c).

  char  name[9];
  int   size;

    // killough 4/17/98: namespace tags, to prevent conflicts between resources
  enum {
    ns_global=0,
    ns_sprites,
    ns_flats,
    ns_colormaps,
    ns_prboom,
    ns_demos,
    ns_hires //e6y
  } li_namespace; // haleyjd 05/21/02: renamed from "namespace"

  wadfile_info_t *wadfile;
  int position;
  wad_source_t source;
  int flags; //e6y
} lumpinfo_t; */

public class lumpinfo_t implements Cloneable{
        public String    name;
        public InputStream     handle;
        public long     position;
        public long     size;
        // A 32-bit hash which should be enough for searching through hashtables.
        public int hash;
        // A 64-bit hash that just maps an 8-char string to a long num, good for hashing
        // or for direct comparisons.
        //public long stringhash;
        // Intepreting the first 32 bits of their name as an int. Used in initsprites.
        public int intname; 
        // public int next;
        //public int index;
        
        // For BOOM compatibility
        public li_namespace namespace;
        public wadfile_info_t wadfile;
        
        public int hashCode(){
            return hash;
        }
        
        public String toString(){
            return (name +" "+ Integer.toHexString(hash));
        }
        
        public lumpinfo_t clone(){
        	lumpinfo_t tmp=new lumpinfo_t();
        	tmp.name=name; // Well... a reference will do.
        	tmp.handle=handle;
        	tmp.position=position;
        	tmp.size=size;
        	tmp.hash=hash;
        	tmp.intname=intname;
        	tmp.namespace=namespace;
        	tmp.wadfile=wadfile;
        	
			return tmp;
        	
        }
        
    }
