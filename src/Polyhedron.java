
/**
 * class to serve as base class for polyhedrons
 * (such as cubes or tetrahedrons)
 * @author gilad
 *
 */
public abstract class Polyhedron extends Object3D {
	
	/**
	 * setter constructor
	 * @param axis
	 * @param angle
	 */
	protected Polyhedron(Vector axis, double angle) {
		super(axis, angle);
	}

	/**
	 * yet another setter constructor
	 * @param position
	 * @param axis
	 * @param angle
	 */
	protected Polyhedron(Vector position, Vector axis, double angle) {
		super(position,axis,angle);
		orientation = new Orientation(position);
		orientation.setFixedRotation(axis, angle);
	}
	
	/**
	 * inherited from Object3D - updates the vertices
	 */
	//TODO: method should change a bit once we change orientation class
	@Override
	protected void update() {
		Vector2Tuple[] vertices = getVertices();
		for (Vector2Tuple t : vertices) {
			orientation.rotateAtPredefinedAxisAndAngle(t.v,t.u);
		}
	};
	
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
