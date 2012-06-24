package com.biu.cg.math3d;

/**
 * when given a vector and an axis of rotation (and an angle theta)
 * this class handles the mathematics behind the rotation. i.e. in
 * order to rotate about an arbitrary axis, you may use this class.
 * @author gilad
 *
 */
public class Rotator {
	
	//"matrix" variables (default initialization - unit matrix)
	private float m00 = 1f;
	private float m01 = 0f;
	private float m02 = 0f;
	private float m10 = 0f;
	private float m11 = 1f;
	private float m12 = 0f;
	private float m20 = 0f;
	private float m21 = 0f;
	private float m22 = 1f;

	/**
	 * set axis and angle for rotation
	 * @param axis
	 * @param theta
	 */
	public void setAxisAndAngle(Vector axis, float theta) {
		float n_x = axis.getX();
		float n_y = axis.getY();
		float n_z = axis.getZ();
		float n2x = n_x * n_x;
		float n2y = n_y * n_y;
		float n2z = n_z * n_z;
		float nxy = n_x * n_y;
		float nxz = n_x * n_z;
		float nyz = n_y * n_z;
		float cos = (float)Math.cos(theta);
		float sin = (float)Math.sin(theta);
		float m1c = 1f - cos;
		
		m00 = n2x * m1c + cos;
		m01 = nxy * m1c + n_z * sin;
		m02 = nxz * m1c - n_y * sin;
		
		m10 = nxy * m1c - n_z * sin;
		m11 = n2y * m1c + cos;
		m12 = nyz * m1c + n_x * sin;
		
		m20 = nxz * m1c + n_y * sin;
		m21 = nyz * m1c - n_x * sin;
		m22 = n2z * m1c + cos;
		
	}

	/**
	 * rotate about the predefined axis the vector v.
	 * @param v
	 */
	public void rotate(Vector v) {
		float x = v.x * m00 + v.y * m01 + v.z * m02;
		float y = v.x * m10 + v.y * m11 + v.z * m12;
		float z = v.x * m20 + v.y * m21 + v.z * m22;
		v.setX(x);
		v.setY(y);
		v.setZ(z);
	}

	/**
	 * static method for rotation
	 * @param v - origin
	 * @param u - to put result of the rotated origin in
	 * @param axis - axis to rotate about
	 * @param angle - the angle of rotation
	 */
	public static void oneTimeRotatation(Vector v, Vector u, Vector axis, float angle) {
		float n_x = axis.getX();
		float n_y = axis.getY();
		float n_z = axis.getZ();
		float n2x = n_x * n_x;
		float n2y = n_y * n_y;
		float n2z = n_z * n_z;
		float nxy = n_x * n_y;
		float nxz = n_x * n_z;
		float nyz = n_y * n_z;
		float cos = (float)Math.cos(angle);
		float sin = (float)Math.sin(angle);
		float m1c = 1f - cos;
		float m00 = n2x * m1c + cos;
		float m01 = nxy * m1c + n_z * sin;
		float m02 = nxz * m1c - n_y * sin;
		float m10 = nxy * m1c - n_z * sin;
		float m11 = n2y * m1c + cos;
		float m12 = nyz * m1c + n_x * sin;
		float m20 = nxz * m1c + n_y * sin;
		float m21 = nyz * m1c - n_x * sin;
		float m22 = n2z * m1c + cos;
		u.setX(v.x * m00 + v.y * m01 + v.z * m02);
		u.setY(v.x * m10 + v.y * m11 + v.z * m12);
		u.setZ(v.x * m20 + v.y * m21 + v.z * m22);
	}
}
