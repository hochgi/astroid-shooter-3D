import javax.vecmath.*;
import javax.media.j3d.Transform3D;

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
		//oposite as in OpenGL, angle is radians here
		AxisAngle4d rotateAxisAngle = new AxisAngle4d(theta, axis.getX(), axis.getY(), axis.getZ());
		
		Matrix3d m = new Matrix3d();
		
		m.set(rotateAxisAngle);
		
		Vector3d v = new Vector3d(x, y, z);
		
		m.transform(v);
		
		v.normalize();
		
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+","+z+")";
	}
}
