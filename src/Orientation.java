import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


public class Orientation {

	private Vector position;
	private Vector xUnit;
	private Vector yUnit;
	private Vector zUnit;
	private GLAutoDrawable gLDrawable;

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

		//TODO: delete debug printing
		System.out.println("pitch rotation old:");
		System.out.println("y: " + yUnit.toString() + "z: " + zUnit.toString());
		
		yUnit.rotateAroundAndNormalize(xUnit, theta);
		zUnit.rotateAroundAndNormalize(xUnit, theta);

		//TODO: delete debug printing
		System.out.println("pitch rotation new:");
		System.out.println("y: " + yUnit.toString() + "z: " + zUnit.toString());

	}

	public void rotateHeading(double theta) {
		//TODO: delete debug printing
		System.out.println("heading rotation old:");
		System.out.println("x: " + xUnit.toString() + "z: " + zUnit.toString());
		
		xUnit.rotateAroundAndNormalize(yUnit, theta);
		zUnit.rotateAroundAndNormalize(yUnit, theta);
		
		//TODO: delete debug printing
		System.out.println("heading rotation new:");
		System.out.println("x: " + xUnit.toString() + "z: " + zUnit.toString());
	}

	public void rotateRoll(double theta) {
		//TODO: delete debug printing
		System.out.println("roll rotation old:");
		System.out.println("x: " + xUnit.toString() + "y: " + yUnit.toString());
		
		xUnit.rotateAroundAndNormalize(zUnit, theta);
		yUnit.rotateAroundAndNormalize(zUnit, theta);
		
		//TODO: delete debug printing
		System.out.println("roll rotation old:");
		System.out.println("x: " + xUnit.toString() + "y: " + yUnit.toString());
	}
}
