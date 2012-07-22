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
		this.position = new Vector(0f,0f,0f);
		this.xUnit = new Vector(1f,0f,0f);
		this.yUnit = new Vector(0f,1f,0f);
		this.zUnit = new Vector(0f,0f,1f);
		rotator = new Rotator();
	}
	
	/**
	 * constructor that sets the position
	 * @param position
	 */
	public Orientation(Vector position) {
		this.position = position;
		this.xUnit = new Vector(1f,0f,0f);
		this.yUnit = new Vector(0f,1f,0f);
		this.zUnit = new Vector(0f,0f,1f);
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
		return position.add(zUnit, 1f);
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
	public void translateForward(float step) {
		position = position.add(zUnit, step);
	}

	/**
	 * position <- position - zValue
	 */
	public void translateBackward(float step) {
		position = position.sub(zUnit, step);
	}

	/**
	 * position <- position + xValue
	 */
	public void translateLeftward(float step) {
		position = position.add(xUnit, step);
	}

	/**
	 * position <- position - xValue
	 */
	public void translateRightward(float step) {
		position = position.sub(xUnit, step);
	}

	/**
	 * position <- position + yValue
	 */
	public void translateUpward(float step) {
		position = position.add(yUnit, step);
	}

	/**
	 * position <- position - yValue
	 */
	public void translateDownward(float step) {
		position = position.sub(yUnit, step);
	}

	/**
	 * pitch rotation
	 * @param theta - angle to rotate
	 */
	public void rotatePitch(float theta) {
		rotator.setAxisAndAngle(xUnit, theta);
		rotator.rotate(yUnit);
		rotator.rotate(zUnit);
	}

	/**
	 * heading rotation
	 * @param theta - angle to rotate
	 */
	public void rotateHeading(float theta) {
		rotator.setAxisAndAngle(yUnit, theta);
		rotator.rotate(xUnit);
		rotator.rotate(zUnit);
	}

	/**
	 * roll rotation
	 * @param theta - angle to rotate
	 */
	public void rotateRoll(float theta) {
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
	public void reset(float xPos, float yPos, float zPos,
					  float xUnx, float yUnx, float zUnx, 
					  float xUny, float yUny, float zUny, 
					  float xUnz, float yUnz, float zUnz) {
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

	public Vector[] getOrthogonalQuadAtPosition(Vector pos, float size) {

		Vector[] quad = new Vector[4];
		Vector a = null, b = null;
		Vector pltp = perpendicularLineToPlane(pos); 

		//if camera faces directly to the plane:
		if(pltp.dot(yUnit) == 0f) {
			a = yUnit;
		}
		else {
			a = yUnit.projectionOnPlane(pltp.normalize());
		}
		a = a.normalize();
		b = Vector.cross(pltp.normalize(), a);
		
		quad[0] = pos.add(a.add(b, 1), size);
		quad[1] = pos.add(b.sub(a, 1),size);
		quad[2] = pos.add(a.neg().sub(b, 1),size);
		quad[3] = pos.add(a.sub(b, 1),size);

		return quad;
	}

	public Vector perpendicularLineToPlane(Vector pos) {
		return pos.sub(position, 1);
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public float zedDistanceTo(Vector pos) {
		return zUnit.x*pos.x + zUnit.y*pos.y + zUnit.z*pos.z;
		//return xUnit.z*pos.x + yUnit.z*pos.y + zUnit.z*pos.z;
	}

	public void convertVector(Vector v) {
		float vx = v.x;
		float vy = v.y;
		float vz = v.z;
		
		v.x = xUnit.x*vx + xUnit.y*vy + xUnit.z*vz;
		v.y = yUnit.x*vx + yUnit.y*vy + yUnit.z*vz;
		v.z = zUnit.x*vx + zUnit.y*vy + zUnit.z*vz;
	}
}
