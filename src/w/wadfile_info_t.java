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
import java.util.zip.ZipEntry;

// CPhipps - changed wad init
// We _must_ have the wadfiles[] the same as those actually loaded, so there 
// is no point having these separate entities. This belongs here.

public class wadfile_info_t {
      public String name; // Also used as a resource identifier, so save with full path and all.
      public ZipEntry entry; // Secondary resource identifier e.g. files inside zip archives.
      public int type; // as per InputStreamSugar
      public wad_source_t src;
      public InputStream handle;
      public boolean cached; // Whether we use local caching e.g. for URL or zips
      public long maxsize=-1; // Update when known for sure. Will speed up seeking.
    }
