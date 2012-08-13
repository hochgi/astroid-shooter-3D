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
import com.biu.cg.objects3d.particles.sprites.SmokeEmitter;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;

public class SmallAsteroid extends Asteroid {



	public SmallAsteroid(Earth earth, Orientation camera, Vector pos) {
		super(earth, camera, pos, "models/asteroids/astro01.wng");
		
	}

	@Override
	public void collisionAction(Collidable collidable) {
		Vector dir = direction.neg();
		switch(collidable.getType()){
		case MOTHERSHIP:
			//dir = collision normal on wall... (if you want)
		case EARTH:
		case ROCKET:
		case PHOTON:
			new Explosion(getPosition(),camera, 0.25f, dir);
			Collidables.unregisterObject(this);
			Asteroids.unregisterAsteroid(this);
			Object3D.unregisterAsteroid(this);
			alive=false;
			dismissLockedRockets();
			break;
		case ATMOSPHERE:
			for (int i = 0; i < 4; i++)
				Sprite.registerObject(new Flare(Game.particleTex, new Vector(getPosition()), 9f * ((float)Math.random()*0.9f + 0.9f), direction.neg().mulMutate(0.3f).noise(0.05f), camera, 15f));
			
			break;
		}
		
	}
	
	@Override
	public BoundingShape getBoundingShape() {
		return new BoundingSphere(getPosition(), 20);
	}

}
