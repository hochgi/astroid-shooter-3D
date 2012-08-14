package com.biu.cg.object3d.physics.boundingShapes;

import java.util.ArrayList;

import com.biu.cg.objects3d.Object3D;

/**
 * AABBSuit - a collection of many AABBs. It is used the create a custom collision detection model on a complex object.
 * We used it to implement mothership's collision detection model. Since it is a complex object that has a corridor that 
 * the player's ship supposed to fly thru it, we enveloped the mothership with box shapes manually at Blender. This class loads
 * those boxes and creates the AABB suit.
 * @author Irzh
 *
 */
public class AABBSuit implements BoundingShape {
	
	ArrayList<AABB> aabbs;

	/**
	 * get the collection of the aabbs.
	 * @return
	 */
	public ArrayList<AABB> getAabbs() {
		return aabbs;
	}
	
	/**
	 * c'tor.
	 * @param aabbs
	 */
	public AABBSuit(ArrayList<AABB> aabbs){
		this.aabbs = aabbs;
	}
	
	/**
	 * intersection with AABB shape - according to the algorithm learned in class.
	 */
	@Override
	public boolean intersect(AABB shape) {
		// check collision for each AABB.
		for(AABB b : aabbs){
			if(b.intersect(shape))
				return true;
		}
		return false;
	}

	/**
	 * intersection with BoundingSphere shape - according to the algorithm learned in class.
	 */
	@Override
	public boolean intersect(BoundingSphere shape) {
		// check collision for each AABB.
		for(AABB b : aabbs){
			if(b.intersect(shape))
				return true;
		}
		return false;
	}

	/**
	 * intersection with Dot shape - according to the algorithm learned in class.
	 */
	@Override
	public boolean intersect(Dot shape) {
		// check collision for each AABB.
		for(AABB b : aabbs){
			if(b.intersect(shape))
				return true;
		}
		return false;
	}

	@Override
	public Object3D getCreator() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * intersection with AABBSuit shape (no need to implement).
	 */
	@Override
	public boolean intersect(AABBSuit shape) {
		// TODO Auto-generated method stub
		return false;
	}

}
