package com.biu.cg.object3d.boundingShapes;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Object3D;

public class Dot implements BoundingShape {
	
	private Vector pos;
	
	
	public Vector getPos() {
		return pos;
	}
	
	public Dot(Vector pos){
		this.pos = pos;
	}
	
	
	@Override
	public boolean intersect(AABB shape) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersect(BoundingSphere shape) {
		// TODO Auto-generated method stubs
		if(Math.sqrt(pos.sqrDistanceTo(shape.getCenter())) < shape.getRadius())
			return true;
		
		return false;
	}


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

}
