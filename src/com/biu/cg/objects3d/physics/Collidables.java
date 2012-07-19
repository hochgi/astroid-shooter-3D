package com.biu.cg.objects3d.physics;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.object3d.physics.boundingShapes.Dot;

public class Collidables {
	//static fields:
	private static Timer timer;
	protected static Object lock1 = new Object();
	protected static Object lock2 = new Object();
	//the list keeps all the objects to update
	private static LinkedList<Collidable> collidables = new LinkedList<Collidable>();
	private static LinkedList<Collidable> graveYard = new LinkedList<Collidable>();
	private static LinkedList<Collidable> newlyBorn = new LinkedList<Collidable>();

	// this anonymous timer task, handles the updating
	// of every registered object in the objects list
	private static TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			synchronized(lock1) {		
				collidables.addAll(newlyBorn);
				newlyBorn.clear();
			}
			
			if(collidables.isEmpty()) {
				return;
			}
			
			synchronized(lock2) {		
				collidables.removeAll(graveYard);
				graveYard.clear();
			}
			
			
				
			for (int i=0 ; i< collidables.size()-1 ; i++) {
				for (int j=i+1 ; j< collidables.size() ; j++){
					try {
						if(collidables.get(i).getBoundingShape().intersect((BoundingSphere)collidables.get(j).getBoundingShape())){
							collidables.get(i).collisionAction(collidables.get(j));
							collidables.get(j).collisionAction(collidables.get(i));
							//System.out.println(collidables.get(i).getType() + " collided with " + collidables.get(j).getType());
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						if(collidables.get(i).getBoundingShape().intersect((Dot)collidables.get(j).getBoundingShape())){
							collidables.get(i).collisionAction(collidables.get(j));
							collidables.get(j).collisionAction(collidables.get(i));
							//System.out.println(collidables.get(i).getType() + " collided with " + collidables.get(j).getType());
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
				}
					
			}
			
		}
	};
	
	//static initialization that executes only once
	static {
		timer = new Timer("Timer-Collidables");
		timer.scheduleAtFixedRate(task, 0, 40);
	}

	/**
	 * register an object for auto updating
	 * @param obj
	 */
	public static void registerObject(Collidable obj) {
		synchronized (lock1) {
			newlyBorn.addLast(obj);
		}
	}
	
	/**
	 * unregister an object from auto updating
	 * @param obj
	 */
	public static void unregisterObject(Collidable obj) {
		synchronized (lock2) {
			graveYard.add(obj);
		}
	}
	

}
