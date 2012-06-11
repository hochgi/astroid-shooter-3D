
/**
 * this class handles the position,
 * rotation, etc'... of "something"
 * @author gilad
 *
 */
//TODO: this could be done more elegantly...
//		rotators should not be included here
//		it's better to have a rotator method
//		that takes on orientation object,and
//		changes it.the rotators should be in
//		an objects directly, and not through
//		orientation objects.
public class Orientation {

	private Vector position;
	private Vector xUnit;
	private Vector yUnit;
	private Vector zUnit;
	private Rotator rotator;

	/**
	 * constructor (default)
	 */
	public Orientation() {
		this.position = new Vector(0.0,0.0,0.0);
		this.xUnit = new Vector(1.0,0.0,0.0);
		this.yUnit = new Vector(0.0,1.0,0.0);
		this.zUnit = new Vector(0.0,0.0,1.0);
		rotator = new Rotator();
	}
	
	/**
	 * constructor that sets the position
	 * @param position
	 */
	public Orientation(Vector position) {
		this.position = position;
		this.xUnit = new Vector(1.0,0.0,0.0);
		this.yUnit = new Vector(0.0,1.0,0.0);
		this.zUnit = new Vector(0.0,0.0,1.0);
		rotator = new Rotator();
	}
	
	/**
	 * full setter constructor
	 * @param position
	 * @param xUnit
	 * @param yUnit
	 * @param zUnit
	 */
	public Orientation(Vector position, Vector xUnit, Vector yUnit, Vector zUnit) {
		this.position = position;
		this.xUnit = xUnit;
		this.yUnit = yUnit;
		this.zUnit = zUnit;
		rotator = new Rotator();
	}

	/**
	 * returns the position
	 * @return
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * returns the Z axis
	 * @return
	 */
	public Vector getTargetLookAtVector() {
		return position.add(zUnit, 1.0);
	}

	/**
	 * returns the Y axis
	 * @return
	 */
	public Vector getUpVector() {
		return yUnit;
	}

	/**
	 * position <- position + zValue
	 */
	public void translateForward(double step) {
		position = position.add(zUnit, step);
	}

	/**
	 * position <- position - zValue
	 */
	public void translateBackward(double step) {
		position = position.sub(zUnit, step);
	}

	/**
	 * position <- position + xValue
	 */
	public void translateLeftward(double step) {
		position = position.add(xUnit, step);
	}

	/**
	 * position <- position - xValue
	 */
	public void translateRightward(double step) {
		position = position.sub(xUnit, step);
	}

	/**
	 * position <- position + yValue
	 */
	public void translateUpward(double step) {
		position = position.add(yUnit, step);
	}

	/**
	 * position <- position - yValue
	 */
	public void translateDownward(double step) {
		position = position.sub(yUnit, step);
	}

	/**
	 * pitch rotation
	 * @param theta - angle to rotate
	 */
	public void rotatePitch(double theta) {
		yUnit.rotateAroundAndNormalize(xUnit, theta);
		zUnit.rotateAroundAndNormalize(xUnit, theta);
	}

	/**
	 * heading rotation
	 * @param theta - angle to rotate
	 */
	public void rotateHeading(double theta) {
		xUnit.rotateAroundAndNormalize(yUnit, theta);
		zUnit.rotateAroundAndNormalize(yUnit, theta);
	}

	/**
	 * roll rotation
	 * @param theta - angle to rotate
	 */
	public void rotateRoll(double theta) {
		xUnit.rotateAroundAndNormalize(zUnit, theta);
		yUnit.rotateAroundAndNormalize(zUnit, theta);
	}

	public Vector getAxis(char c) {
		Vector rv = null;
		switch (c) {
		case 'X':
		case 'x':
			rv = xUnit;
			break;
		case 'Y':
		case 'y':
			rv = yUnit;
			break;
		case 'Z':
		case 'z':
			rv = zUnit;
			break;
		default:
			//SHOULD'NT GET IN HERE
			break;
		}
		return rv;
	}

	/**
	 * reset orientation to the given coordinates
	 * @param xPos - x coordinate of the position
	 * @param yPos - y coordinate of the position
	 * @param zPos - z coordinate of the position
	 * @param xUnx - x coordinate of the 'X' axis
	 * @param yUnx - y coordinate of the 'X' axis
	 * @param zUnx - z coordinate of the 'X' axis
	 * @param xUny - x coordinate of the 'Y' axis
	 * @param yUny - y coordinate of the 'Y' axis
	 * @param zUny - z coordinate of the 'Y' axis
	 * @param xUnz - x coordinate of the 'Z' axis
	 * @param yUnz - y coordinate of the 'Z' axis
	 * @param zUnz - z coordinate of the 'Z' axis
	 */
	public void reset(double xPos, double yPos, double zPos,
					  double xUnx, double yUnx, double zUnx, 
					  double xUny, double yUny, double zUny, 
					  double xUnz, double yUnz, double zUnz) {
		position.x = xPos;
		position.y = yPos;
		position.z = zPos;
		xUnit.x = xUnx;
		xUnit.y = yUnx;
		xUnit.z = zUnx;
		yUnit.x = xUny;
		yUnit.y = yUny;
		yUnit.z = zUny;
		zUnit.x = xUnz;
		zUnit.y = yUnz;
		zUnit.z = zUnz;
	}
}
