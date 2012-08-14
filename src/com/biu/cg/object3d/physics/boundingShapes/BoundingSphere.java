package com.biu.cg.object3d.physics.boundingShapes;

import java.util.ArrayList;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Object3D;

/**
 * BoundingSphere - Sphere collision shape.
 * @author Irzh
 *
 */
public class BoundingSphere implements BoundingShape {

	private Vector center;
	private float radius;
	
	/**
	 * gets the center of the AABB.
	 * @return
	 */
	public Vector getCenter() {
		return center;
	}
	
	/**
	 * gets the radius of the shape.
	 * @return
	 */
	public float getRadius() {
		return radius;
	}
	
	/**
	 * c'tor - create a shape according to a center and a radius.
	 * @param center
	 * @param radius
	 */
	public BoundingSphere(Vector center , float radius){
		this.center = center;
		this.radius = radius;
	}
	
	/**
	 * c'tor - create a shape according to a collection of vertices.
	 * @param vertices
	 */
	public BoundingSphere(ArrayList<Vector> vertices){	
		
		float xSum=0;
		float ySum=0;
		float zSum=0;
		
		Vector farestVector=vertices.get(0);
		
		for(Vector v : vertices){
			xSum+=v.x;
			ySum+=v.y;
			zSum+=v.z;
		}
		xSum /= vertices.size();
		ySum /= vertices.size();
		zSum /= vertices.size();
		
		
		center = new Vector(xSum , ySum , zSum);
		
		for(Vector v : vertices){
			if(center.sqrDistanceTo(v) > center.sqrDistanceTo(farestVector))
				farestVector = v;
		}
		
		radius = (float)Math.sqrt(center.sqrDistanceTo(farestVector));
	}
	
	/**
	 * c'tor - create a shape according to a collection of vertices with plus and mul alterations.
	 * @param vertices
	 */
	public BoundingSphere(ArrayList<Vector> vertices , float plus , float mul){	
		float xSum=0;
		float ySum=0;
		float zSum=0;
		
		Vector farestVector=vertices.get(0);
		
		for(Vector v : vertices){
			xSum+=v.x;
			ySum+=v.y;
			zSum+=v.z;
		}
		xSum /= vertices.size();
		ySum /= vertices.size();
		zSum /= vertices.size();
		
		center = new Vector(xSum , ySum , zSum);
		
		for(Vector v : vertices){
			if(center.sqrDistanceTo(v) > center.sqrDistanceTo(farestVector))
				farestVector = v;
		}
		
		radius = (float)Math.sqrt(center.sqrDistanceTo(farestVector));
		radius = (radius + plus) * mul;
	}
	

	/**
	 * intersection with AABB shape - according to the algorithm learned in class.
	 */
	@Override
	public boolean intersect(AABB shape) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * intersection with BoundingSphere shape - according to the algorithm learned in class.
	 */
	@Override
	public boolean intersect(BoundingSphere shape) {
		if(Math.sqrt((double)center.sqrDistanceTo(shape.getCenter())) < getRadius() + shape.getRadius())
			return true;
		return false;
	}

	/**
	 * intersection with Dot shape - according to the algorithm learned in class.
	 */
	@Override
	public boolean intersect(Dot shape) {
		if(Math.sqrt(center.sqrDistanceTo(shape.getPos())) < radius)
			return true;
		return false;
	}

	@Override
	public Object3D getCreator() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * intersection with AABB suit.
	 */
	@Override
	public boolean intersect(AABBSuit shape) {
		return shape.intersect(this);
	}

}
