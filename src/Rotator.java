import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;


public class Rotator {
	
	double[] m = new double[16];
	Matrix4d matrix = new Matrix4d();

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
		
		m[0] = n2x * m1c + cos;
		m[1] = nxy * m1c + n_z * sin;
		m[2] = nxz * m1c - n_y * sin;
		m[3] = 0;
		
		m[4] = nxy * m1c - n_z * sin;
		m[5] = n2y * m1c + cos;
		m[6] = nyz * m1c + n_x * sin;
		m[7] = 0;
		
		m[8] = nxz * m1c + n_y * sin;
		m[9] = nyz * m1c - n_x * sin;
		m[10]= n2z * m1c + cos;
		m[11]= 0;
		
		m[12]= 0;
		m[13]= 0;
		m[14]= 0;
		m[15]= 1;
	}

	public void rotate(Vector v) {
		matrix.set(m);
		Point3d p = new Point3d(v.getX(), v.getY(), v.getZ());
		matrix.transform(p);
		v.setX(p.x);
		v.setY(p.y);
		v.setZ(p.z);
	}
}
