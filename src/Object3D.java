import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.GLAutoDrawable;


public abstract class Object3D {
	private static Timer timer;
	private static Object lock = new Object();
	private static LinkedList<Object3D> objects = new LinkedList<Object3D>();
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
	
	static {
		timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 40);
	}

	public static void registerObject(Object3D obj) {
		objects.add(obj);
	}
	
	//////////////////////////////////////
	//END OF STATIC SECTION OF THE CLASS//
	//////////////////////////////////////
	
	protected Orientation orientation;

	public Object3D(Vector axis, double angle) {
		orientation = new Orientation();
		orientation.setFixedRotation(axis, angle);
	}

	public Object3D(Vector position, Vector axis, double angle) {
		orientation = new Orientation(position);
		orientation.setFixedRotation(axis, angle);
	}

	public void synchronizedDraw(GLAutoDrawable gLDrawable) {
		synchronized (lock) {
			synchronizedDraw(gLDrawable);
		}
	}
	
	protected abstract void draw(GLAutoDrawable gLDrawable);
	protected abstract void update();

}
