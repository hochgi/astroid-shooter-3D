package com.biu.cg.object3d.boundingShapes;

import com.biu.cg.objects3d.Object3D;

public interface BoundingShape {
	public boolean intersect(AABB shape);
	public boolean intersect(BoundingSphere shape);
	public boolean intersect(Dot shape);
	
	
	public Object3D getCreator();
}
