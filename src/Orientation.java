
public class Orientation {

	private Vector position;
	private Vector xUnit;
	private Vector yUnit;
	private Vector zUnit;
	private Rotator rotator;
	private Rotator fixedRotator = null;
	private double fixedAngle = 0.0;
	private double accumulatedAngle;
	private Vector fixedAxis;

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
		while(angle < 0) {
			angle += (Math.PI * 2);
		}
		while(angle >= (Math.PI * 2)) {
			angle -= (Math.PI * 2);
    	}
		accumulatedAngle = fixedAngle  = angle;
		fixedAxis = axis;
		fixedRotator = new Rotator();
		fixedRotator.setAxisAndAngle(axis, angle);
	}

	public void rotateAtPredefinedAxisAndAngle(Vector v, Vector u) {
		if(accumulatedAngle >= (Math.PI * 2)) {
			accumulatedAngle -= (Math.PI * 2);
    	}
		fixedRotator.oneTimeRotatation(v,u,fixedAxis,accumulatedAngle);
		accumulatedAngle += fixedAngle;
	}

	public void reverseRotation() {
		fixedAngle = (Math.PI * 2) - fixedAngle;
	}

	public void setAngle(double d) {
		fixedAngle = d;
	}

	public Vector getAxis(char c) {
		Vector rv = null;
		switch (c) {
		case 'X':
			rv = xUnit;
			break;
		case 'Y':
			rv = yUnit;
			break;
		case 'Z':
			rv = zUnit;
			break;
		default:
			//SHOULD'NT GET IN HERE
			break;
		}
		return rv;
	}

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
