package com.biu.cg.objects3d.asteroids;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.planets.Earth;
import com.biu.cg.objects3d.Object3D;

/**
 * Asteroids - manages the asteroids.
 * @author Irzh
 *
 */
public class Asteroids {
	
	private static Random rand = new Random();
	private static Earth earth=null;
	private static Orientation camera=null;
	
	private static Timer timer;
	private static final Object nLock = new Object();
	private static final Object gLock = new Object();
	private static final Object aLock = new Object();
	private static LinkedList<Asteroid> asteroids = new LinkedList<Asteroid>();
	private static LinkedList<Asteroid> newlyBorn = new LinkedList<Asteroid>();
	private static LinkedList<Asteroid> graveyard = new LinkedList<Asteroid>();
	
	private static int creationCounter=0;
	private static final int CREATION_DELAY = 200; //250
	
	/**
	 * set Earth.
	 * @param earth
	 */
	public static void setEarth(Earth earth) {
		Asteroids.earth = earth;
	}
	
	/**
	 * set the camera.
	 * @param camera
	 */
	public static void setCamera(Orientation camera) {
		Asteroids.camera = camera;
	}
	
	/**
	 * get active asteroids.
	 * @return
	 */
	public static LinkedList<Asteroid> getAsteroids() {
		return asteroids;
	}
	
	/**
	 * register asteroid.
	 * @param obj
	 */
	public static void registerAsteroid(Asteroid obj) {
		synchronized (nLock) {
			newlyBorn.add(obj);
		}
	}
	
	/**
	 * unregister an asteroid
	 * @param obj
	 */
	public static void unregisterAsteroid(Asteroid obj) {
		synchronized (gLock) {
			graveyard.add(obj);
		}
	}
	
	
	// this anonymous timer task, handles the updating
	// of every registered object in the objects list
	private static TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			
			// remove all the asteroids from the grave yard (the asteroids that were destroyed).
			synchronized (gLock) {
				synchronized (aLock) {
					asteroids.removeAll(graveyard);
					graveyard.clear();
				}
			}
			
			// add all newly born asteroids the the asteroid list. 
			synchronized (nLock) {
				synchronized (aLock) {
					asteroids.addAll(newlyBorn);
					newlyBorn.clear();
					
				}
			}
			
			
			if(earth==null || camera==null) {
				return;
			}
			
			// create a new asteroid according to the creation counter.
			creationCounter = (creationCounter + 1) % CREATION_DELAY;
			if(creationCounter==0){
				Asteroid a=null;
				if(rand.nextInt(5)==0) // will create either a small asteroid or a big one.
					a = new BigAsteroid(earth, camera , new Vector(-800f , (rand.nextInt(1000) - 500) , (rand.nextInt(1000) - 500)));
				else
					a = new SmallAsteroid(earth, camera , new Vector(-800f , (rand.nextInt(1000) - 500) , (rand.nextInt(1000) - 500)));
				
				registerAsteroid(a);
				Object3D.registerObject(a);
			}
		}
	};
	
	//static initialization that executes only once
	static {
		timer = new Timer("Timer-Asteroids");
		timer.scheduleAtFixedRate(task, 0, 40);
	}

	/**
	 * draws the asteroids.
	 * @param gLDrawable
	 */
	public static void drawAsteroids(GLAutoDrawable gLDrawable) {
		synchronized (aLock) {
			for (Asteroid a : asteroids) {
				a.draw(gLDrawable);
			}
		}
	}
}
