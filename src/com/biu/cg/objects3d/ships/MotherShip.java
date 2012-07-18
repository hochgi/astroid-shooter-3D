package com.biu.cg.objects3d.ships;

import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.boundingShapes.BoundingShape;
import com.biu.cg.object3d.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.physics.Collidable;

public class MotherShip extends Model3D implements Collidable {

	public MotherShip(Vector position) {
		super(position, "models/mothership/mothership.wng" , "models/mothership/mothership.jpg");
	}

	@Override
	protected void update() {}

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
