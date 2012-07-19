package com.biu.cg.objects3d.asteroids;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.planets.Earth;
import com.biu.cg.objects3d.Object3D;
import com.biu.cg.objects3d.physics.Collidables;

public class Asteroids {
	
	private static Random rand = new Random();
	private static Earth earth=null;
	private static Orientation camera=null;
	
	private static Timer timer;
	protected static Object lock = new Object();
	private static LinkedList<Asteroid> asteroids = new LinkedList<Asteroid>();
	
	
	public static void setEarth(Earth earth) {
		Asteroids.earth = earth;
	}
	
	public static void setCamera(Orientation camera) {
		Asteroids.camera = camera;
	}
	
	
	public static LinkedList<Asteroid> getAsteroids() {
		synchronized (lock) {
			return (LinkedList<Asteroid>) asteroids.clone();
		}
		
	}
	
	/**
	 * register asteroid.
	 * @param obj
	 */
	public static void registerAsteroid(Asteroid obj) {
		synchronized (lock) {
			asteroids.addLast(obj);
		}
	}
	
	/**
	 * unregister an asteroid
	 * @param obj
	 */
	public static void unregisterAsteroid(Asteroid obj) {
		synchronized (lock) {
			asteroids.remove(obj);
		}
	}
	
	
	// this anonymous timer task, handles the updating
	// of every registered object in the objects list
	private static TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			if(earth==null || camera==null) {
				return;
			}
			
			Asteroid a=null;
			if(rand.nextInt(3)==0)
				a = new BigAsteroid(earth, camera , new Vector(-800f , (rand.nextInt(1000) - 500) , (rand.nextInt(1000) - 500)));
			else
				a = new SmallAsteroid(earth, camera , new Vector(-800f , (rand.nextInt(1000) - 500) , (rand.nextInt(1000) - 500)));
			
			registerAsteroid(a);
			Collidables.registerObject(a);
			Object3D.registerObject(a);
		}
	};
	
	//static initialization that executes only once
	static {
		timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 10000);
	}
}
