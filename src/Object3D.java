import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.opengl.GLAutoDrawable;

/**
 * this abstract class should be the root class
 * of every element to draw on the GLCanvas.
 * it has a thread especially for updating objects
 * on given intervals (40 ms)
 * @author gilad
 *
 */
//TODO: unregister objects:
//		in case we would want to dispose stuff...
//TODO:	make a subclass "Model" like "Polyhedron"
public abstract class Object3D {
	//static fields:
	private static Timer timer;
	private static Object lock = new Object();
	//the list keeps all the objects to update
	private static LinkedList<Object3D> objects = new LinkedList<Object3D>();

	// this anonymous timer task, handles the updating
	// of every registered object in the objects list
	private static TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			if(objects.isEmpty()) {
				return;
			}
			synchronized(lock) {
				for (Object3D o : objects) {
					o.update();
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
	public static void registerObject(Object3D obj) {
		objects.add(obj);
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

	/**
	 * invoking draw, will cause synchronized invokation of
	 * synchronizedDraw(). this method is final, and should
	 * not be override (actually, you can't... it is final).
	 * it's synchronized in order to prevent the timer thread
	 * to collide with whatever thread draw was invoked from.   
	 * @param gLDrawable
	 */
	public final void draw(GLAutoDrawable gLDrawable) {
		synchronized (lock) {
			synchronizedDraw(gLDrawable);
		}
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

}
