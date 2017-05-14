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

package rr;

import java.io.IOException;

import rr.parallel.IGetSmpColumn;

/** All texture, flat and sprite management operations should be handled
 *  by an implementing class. As of now, the renderer does both, though it's
 *  not really the most ideal.
 *  
 * @author Velktron
 *
 */

public interface TextureManager<T> extends IGetColumn<T>, IGetCachedColumn<T>,IGetSmpColumn{

    public final static String[] texturelumps={"TEXTURE1","TEXTURE2"};
    public final static int NUMTEXLUMPS=texturelumps.length;
    public final static int TEXTURE1=0;
    public final static int TEXTURE2=1;
    
	int TextureNumForName(String texname);
	
	
	/**The "num" expected here is the internal flat number,
	 * not the absolute lump number. So impement accordingly.
	 * 
	 * @param flatname
	 * @return
	 */
	int FlatNumForName(String flatname);
	
	void PrecacheLevel() throws IOException;
	
	void GenerateComposite(int tex);
	
	int getTextureheight(int texnum);	
		
	int getTextureTranslation(int texnum);
	
	int getFlatTranslation(int flatnum);
	
	void setTextureTranslation(int texnum, int amount);
	
	void setFlatTranslation(int flatnum,int amount);

	int CheckTextureNumForName(String texnamem);

	String CheckTextureNameForNum(int texnum);
	
    int getTexturewidthmask(int tex);
   
    int getTextureColumnLump(int tex, int col);
   
    char getTextureColumnOfs(int tex, int col);

    T[] getTextureComposite(int tex);

    T getTextureComposite(int tex, int col);

    void InitFlats();

    void InitTextures() throws IOException;

    //int getFirstFlat();

    int getSkyTextureMid();

    int getSkyFlatNum();

    int getSkyTexture();

    void setSkyTexture(int skytexture);

    int InitSkyMap();

    void setSkyFlatNum(int skyflatnum);

    void GenerateLookup(int texnum)
            throws IOException;
    
    int getFlatLumpNum(int flatnum);


	T getRogueColumn(int lump, int column);

    patch_t getMaskedComposite(int tex);


    void GenerateMaskedComposite(int texnum);
    
    /** Return a "sanitized" patch. If data is insufficient, return
     *  a default patch or attempt a partial draw.
     * 
     * @param patchnum
     * @return
     */
    
    public T getSafeFlat(int flatnum);


    column_t GetColumnStruct(int tex, int col);


    void setSMPVars(int nUMMASKEDTHREADS);
    
    }
