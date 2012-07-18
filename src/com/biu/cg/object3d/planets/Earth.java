package com.biu.cg.object3d.planets;

import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.boundingShapes.BoundingShape;
import com.biu.cg.object3d.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.physics.Collidable;

public class Earth extends Model3D implements Collidable {

	public Earth() {
		super(new Vector(3000, 0 , 0),"models/earth/earth.wng" , "models/earth/earth.jpg");
		// TODO Auto-generated constructor stub
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
	
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.EARTH;
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}
	
	

}
