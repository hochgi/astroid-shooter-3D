package com.biu.cg.objects3d.ships;

import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.physics.Collidable;

public class ReloadPoint implements Collidable {
	
	Vector position;
	
	public ReloadPoint(Vector position){
		this.position = position;
	}

	@Override
	public void collisionAction(Collidable collidable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoundingShape getBoundingShape() {
		// TODO Auto-generated method stub
		return new BoundingSphere(position, 50);
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.RELOAD;
	}

}
