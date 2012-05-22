
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
	public void rotateAround(Vector axis, double theta) {
		// TODO Auto-generated method stub
		
	}
	public void normalize() {
		// TODO Auto-generated method stub
		
	}
}
