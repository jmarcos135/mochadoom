// This file is part of Mocha Doom.
// Copyright (C) 2010-2013  Victor Epitropou
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
import java.io.IOException;
import java.io.InputStream;

/** This is an interface implemented by objects that must be read form disk.
 *  Every object is supposed to do its own umarshalling. This way,
 *  structured and hierchical reads are possible. Another superior innovation
 *  of Mocha Doom ;-) Err....ok :-p
 *  
 * @author Velktron
 *
 */

public interface IReadableDoomObject {
    
    public void read(DataInputStream f) throws IOException ;
}
