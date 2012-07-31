package com.biu.cg.object3d.physics.boundingShapes;

import java.util.ArrayList;

import com.biu.cg.objects3d.Object3D;

public class AABBSuit implements BoundingShape {
	
	ArrayList<AABB> aabbs;

	public AABBSuit(ArrayList<AABB> aabbs){
		this.aabbs = aabbs;
	}
	
	@Override
	public boolean intersect(AABB shape) {
		for(AABB b : aabbs){
			if(b.intersect(shape))
				return true;
		}
		return false;
	}

	@Override
	public boolean intersect(BoundingSphere shape) {
		for(AABB b : aabbs){
			if(b.intersect(shape))
				return true;
		}
		return false;
	}

	@Override
	public boolean intersect(Dot shape) {
		for(AABB b : aabbs){
			if(b.intersect(shape))
				return true;
		}
		return false;
	}

	@Override
	public Object3D getCreator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean intersect(AABBSuit shape) {
		// TODO Auto-generated method stub
		return false;
	}

}
