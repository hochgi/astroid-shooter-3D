package com.biu.cg.objects3d.particles;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Object3D;

public abstract class Particle extends Object3D {

	//static fields:
	private static Timer timer;
	private static Object pLock = new Object();
	//the list keeps all the objects to update
	private static LinkedList<Particle> newlyBorn = new LinkedList<Particle>();
	private static LinkedList<Particle> particles = new LinkedList<Particle>();
	private static LinkedList<Particle> graveyard = new LinkedList<Particle>();
	
	private static TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			particles.removeAll(graveyard);
			graveyard.clear();
			synchronized(pLock) {
				particles.addAll(newlyBorn);
				newlyBorn.clear();
			}
			if(particles.isEmpty()) {
				return;
			}
			for (Particle p : particles) {
				if(p.isDead()) {
					graveyard.add(p);
				}
				else{
					p.update();
				}
			}
		}
	};
	
	static {
		timer = new Timer("Timer-Particle");
		timer.scheduleAtFixedRate(task, 0, 40);
	}
	
	/**
	 * register an object for auto updating
	 * @param obj
	 */
	public static void registerObject(Particle p) {
		synchronized (pLock) {
			newlyBorn.add(p);
		}
	}
	
	public Particle(Vector position) {
		super(position);
	}

	/**
	 * method is meant for external updaters to know if particle is updateable,
	 * or should it be dumped...
	 * @return
	 */
	public abstract boolean isDead();
}
