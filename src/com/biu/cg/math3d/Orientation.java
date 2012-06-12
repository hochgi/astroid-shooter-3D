package com.biu.cg.math3d;

/**
 * this class handles the position,
 * rotation, etc'... of "something"
 * @author gilad
 *
 */
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
		rotator.setAxisAndAngle(xUnit, theta);
		rotator.rotate(yUnit);
		rotator.rotate(zUnit);
	}

	/**
	 * heading rotation
	 * @param theta - angle to rotate
	 */
	public void rotateHeading(double theta) {
		rotator.setAxisAndAngle(yUnit, theta);
		rotator.rotate(xUnit);
		rotator.rotate(zUnit);
	}

	/**
	 * roll rotation
	 * @param theta - angle to rotate
	 */
	public void rotateRoll(double theta) {
		rotator.setAxisAndAngle(zUnit, theta);
		rotator.rotate(xUnit);
		rotator.rotate(yUnit);
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

	public Vector[] getOrthogonalQuadAtPosition(Vector pos, double size) {

		Vector[] quad = new Vector[4];
		Vector a = null, b = null;
		Vector pltp = perpendicularLineToPlane(pos); 

		//if camera faces directly to the plane:
		if(pltp.dot(yUnit) == 0.0) {
			a = yUnit;
		}
		else {
			a = yUnit.projectionOnPlane(pltp.normalize());
		}
		a = a.normalize();
		b = Vector.cross(pltp.normalize(), a);
		
		quad[0] = a.add(b, 1).mul(size);
		quad[1] = b.sub(a, 1).mul(size);
		quad[2] = a.neg().sub(b, 1).mul(size);
		quad[3] = a.sub(b, 1).mul(size);

		return quad;
	}

	public Vector perpendicularLineToPlane(Vector pos) {
		return pos.sub(position, 1);
	}

	public void setPosition(Vector position) {
		this.position = position;
	}
}
