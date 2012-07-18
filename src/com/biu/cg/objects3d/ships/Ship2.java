package com.biu.cg.objects3d.ships;

import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.boundingShapes.BoundingShape;
import com.biu.cg.object3d.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.physics.Collidable;

public class Ship2 extends Ship implements Collidable  {

	public Ship2(String objFile, String texture) {
		super("models/ship2/ship.obj" , "models/ship1/F03_512.jpg");
		// TODO Auto-generated constructor stub
	}

	public Ship2(Vector position) {
		super(position, "models/ship2/ship.wng" , "models/ship2/F03_512.jpg");
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public float getWingHeight() {
		return 0;
	}

	@Override
	public float getWingWidth() {
		return 0;
	}

	@Override
	public void collisionAction(Collidable collidable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoundingShape getBoundingShape() {
		// TODO Auto-generated method stub
		return new BoundingSphere(getVertices());
	}
}
