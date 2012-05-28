
public class Rotator {
	
	private double m00 = 1.0;
	private double m01 = 0.0;
	private double m02 = 0.0;
	private double m10 = 0.0;
	private double m11 = 1.0;
	private double m12 = 0.0;
	private double m20 = 0.0;
	private double m21 = 0.0;
	private double m22 = 1.0;

	public void setAxisAndAngle(Vector axis, double theta) {
		double n_x = axis.getX();
		double n_y = axis.getY();
		double n_z = axis.getZ();
		double n2x = n_x * n_x;
		double n2y = n_y * n_y;
		double n2z = n_z * n_z;
		double nxy = n_x * n_y;
		double nxz = n_x * n_z;
		double nyz = n_y * n_z;
		double cos = Math.cos(theta);
		double sin = Math.sin(theta);
		double m1c = 1.0 - cos;
		
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

	public void rotate(Vector v) {
		double x = v.x * m00 + v.y * m01 + v.z * m02;
		double y = v.x * m10 + v.y * m11 + v.z * m12;
		double z = v.x * m20 + v.y * m21 + v.z * m22;
		v.setX(x);
		v.setY(y);
		v.setZ(z);
	}

	/**
	 * 
	 * @param v - origin
	 * @param u - to put result of the rotated origin in
	 * @param axis - axis to rotate about
	 * @param angle - the angle of rotation
	 */
	public void oneTimeRotatation(Vector v, Vector u, Vector axis, double angle) {
		double n_x = axis.getX();
		double n_y = axis.getY();
		double n_z = axis.getZ();
		double n2x = n_x * n_x;
		double n2y = n_y * n_y;
		double n2z = n_z * n_z;
		double nxy = n_x * n_y;
		double nxz = n_x * n_z;
		double nyz = n_y * n_z;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double m1c = 1.0 - cos;
		double m00 = n2x * m1c + cos;
		double m01 = nxy * m1c + n_z * sin;
		double m02 = nxz * m1c - n_y * sin;
		double m10 = nxy * m1c - n_z * sin;
		double m11 = n2y * m1c + cos;
		double m12 = nyz * m1c + n_x * sin;
		double m20 = nxz * m1c + n_y * sin;
		double m21 = nyz * m1c - n_x * sin;
		double m22 = n2z * m1c + cos;
		u.setX(v.x * m00 + v.y * m01 + v.z * m02);
		u.setY(v.x * m10 + v.y * m11 + v.z * m12);
		u.setZ(v.x * m20 + v.y * m21 + v.z * m22);
	}
}
