import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;


public class Rotator {
	
	Matrix4d m = new Matrix4d();

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
		
		m.m00 = n2x * m1c + cos;
		m.m01 = nxy * m1c + n_z * sin;
		m.m02 = nxz * m1c - n_y * sin;
		m.m03 = 0;
		
		m.m10 = nxy * m1c - n_z * sin;
		m.m11 = n2y * m1c + cos;
		m.m12 = nyz * m1c + n_x * sin;
		m.m13 = 0;
		
		m.m20 = nxz * m1c + n_y * sin;
		m.m21 = nyz * m1c - n_x * sin;
		m.m22 = n2z * m1c + cos;
		m.m23 = 0;
		
		m.m30 = 0;
		m.m31 = 0;
		m.m32 = 0;
		m.m33 = 1;
	}

	public void rotate(Vector v) {
		Point3d p = new Point3d(v.getX(), v.getY(), v.getZ());
		m.transform(p);
		v.setX(p.x);
		v.setY(p.y);
		v.setZ(p.z);
	}
}
