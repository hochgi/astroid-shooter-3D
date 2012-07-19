package com.biu.cg.objects3d.asteroids;

import com.biu.cg.main.Explosion;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.object3d.planets.Earth;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;

public class SmallAsteroid extends Asteroid {

	public SmallAsteroid(Earth earth, Orientation camera, Vector pos) {
		super(earth, camera, pos, "models/asteroids/astro01.wng");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collisionAction(Collidable collidable) {
		switch(collidable.getType()){
		case ROCKET:
			alive=false;
			new Explosion(getPosition(), camera, false);
			Collidables.unregisterObject(this);
			Asteroids.unregisterAsteroid(this);
			break;
		case PHOTON:
			alive=false;
			new Explosion(getPosition(), camera, false);
			Collidables.unregisterObject(this);
			Asteroids.unregisterAsteroid(this);
			break;
		case EARTH:
			new Explosion(getPosition(),camera, false);
			Collidables.unregisterObject(this);
			Asteroids.unregisterAsteroid(this);
			alive=false;
			break;
		}
	}
	
	@Override
	public BoundingShape getBoundingShape() {
		// TODO Auto-generated method stub
		return new BoundingSphere(getPosition(), 50);
	}

}
