
public class Vector {
	double x;
	double y;
	double z;
	double speed;
	
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector() {
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
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
	
	@Override
	public String toString() {
		return "("+x+","+y+","+z+")";
	}
	public Vector normalize() {
		double length = Math.sqrt(x*x + y*y + z*z);
		if(length != 1.0) {
			x /= length;
			y /= length;
			z /= length;
		}
		return this;
	}
}
