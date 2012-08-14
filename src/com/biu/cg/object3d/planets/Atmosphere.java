package com.biu.cg.object3d.planets;

import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.physics.Collidable;

/**
 * Atmosphere - implements Collidable.
 * @author Irzh
 *
 */
public class Atmosphere implements Collidable  {
	private Earth earth;
	
	public Atmosphere(Earth earth){
		this.earth = earth;
	}
	
	
	@Override
	public void collisionAction(Collidable collidable) {}

	/**
	 * gets object's bounding shape.
	 */
	@Override
	public BoundingShape getBoundingShape() {
		return new BoundingSphere(earth.getPosition(), ((BoundingSphere)earth.getBoundingShape()).getRadius() * 1.5f);
	}
	
	/**
	 * gets the type of the object.
	 */
	@Override
	public Type getType() {
		return Type.ATMOSPHERE;
	}

}
