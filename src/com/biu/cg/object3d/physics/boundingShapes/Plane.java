package com.biu.cg.object3d.physics.boundingShapes;

import java.util.ArrayList;

import com.biu.cg.math3d.Polygon;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Object3D;

public class Plane implements BoundingShape {
	private ArrayList<Vector> vertices;
	
	public Plane(Polygon poly){
		this.vertices = poly.vertices;
	}
	
	public Plane(){}

	
	public void addVertex(Vector v){
		vertices.add(v);
	}
	
	@Override
	public boolean intersect(AABB shape) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersect(BoundingSphere shape) {
		// TODO Auto-generated method stub
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

	@Override
	public boolean intersect(AABBSuit shape) {
		// TODO Auto-generated method stub
		return false;
	}
}