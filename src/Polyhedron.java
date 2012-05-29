
public abstract class Polyhedron extends Object3D {
	
	protected Polyhedron(Vector axis, double angle) {
		super(axis, angle);
	}

	protected Polyhedron(Vector position, Vector axis, double angle) {
		super(position,axis,angle);
		orientation = new Orientation(position);
		orientation.setFixedRotation(axis, angle);
	}

	protected Orientation getOrientation() {
		return orientation;
	}
	
	@Override
	protected void update() {
		Vector2Tuple[] vertices = getVertices();
		for (Vector2Tuple t : vertices) {
			orientation.rotateAtPredefinedAxisAndAngle(t.v,t.u);
		}
	};
	
	protected abstract Vector2Tuple[] getVertices();
}
