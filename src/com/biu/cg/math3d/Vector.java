package com.biu.cg.math3d;

import javax.vecmath.Vector3d;

/**
 * a simple Vector class
 * @author gilad
 *
 */
public class Vector {
	public double x;
	public double y;
	public double z;
	
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
	
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
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
	public Vector add(Vector v, double step) {
		return new Vector(x+v.getX()*step,y+v.getY()*step,z+v.getZ()*step);
	}
	/**
	 * substruct a vector from self
	 * @param v - vector
	 * @return
	 */
	public Vector sub(Vector v, double step) {
		return new Vector(x-v.getX()*step,y-v.getY()*step,z-v.getZ()*step);
	}
	
	public Vector mul(double c) {
		return new Vector(c*x,c*y,c*z);
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
	public double dot(Vector u) {
		return x*u.x + y*u.y + z*u.z;
	}
	
	public Vector projectionOnPlane(Vector normalToPlain) {		
		return sub(normalToPlain.mul(dot(normalToPlain)),1);
	}
	
	public static Vector cross(Vector u, Vector v) {
		//TODO: implement own version
		Vector3d w = new Vector3d();
		w.cross(new Vector3d(u.x, u.y, u.z), new Vector3d(v.x, v.y, v.z));
		return new Vector(w.x,w.y,w.z).normalize();
	}
	
	public Vector neg() {
		return new Vector(-x,-y,-z);
	}
	
	public Vector noise(double d) {
		x += Math.random()*d;
		y += Math.random()*d;
		z += Math.random()*d;
		return this;
	}
}
