import javax.vecmath.*;

public class Vector {
	double x;
	double y;
	double z;
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public Vector add(Vector v) {
		return new Vector(x+v.getX(),y+v.getY(),z+v.getZ());
	}
	public Vector sub(Vector v) {
		return new Vector(x-v.getX(),y-v.getY(),z-v.getZ());
	}
	public void rotateAroundAndNormalize(Vector axis, double theta) {
		
		double[] rotationMatrixValues = Vector.generateRotationArray(axis, theta);
		Matrix4d m = new Matrix4d(rotationMatrixValues);
		Point3d p = new Point3d(x,y,z);
		m.transform(p);
		x = p.x;
		y = p.y;
		z = p.z;
	}
	
	private static double[] generateRotationArray(Vector axis, double theta) {
		
		double[] m = new double[16];
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
		
		return m;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+","+z+")";
	}
}
