
/**
 * class to serve as base class for polyhedrons
 * (such as cubes or tetrahedrons)
 * @author gilad
 *
 */
public abstract class Polyhedron extends Object3D {
	
	private Vector fixedAxis;
	private double fixedAngle = 0.0;
	private double accumulatedAngle;

	/**
	 * yet another setter constructor
	 * @param position
	 * @param axis
	 * @param angle
	 */
	protected Polyhedron(Vector position, Vector axis, double angle) {
		super(position);
		orientation = new Orientation(position);
		setFixedRotation(axis, angle);
	}
	
	private void setFixedRotation(Vector axis, double angle) {
		while(angle < 0) {
			angle += (Math.PI * 2);
		}
		while(angle >= (Math.PI * 2)) {
			angle -= (Math.PI * 2);
    	}
		accumulatedAngle = fixedAngle  = angle;
		fixedAxis = axis;
	}
	
	/**
	 * inherited from Object3D - updates the vertices
	 */
	//TODO: method should change a bit once we change orientation class
	@Override
	protected void update() {
		Vector2Tuple[] vertices = getVertices();
		for (Vector2Tuple t : vertices) {
			rotateAtPredefinedAxisAndAngle(t.v,t.u);
		}
	}
	
	/**
	 * reverse rotation
	 */
	//TODO: also don't belong here
	public void reverseRotation() {
		fixedAngle = (Math.PI * 2) - fixedAngle;
	}
	
	/**
	 * simple setter (fixed angle for fixed rotation)
	 * @param d - angle
	 */
	//TODO: same as above.
	public void setAngle(double d) {
		fixedAngle = d;
	}
	
	/**
	 * an updater method. rotates a vector.
	 * @param v - original vector
	 * @param u - vector to store results in
	 */
	//TODO: same as above, this logic does not belong here
	public void rotateAtPredefinedAxisAndAngle(Vector v, Vector u) {
		if(accumulatedAngle >= (Math.PI * 2)) {
			accumulatedAngle -= (Math.PI * 2);
    	}
		Rotator.oneTimeRotatation(v,u,fixedAxis,accumulatedAngle);
		accumulatedAngle += fixedAngle;
	}
	
	/**
	 * a polyhedron is composed of polygons,
	 * so it is defind by it's vertices and,
	 * this method should retrieve all the
	 * vertices of the polyhedron, as a tuple.
	 * first argument in the tuple should be original
	 * point, and the second is for update to put
	 * results in. (it is done for efficiency,
	 * to prevent a hell of a lot allocations...) 
	 * @return
	 */
	protected abstract Vector2Tuple[] getVertices();
}
