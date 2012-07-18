package com.biu.cg.objects3d.physics;

import com.biu.cg.object3d.boundingShapes.BoundingShape;

public interface Collidable {
	public void collisionAction(Collidable collidable);
	
	public BoundingShape getBoundingShape();
}
