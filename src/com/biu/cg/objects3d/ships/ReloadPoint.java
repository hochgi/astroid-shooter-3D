package com.biu.cg.objects3d.ships;

import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.physics.Collidable;

/**
 * ReloadPoint - refills player's ship fuel and rockets.
 * @author Irzh
 *
 */
public class ReloadPoint implements Collidable {
	
	Vector position;
	BoundingSphere bs;
	
	/**
	 * c'tor
	 * @param position
	 */
	public ReloadPoint(Vector position){
		this.position = position;
		bs = new BoundingSphere(position, 100);
	}

	
	@Override
	public void collisionAction(Collidable collidable) {}

	/**
	 * gets object's bounding shape.
	 */
	@Override
	public BoundingShape getBoundingShape() {
		return bs;
	}

	/**
	 * gets the type of the object.
	 */
	@Override
	public Type getType() {
		return Type.RELOAD;
	}

}
