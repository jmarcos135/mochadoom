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

package i;

import doom.ticcmd_t;

public class DummySystem implements IDoomSystem{

    @Override
    public void AllocLow(int length) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void BeginRead() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void EndRead() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void WaitVBL(int count) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public byte[] ZoneBase(int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int GetHeapSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void Tactile(int on, int off, int total) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void Quit() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ticcmd_t BaseTiccmd() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void Error(String error, Object... args) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void Error(String error) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void Init() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean GenerateAlert(String title, String cause) {
        // TODO Auto-generated method stub
        return false;
    }

}
