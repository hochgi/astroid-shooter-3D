import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.opengl.GLAutoDrawable;


public abstract class Polyhedron {
	protected Orientation orientation;
	
	private static Timer timer;
	private static Object lock = new Object();
	private static LinkedList<Polyhedron> polyhedrons = new LinkedList<Polyhedron>();
	private static TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			if(polyhedrons.isEmpty()) {
				return;
			}
			synchronized(lock) {
				for (Polyhedron p : polyhedrons) {
					Vector2Tuple[] vertices = p.getVertices();
					for(Vector2Tuple t : vertices) {
						p.rotateAtPredefinedAxisAndAngle(t.v, t.u);
					}	
				}
			}
		}
	};
	
	static {
		timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 40);
	}
	
	protected Polyhedron(Vector axis, double angle) {
		orientation = new Orientation();
		orientation.setFixedRotation(axis, angle);
	}
	
	protected void rotateAtPredefinedAxisAndAngle(Vector v, Vector u) {
		orientation.rotateAtPredefinedAxisAndAngle(v, u);
	}

	protected Polyhedron(Vector position, Vector axis, double angle) {
		orientation = new Orientation(position);
		orientation.setFixedRotation(axis, angle);
	}

	protected Orientation getOrientation() {
		return orientation;
	}
	
	public static void registerPolyhedron(Polyhedron polyhedron) {
		polyhedrons.add(polyhedron);
	}
	
	public void draw(GLAutoDrawable gLDrawable) {
		synchronized (lock) {
			synchronizedDraw(gLDrawable);
		}
	}
	
	protected abstract void synchronizedDraw(GLAutoDrawable gLDrawable);
	
	protected abstract Vector2Tuple[] getVertices();
}
