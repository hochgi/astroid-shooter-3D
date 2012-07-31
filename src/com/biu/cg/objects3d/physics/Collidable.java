package com.biu.cg.objects3d.physics;

import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;

public interface Collidable {
	
	enum Type {ROCKET , PHOTON , ASTEROID , SHIP , PLAYER_SHIP , EARTH , ATMOSPHERE , MOTHERSHIP , RELOAD , OTHER}
	
	public void collisionAction(Collidable collidable);
	
	public BoundingShape getBoundingShape();
	
	public Type getType();
}
