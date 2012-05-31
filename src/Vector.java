
/**
 * a simple Vector class
 * @author gilad
 *
 */
public class Vector {
	double x;
	double y;
	double z;
	
	/**
	 * setter constructor
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * default constructor
	 */
	public Vector() {
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
	}
	
	//getters & setters:
	
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
	
	/**
	 * add a vector to self
	 * @param v - vector
	 * @return
	 */
	public Vector add(Vector v) {
		return new Vector(x+v.getX(),y+v.getY(),z+v.getZ());
	}
	/**
	 * substruct a vector from self
	 * @param v - vector
	 * @return
	 */
	public Vector sub(Vector v) {
		return new Vector(x-v.getX(),y-v.getY(),z-v.getZ());
	}
	
	/**
	 * toString...
	 */
	@Override
	public String toString() {
		return "("+x+","+y+","+z+")";
	}
	
	/**
	 * normalize the vector (same orientation, but |v| = 1 always)
	 * @return
	 */
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
