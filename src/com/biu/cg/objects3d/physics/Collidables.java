package com.biu.cg.objects3d.physics;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.biu.cg.object3d.boundingShapes.AABB;
import com.biu.cg.objects3d.Object3D;

public class Collidables {
	//static fields:
	private static Timer timer;
	protected static Object lock = new Object();
	//the list keeps all the objects to update
	private static LinkedList<Collidable> collidables = new LinkedList<Collidable>();

	// this anonymous timer task, handles the updating
	// of every registered object in the objects list
	private static TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			if(collidables.isEmpty()) {
				return;
			}
			synchronized(lock) {
				for (int i=0 ; i< collidables.size()-1 ; i++) {
					for (int j=i+1 ; j< collidables.size() ; j++){
						if(collidables.get(i).getBoundingShape().intersect((AABB)collidables.get(j).getBoundingShape())){
							collidables.get(i).collisionAction(collidables.get(j));
							collidables.get(j).collisionAction(collidables.get(i));
						}
					}
						
				}
			}
		}
	};
	
	//static initialization that executes only once
	static {
		timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 40);
	}

	/**
	 * register an object for auto updating
	 * @param obj
	 */
	public static void registerObject(Collidable obj) {
		synchronized (lock) {
			collidables.add(obj);
		}
	}
}
