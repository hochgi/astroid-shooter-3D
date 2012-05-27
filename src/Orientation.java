
public class Orientation {

	private Vector position;
	private Vector xUnit;
	private Vector yUnit;
	private Vector zUnit;
	private Rotator rotator;
	private Rotator fixedRotator = null;
	private double fixedAngle = 0.0;

	public Orientation() {
		this.position = new Vector(0.0,0.0,0.0);
		this.xUnit = new Vector(1.0,0.0,0.0);
		this.yUnit = new Vector(0.0,1.0,0.0);
		this.zUnit = new Vector(0.0,0.0,1.0);
		rotator = new Rotator();
	}
	
	public Orientation(Vector position) {
		this.position = position;
		this.xUnit = new Vector(1.0,0.0,0.0);
		this.yUnit = new Vector(0.0,1.0,0.0);
		this.zUnit = new Vector(0.0,0.0,1.0);
		rotator = new Rotator();
	}
	
	public Orientation(Vector position, Vector xUnit, Vector yUnit, Vector zUnit) {
		this.position = position;
		this.xUnit = xUnit;
		this.yUnit = yUnit;
		this.zUnit = zUnit;
		rotator = new Rotator();
	}

	public Vector getPosition() {
		return position;
	}

	public Vector getTargetLookAtVector() {
		return position.add(zUnit);
	}

	public Vector getUpVector() {
		return yUnit;
	}

	public void translateForward() {
		position = position.add(zUnit);
	}

	public void translateBackward() {
		position = position.sub(zUnit);
	}

	public void translateLeftward() {
		position = position.add(xUnit);
	}

	public void translateRightward() {
		position = position.sub(xUnit);
	}

	public void translateUpward() {
		position = position.add(yUnit);
	}

	public void translateDownward() {
		position = position.sub(yUnit);
	}

	public void rotatePitch(double theta) {
		yUnit.rotateAroundAndNormalize(xUnit, theta);
		zUnit.rotateAroundAndNormalize(xUnit, theta);
	}

	public void rotateHeading(double theta) {
		xUnit.rotateAroundAndNormalize(yUnit, theta);
		zUnit.rotateAroundAndNormalize(yUnit, theta);
	}

	public void rotateRoll(double theta) {
		xUnit.rotateAroundAndNormalize(zUnit, theta);
		yUnit.rotateAroundAndNormalize(zUnit, theta);
	}

	public void setFixedRotation(Vector axis, double angle) {
		//fixedAngle  = angle;
		fixedRotator = new Rotator();
		fixedRotator.setAxisAndAngle(axis, angle);
	}

	public void rotateAtPredefinedAxisAndAngle(Vector v) {
		fixedRotator.rotate(v);
	}
}
