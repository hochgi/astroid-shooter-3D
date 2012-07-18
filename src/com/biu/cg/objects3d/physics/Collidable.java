package com.biu.cg.objects3d.physics;

import com.biu.cg.object3d.boundingShapes.BoundingShape;

public interface Collidable {
	
	enum Type {ROCKET , ASTEROID , SHIP , PLAYER_SHIP , EARTH , OTHER}
	
	public void collisionAction(Collidable collidable);
	
	public BoundingShape getBoundingShape();
	
	public Type getType();
}
