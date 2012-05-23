
public class Vector {
	double x;
	double y;
	double z;
	double speed;
	
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.speed = 2.0;
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
		return new Vector(x+(v.getX()*speed),y+(v.getY()*speed),z+(v.getZ()*speed));
	}
	public Vector sub(Vector v) {
		return new Vector(x-(v.getX()*speed),y-(v.getY()*speed),z-(v.getZ()*speed));
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+","+z+")";
	}
}
