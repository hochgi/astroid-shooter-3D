package com.biu.cg.sound;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.*;


public class SoundPlayer {
	
	private static void playSound(String path){
		try{ 
			AudioPlayer p = AudioPlayer.player;
			
			AudioStream as = new AudioStream(new FileInputStream(path));
			p.start(as);

			}

			catch(IOException IOE){
				IOE.printStackTrace();
			}
	}
	
	public static void photonShot(){
		playSound("sounds/photon.wav");
	}
	
	public static void rocketShot(){
		playSound("sounds/rocket.wav");
	}
	
	public static void explosion(){
		playSound("sounds/explosion-01.wav");
	}
	
	
}
