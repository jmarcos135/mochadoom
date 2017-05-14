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

package v;

import doom.ICommandLineManager;
import static utils.C2JUtils.*;

public class VisualSettings {

    /** Default video scale is "triple vanilla: 3 x (320 x 200) */
    public final static IVideoScale vanilla=new VideoScaleInfo(1.0f);
    public final static IVideoScale double_vanilla=new VideoScaleInfo(2.0f);
    public final static IVideoScale triple_vanilla=new VideoScaleInfo(3.0f);
    public final static IVideoScale default_scale=triple_vanilla;
    
    /** Parses the command line for resolution-specific commands, and creates
     *  an appropriate IVideoScale object.
     *  
     * @param CM
     * @return
     */
    
    public final static IVideoScale parse(ICommandLineManager CM){
        
        int width=-1;
        int height=-1;
        
        // -multiply parameter defined from linux doom.
        // It gets priority over all others, if present.
        int p=CM.CheckParm("-multiply");
        if (eval(p)) {            
            try {
                width=Integer.parseInt(CM.getArgv(p+1));
            } catch (NumberFormatException e){
                // We failed. Mark the occasion.
                width=-1;
            }
        }
        
        // If -multiply was successful, trump any others.
        // Implied to be a solid multiple of the vanilla resolution.
        if (width>0 && width<=5) return new VideoScaleInfo(width); 
        
        // Width defined?
        p=CM.CheckParm("-width");
        if (eval(p)) {            
            try {
                width=Integer.parseInt(CM.getArgv(p+1));
            } catch (NumberFormatException e){
                // We failed. Mark the occasion.
                width=-1;
            }
        }
        
        p=CM.CheckParm("-height");
        if (eval(p)) {
            try {
                height=Integer.parseInt(CM.getArgv(p+1));
            } catch (NumberFormatException e){
                // We failed. Mark the occasion.
                height=-1;
            }
        }
    
        // Nothing to do?
        if (height==-1 && width==-1) return default_scale;

        // At least one of them is not a dud.
        int mulx, muly,mulf;
        
        // Break them down to the nearest multiple of the base width or height.
        mulx=Math.round((float)width/IVideoScale.BASE_WIDTH);
        muly=Math.round((float)height/IVideoScale.BASE_HEIGHT);
        
        // Do not accept zero or sub-vanilla resolutions
        if (mulx>0 || muly>0){
        
        // Use the maximum multiplier. We don't support skewed
        // aspect ratios yet.
        mulf=Math.max(mulx,muly);
        if (mulf>=1 && mulf<=5) return new VideoScaleInfo(mulf);
        }
        
        // In all other cases...
        return default_scale;
        
        
        
    }
    
}
