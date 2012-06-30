package com.biu.cg.math3d;

//import javax.vecmath.Vector3f;

/**
 * a simple Vector class
 * @author gilad
 *
 */
public class Vector {
	public float x;
	public float y;
	public float z;
	
	/**
	 * setter constructor
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	/**
	 * default constructor
	 */
	public Vector() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	//getters & setters:
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	
	/**
	 * add a vector to self
	 * @param v - vector
	 * @return
	 */
	public Vector add(Vector v, float step) {
		return new Vector(x+v.getX()*step,y+v.getY()*step,z+v.getZ()*step);
	}
	
	/**
	 * add a vector to self
	 * @param v - vector
	 * @return
	 */
	public void addMutate(Vector v, float step) {
		x+=v.getX()*step;
		y+=v.getY()*step;
		z+=v.getZ()*step;
	}
	
	/**
	 * substruct a vector from self
	 * @param v - vector
	 * @return
	 */
	public Vector sub(Vector v, float step) {
		return new Vector(x-v.getX()*step,y-v.getY()*step,z-v.getZ()*step);
	}
	
	public void subMutate(Vector v, float step) {
		x-=v.getX()*step;
		y-=v.getY()*step;
		z-=v.getZ()*step;
	}
	
	public Vector mul(float c) {
		return new Vector(c*x,c*y,c*z);
	}
	
	public void mulMutate(float c) {
		x *= c;
		y *= c;
		z *= c;
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
	
	public float dot(Vector u) {
		return x*u.x + y*u.y + z*u.z;
	}
	
	public Vector projectionOnPlane(Vector normalToPlain) {		
		return sub(normalToPlain.mul(dot(normalToPlain)),1);
	}
	
	public static Vector cross(Vector u, Vector v) {
//		Vector3f w = new Vector3f();
//		w.cross(new Vector3f(u.x, u.y, u.z), new Vector3f(v.x, v.y, v.z));
//		return new Vector(w.x,w.y,w.z).normalize();
		return new Vector(u.y*v.z-u.z*v.y,
						  u.z*v.x-u.x*v.z,
						  u.x*v.y-u.y*v.x);
	}
	
	public Vector neg() {
		return new Vector(-x,-y,-z);
	}
	
	public Vector noise(float noise) {
		x += Math.random()*noise - noise/2f;
		y += Math.random()*noise - noise/2f;
		z += Math.random()*noise - noise/2f;
		return this;
	}
	
	public float sqrDistanceTo(Vector v) {
		return (v.x-x)*(v.x-x) +
			   (v.y-y)*(v.y-y) +
			   (v.z-z)*(v.z-z);
	}

	public float len() {
		return (float)Math.sqrt(x*x+y*y+z*z);
	}

	public void neg(char c) {
		switch(c){
		case 'x':
		case 'X':
			x = -x;
			break;
		case 'y':
		case 'Y':
			y = -y;
			break;
		case 'z':
		case 'Z':
			z = -z;
			break;
		default:
				//do nothing
		}
	}
}
