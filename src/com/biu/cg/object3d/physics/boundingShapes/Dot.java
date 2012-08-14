package com.biu.cg.object3d.physics.boundingShapes;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Object3D;

/**
 * Dot - dot collision shape.
 * @author Irzh
 *
 */
public class Dot implements BoundingShape {
	
	private Vector pos;
	
	/**
	 * gets the location of the dot.
	 * @return
	 */
	public Vector getPos() {
		return pos;
	}
	
	/**
	 * c'tor - create a dot at the specified location. 
	 * @param pos
	 */
	public Dot(Vector pos){
		this.pos = pos;
	}
	
	/**
	 * gets the center of the AABB.
	 * @return
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
		// TODO Auto-generated method stubs
		if(Math.sqrt(pos.sqrDistanceTo(shape.getCenter())) < shape.getRadius())
			return true;
		
		return false;
	}

	/**
	 * intersection with Dot shape - according to the algorithm learned in class.
	 */
	@Override
	public boolean intersect(Dot shape) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return shape.intersect(this);
	}

}
