package com.biu.cg.object3d.planets;

import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.physics.Collidable;

public class Atmosphere implements Collidable  {
	private Earth earth;
	
	public Atmosphere(Earth earth){
		this.earth = earth;
	}
	
	@Override
	public void collisionAction(Collidable collidable) {
		// TODO Auto-generated method stub	
	}

	@Override
	public BoundingShape getBoundingShape() {
		// TODO Auto-generated method stub
		//return new BoundingSphere(earth.getPosition(), 1500);
		return new BoundingSphere(earth.getPosition(), 1500);
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.ATMOSPHERE;
	}

}
