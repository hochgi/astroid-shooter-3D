
public class Orientation {

	private Vector position;
	private Vector xUnit;
	private Vector yUnit;
	private Vector zUnit;

	public Orientation(Vector position, Vector xUnit, Vector yUnit, Vector zUnit) {
		this.position = position;
		this.xUnit = xUnit;
		this.yUnit = yUnit;
		this.zUnit = zUnit;
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
		position = position.sub(xUnit);
	}

	public void translateRightward() {
		position = position.add(xUnit);
	}

	public void translateUpward() {
		position = position.add(yUnit);
	}

	public void translateDownward() {
		position = position.sub(yUnit);
	}

	public void rotatePitch(double theta) {
		yUnit.rotateAround(xUnit, theta);
		zUnit.rotateAround(xUnit, theta);
		yUnit.normalize();
		zUnit.normalize();
	}

	public void rotateHeading(double theta) {
		xUnit.rotateAround(yUnit, theta);
		zUnit.rotateAround(yUnit, theta);
		xUnit.normalize();
		zUnit.normalize();
	}

	public void rotateRoll(double theta) {
		xUnit.rotateAround(zUnit, theta);
		yUnit.rotateAround(zUnit, theta);
		xUnit.normalize();
		yUnit.normalize();
	}

}
