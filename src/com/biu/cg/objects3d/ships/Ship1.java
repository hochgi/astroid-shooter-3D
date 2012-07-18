package com.biu.cg.objects3d.ships;

import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.physics.Collidable;

public class Ship1 extends Ship implements Collidable {
	
	
	public Ship1() {
		
		super("models/ship1/ship.obj" , "models/ship1/F02_512.jpg");
	}
	
	public Ship1(Vector position) {
		super(position, "models/ship1/ship.wng" , "models/ship1/F02_512.jpg");
		
	}

	@Override
	public float getWingHeight() {
		return -1.25f;
	}

	@Override
	public float getWingWidth() {
		return 2.5f;
	}

	@Override
	public void collisionAction(Collidable collidable) {
		// TODO Auto-generated method stub
		if(!collidable.getClass().isInstance(Shot.class))
			System.out.println("COLLISION");
	}

	@Override
	public BoundingShape getBoundingShape() {
		// TODO Auto-generated method stub
		return new BoundingSphere(getVertices());
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.PLAYER_SHIP;
	}
}
