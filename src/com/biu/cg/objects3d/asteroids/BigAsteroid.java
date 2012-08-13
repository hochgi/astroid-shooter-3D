package com.biu.cg.objects3d.asteroids;

import com.biu.cg.main.Explosion;
import com.biu.cg.main.Game;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.object3d.planets.Earth;
import com.biu.cg.objects3d.Object3D;
import com.biu.cg.objects3d.particles.sprites.Flare;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;

public class BigAsteroid extends Asteroid{

	public BigAsteroid(Earth earth, Orientation camera, Vector pos) {
		super(earth, camera, pos, "models/asteroids/astro02.wng");
	}

	@Override
	public void collisionAction(Collidable collidable) {
		switch(collidable.getType()){
		case PHOTON:
			Vector pos = getPosition();
			
			Asteroid a1 = new SmallAsteroid(earth, camera , new Vector(pos.x + 20 , pos.y + 20 , pos.z + 20));
			Asteroid a2 = new SmallAsteroid(earth, camera , new Vector(pos.x , pos.y + 20 , pos.z + 20));
			Asteroid a3 = new SmallAsteroid(earth, camera , new Vector(pos.x + 20 , pos.y - 20 , pos.z + 20));
			Asteroid a4 = new SmallAsteroid(earth, camera , new Vector(pos.x - 20 , pos.y + 20 , pos.z));
			
			
			Asteroids.registerAsteroid(a1);
			Collidables.registerObject(a1);
			Object3D.registerObject(a1);
			
			Asteroids.registerAsteroid(a2);
			Collidables.registerObject(a2);
			Object3D.registerObject(a2);
			
			Asteroids.registerAsteroid(a3);
			Collidables.registerObject(a3);
			Object3D.registerObject(a3);
			
			Asteroids.registerAsteroid(a4);
			Collidables.registerObject(a4);
			Object3D.registerObject(a4);
		case ROCKET:
		case MOTHERSHIP:
		case EARTH:
			new Explosion(getPosition(),camera, 3f, direction.neg());
			Collidables.unregisterObject(this);
			Asteroids.unregisterAsteroid(this);
			Object3D.unregisterAsteroid(this);
			alive=false;
			dismissLockedRockets();
			break;
		case ATMOSPHERE:
			for (int i = 0; i < 16; i++)
				Sprite.registerObject(new Flare(Game.particleTex, new Vector(getPosition()), 9f * ((float)Math.random()*0.9f + 0.9f), direction.neg().mulMutate(0.67f).noise(0.075f), camera, 50f));
			
			break;
		
		}
		
	}

	@Override
	public BoundingShape getBoundingShape() {
		return new BoundingSphere(getPosition(), 100);
	}

}
