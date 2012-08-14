package com.biu.cg.object3d.physics.boundingShapes;

import com.biu.cg.objects3d.Object3D;

/**
 * BoundingShape - an interface for collision shape.
 * @author Irzh
 *
 */
public interface BoundingShape {
	public boolean intersect(AABB shape);
	public boolean intersect(BoundingSphere shape);
	public boolean intersect(Dot shape);
	public boolean intersect(AABBSuit shape);
	
	
	public Object3D getCreator();
}
