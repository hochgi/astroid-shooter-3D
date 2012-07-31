package com.biu.cg.object3d.physics.boundingShapes;

import java.util.ArrayList;

import com.biu.cg.math3d.Polygon;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.Object3D;
import com.owens.oobjloader.builder.Face;
import com.owens.oobjloader.builder.FaceVertex;

public class Planes implements BoundingShape  {
	
	private ArrayList<Plane> planes = new ArrayList<Plane>();
	
	public Planes(Model3D model){
		for(Polygon p : model.getPolygons()){
			planes.add(new Plane(p));
		}
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
