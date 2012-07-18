package com.biu.cg.object3d.physics.boundingShapes;

import java.util.ArrayList;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Object3D;

public class BoundingSphere implements BoundingShape {

	Vector center;
	float radius;
	
	public Vector getCenter() {
		return center;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public BoundingSphere(ArrayList<Vector> vertices){	
		float xSum=0;
		float ySum=0;
		float zSum=0;
		
		Vector farestVector=vertices.get(0);
		
		for(Vector v : vertices){
			xSum+=v.x;
			ySum+=v.y;
			zSum+=v.z;
		}
		xSum /= vertices.size();
		ySum /= vertices.size();
		zSum /= vertices.size();
		
		
		center = new Vector(xSum , ySum , zSum);
		
		for(Vector v : vertices){
			if(center.sqrDistanceTo(v) > center.sqrDistanceTo(farestVector))
				farestVector = v;
		}
		
		radius = (float)Math.sqrt(center.sqrDistanceTo(farestVector));
	}
	
	@Override
	public boolean intersect(AABB shape) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersect(BoundingSphere shape) {
		// TODO Auto-generated method stub
		if(Math.sqrt((double)center.sqrDistanceTo(shape.getCenter())) < getRadius() + shape.getRadius())
			return true;
		return false;
	}

	@Override
	public boolean intersect(Dot shape) {
		// TODO Auto-generated method stub
		if(Math.sqrt(center.sqrDistanceTo(shape.getPos())) < radius)
			return true;
		return false;
	}

	@Override
	public Object3D getCreator() {
		// TODO Auto-generated method stub
		return null;
	}

}
