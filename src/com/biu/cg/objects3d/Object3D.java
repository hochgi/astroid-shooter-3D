package com.biu.cg.objects3d;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.math3d.Vector2Tuple;

/**
 * this abstract class should be the root class
 * of every element to draw on the GLCanvas.
 * it has a thread especially for updating objects
 * on given intervals (40 ms)
 * @author gilad
 *
 */
public abstract class Object3D {
	//static fields:
	private static Timer timer;
	private static Object nLock = new Object();
	private static Object gLock = new Object();
	//the list keeps all the objects to update
	private static LinkedList<Object3D> objects = new LinkedList<Object3D>();
	private static LinkedList<Object3D> graveyard = new LinkedList<Object3D>();
	private static LinkedList<Object3D> newlyBorn = new LinkedList<Object3D>();

	// this anonymous timer task, handles the updating
	// of every registered object in the objects list
	private static TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			synchronized (gLock) {
				objects.removeAll(graveyard);
				graveyard.clear();
			}
			synchronized (nLock) {
				objects.addAll(newlyBorn);
				newlyBorn.clear();
			}
			if(objects.isEmpty()) {
				return;
			}
			for (Object3D o : objects) {
				o.update();
			}
		}
	};
	
	
	//static initialization that executes only once
	static {
		timer = new Timer("Timer-Object3D");
		timer.scheduleAtFixedRate(task, 0, 40);
	}

	/**
	 * register an object for auto updating
	 * @param obj
	 */
	public static void registerObject(Object3D obj) {
		synchronized (nLock) {
			newlyBorn.add(obj);
		}
	}
	
	public static void unregisterAsteroid(Object3D obj) {
		synchronized (gLock) {
			graveyard.add(obj);
		}
	}
	
	public static LinkedList<Object3D> getObjects() {
		return objects;
	}
	
	//////////////////////////////////////
	//END OF STATIC SECTION OF THE CLASS//
	//----------------------------------//
	//all code from here, is to handle a//
	//single object updating and drawing//
	//////////////////////////////////////
	
	protected Orientation orientation;

	/**
	 * getter method for orientation
	 * @return
	 */
	public Orientation getOrientation() {
		return orientation;
	}
	
	public Vector getPosition() {
		return orientation.getPosition();
	}
	
	/**
	 * constructor
	 * @param axis
	 * @param angle
	 */
	public Object3D() {
		orientation = new Orientation();
	}

	/**
	 * yet another constructor
	 * @param position
	 * @param axis
	 * @param angle
	 */
	public Object3D(Vector position) {
		orientation = new Orientation(position);
	}
	
	public void setPosition(Vector position){
		orientation.setPosition(position);
	}

	/**
	 * invoking draw, will cause synchronized invokation of
	 * synchronizedDraw(). this method is final, and should
	 * not be override (actually, you can't... it is final).
	 * it's synchronized in order to prevent the timer thread
	 * to collide with whatever thread draw was invoked from.   
	 * @param gLDrawable
	 */
	public final void draw(GLAutoDrawable gLDrawable) {
		synchronizedDraw(gLDrawable);
	}
	
	/**
	 * override this method to allow drawing of the object.
	 * method will be invoked (synchronously) whenever draw
	 * is invoked.
	 * @param gLDrawable
	 */
	protected abstract void synchronizedDraw(GLAutoDrawable gLDrawable);
	
	/**
	 * in case you want a sequential update
	 * for your class at given intervals, 
	 * implement the specific update as this method.
	 * it will be invoked by a timer task that runs
	 * every 40 ms.
	 */
	protected abstract void update();
	
	public void paintOnMinimap(Graphics g){}
	
	public Vector getPositionOnMinimap(){
		Vector v = new Vector();
		v.x = getPosition().x / 10+10;
		v.y = getPosition().z / 10 + 50;
		return v;
	}
}
