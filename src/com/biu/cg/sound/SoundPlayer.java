package com.biu.cg.sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundPlayer {
	
	private static Clip photon;
	private static Clip rocket;
	private static Clip kaboom;
	private static AudioInputStream photonAudio;
	private static AudioInputStream rocketAudio;
	private static AudioInputStream kaboomAudio;
	
	static{
		try {
			photonAudio = AudioSystem.getAudioInputStream(new File("sounds/photon.wav"));
			rocketAudio = AudioSystem.getAudioInputStream(new File("sounds/rocket.wav"));
			kaboomAudio = AudioSystem.getAudioInputStream(new File("sounds/kaboom.wav"));
			photon = (Clip)AudioSystem.getLine(new DataLine.Info(Clip.class, photonAudio.getFormat()));
			rocket = (Clip)AudioSystem.getLine(new DataLine.Info(Clip.class, rocketAudio.getFormat()));
			kaboom = (Clip)AudioSystem.getLine(new DataLine.Info(Clip.class, kaboomAudio.getFormat()));
			photon.open(photonAudio);
			rocket.open(rocketAudio);
			kaboom.open(kaboomAudio);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public static void photonShot(){
		photon.stop();
		photon.setFramePosition(0);
		photon.start();
	}
	
	public static void rocketShot(){
		rocket.stop();
		rocket.setFramePosition(0);
		rocket.start();
	}
	
	public static void explosion(){
		kaboom.stop();
		kaboom.setFramePosition(0);
		kaboom.start();
	}
	
	
}
