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

package demo;

import static data.Limits.MAXPLAYERS;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import utils.C2JUtils;
import w.CacheableDoomObject;
import w.DoomBuffer;
import w.DoomIO;
import defines.skill_t;

public class VanillaDoomDemo implements IDoomDemo,CacheableDoomObject{

	 // This stuff is in the demo header, in the order it appears
	 // However everything is byte-sized when read from disk or to memory.
	 public int version; 
	 public skill_t skill;
	 public int episode;
	 public int map;
	 public boolean deathmatch;
	 public boolean respawnparm;
	 public boolean fastparm;
	 public boolean nomonsters;
	 public int consoleplayer;
	 public boolean[] playeringame; // normally MAXPLAYERS (4) for vanilla.
	 
	 protected int p_demo;
	 
     //  After that, demos contain a sequence of ticcmd_t's to build dynamically at
     // load time or when recording. This abstraction allows arbitrary demo sizes
     // and easy per-step handling, and even changes/extensions. Just make sure
     // that ticcmd_t's are serializable!
     // Also, the format used in demo lumps is NOT the same as in datagrams/network
     // (e.g. there is no consistency) and their handling is modified.
     
     VanillaTiccmd[] commands;
     List<IDemoTicCmd> demorecorder;
     
     public VanillaDoomDemo(){
         this.demorecorder=new  ArrayList<IDemoTicCmd> ();
     }
     
	 public void unpack(ByteBuffer b){
	     
	     // Just the Header info for vanilla should be 13 bytes.
	     // 1 byte at the end is the end-demo marker
	     // So valid vanilla demos should have sizes that
	     // fit the formula 14+4n, since each vanilla 
	     // demo ticcmd_t is 4 bytes.
	     int lens=(b.limit()-13)/4;	     
	     boolean vanilla=(b.limit()==(14+4*lens));
	     
	     // Minimum valid vanilla demo should be 14 bytes...in theory.
     if (b.limit()<14) {
         // Use skill==null as an indicator that loading didn't go well.
         skill=null;
         return;
     }
     
     version=b.get();		
     
     try {
     skill = skill_t.values()[b.get()];
     } catch (Exception e){
         skill=null;
     }
     episode = b.get(); 
     map = b.get(); 
     deathmatch = b.get()!=0;
     respawnparm = b.get()!=0;
     fastparm = b.get()!=0;
     nomonsters = b.get()!=0;
     consoleplayer = b.get();
     
     playeringame=new boolean[MAXPLAYERS];
     
     for (int i=0 ; i<MAXPLAYERS ; i++) 
     playeringame[i] = b.get()!=0;
     
     
     this.commands=new VanillaTiccmd[lens];
     C2JUtils.initArrayOfObjects(this.commands, VanillaTiccmd.class);
     
     try {
		DoomBuffer.readObjectArray(b, this.commands, lens);
	} catch (IOException e) {
	    skill=null;
	    return;
	}

     }

    @Override
    public IDemoTicCmd getNextTic() {
        if ((commands!=null)&&(p_demo<commands.length)){

        return commands[p_demo++];
        }
        else return null;
    }

    @Override
    public void putTic(IDemoTicCmd tic) {
        demorecorder.add(tic);

    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public skill_t getSkill() {
        return skill;
    }

    @Override
    public void setSkill(skill_t skill) {
        this.skill = skill;
    }

    @Override
    public int getEpisode() {
        return episode;
    }

    @Override
    public void setEpisode(int episode) {
        this.episode = episode;
    }

    @Override
    public int getMap() {
        return map;
    }

    @Override
    public void setMap(int map) {
        this.map = map;
    }

    @Override
    public boolean isDeathmatch() {
        return deathmatch;
    }

    @Override
    public void setDeathmatch(boolean deathmatch) {
        this.deathmatch = deathmatch;
    }

    @Override
    public boolean isRespawnparm() {
        return respawnparm;
    }

    @Override
    public void setRespawnparm(boolean respawnparm) {
        this.respawnparm = respawnparm;
    }

    @Override
    public boolean isFastparm() {
        return fastparm;
    }

    @Override
    public void setFastparm(boolean fastparm) {
        this.fastparm = fastparm;
    }

    @Override
    public boolean isNomonsters() {
        return nomonsters;
    }

    @Override
    public void setNomonsters(boolean nomonsters) {
        this.nomonsters = nomonsters;
    }

    @Override
    public int getConsoleplayer() {
        return consoleplayer;
    }

    @Override
    public void setConsoleplayer(int consoleplayer) {
        this.consoleplayer = consoleplayer;
    }
    
    @Override
    public boolean[] getPlayeringame() {
        return playeringame;
    }

    @Override
    public void setPlayeringame(boolean[] playeringame) {
        this.playeringame = playeringame;
    }

    @Override
    public void write(DataOutputStream f)
            throws IOException {
        
        f.writeByte(version);        
        f.writeByte(skill.ordinal()); 
        f.writeByte(episode);
        f.writeByte(map);
        f.writeBoolean(deathmatch);
        f.writeBoolean(respawnparm);
        f.writeBoolean(fastparm);
        f.writeBoolean(nomonsters);
        f.writeByte(consoleplayer);
        DoomIO.writeBoolean(f,this.playeringame,MAXPLAYERS);
        for (IDemoTicCmd i: demorecorder) {            
            i.write(f);
        }
        f.writeByte(DEMOMARKER);
        
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resetDemo() {
        this.p_demo=0;
        
    }
    
    /////////////////////// VARIOUS BORING GETTERS /////////////////////
    
    
	
}
