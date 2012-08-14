package com.biu.cg.objects3d.asteroids;

import com.biu.cg.main.Explosion;
import com.biu.cg.main.Game;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.object3d.planets.Earth;
import com.biu.cg.objects3d.Object3D;
import com.biu.cg.objects3d.particles.sprites.FlameEmitter;
import com.biu.cg.objects3d.particles.sprites.Flare;
import com.biu.cg.objects3d.particles.sprites.SmokeEmitter;
import com.biu.cg.objects3d.particles.sprites.SparkEmitter;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;

/**
 * BigAsteroid - extension of Asteroid.
 * @author Irzh
 *
 */
public class BigAsteroid extends Asteroid{
	private final float baseLife = 20;
	private float life = baseLife;
	public BigAsteroid(Earth earth, Orientation camera, Vector pos) {
		super(earth, camera, pos, "models/asteroids/astro02.wng");
	}

	/**
	 * Decide which action to take on collision event.
	 */
	@Override
	public void collisionAction(Collidable collidable) {
		int damage=0;
		switch(collidable.getType()){
		case PHOTON:	// collision with photon.
			damage=1;
			life-=damage; // reduce asteroid's health.
			if(life<=0){	// asteroid's health is 0.
				Vector pos = getPosition();
				
				// create four small asteroids.
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
				
				// destroy the asteroid.
				onDestroy();
				// add points.
				addPoints(20);
			}else{
				// create photon - asteroid collision visual effects.
				new SparkEmitter(getPosition(), camera, 3);
				new SmokeEmitter(getPosition(), camera, 5);
			}
			break;
		case ROCKET:	// collision with ROCKET.
			// add points.
			addPoints(15);
		case EARTH:	// collision with EARTH.
			// destroy the asteroid.
			onDestroy();	
			//reduce Earth Health.
			reduceEarthHealth(30);
			break;
		case ATMOSPHERE: // collision with ATMOSPHERE.
			// create a smoke trail.
			for (int i = 0; i < 16; i++)
				Sprite.registerObject(new Flare(Game.particleTex, new Vector(getPosition()), 9f * ((float)Math.random()*0.9f + 0.9f), direction.neg().mulMutate(0.67f).noise(0.075f), camera, 50f));
			new FlameEmitter(getPosition(), camera, 3, 2f);
			break;
		
		}
		// change the color ratio of the asteroid.
		colorRatio=life/baseLife;
	}

	/**
	 * gets object's bounding shape.
	 */
	@Override
	public BoundingShape getBoundingShape() {
		return new BoundingSphere(getPosition(), 100);
	}

	/**
	 * What should happen when the asteroid is destroyed.
	 */
	@Override
	protected void onDestroy() {
		new Explosion(getPosition(),camera, 3f, direction.neg());
		Collidables.unregisterObject(this);
		Asteroids.unregisterAsteroid(this);
		Object3D.unregisterAsteroid(this);
		alive=false;
		dismissLockedRockets();
	}

}
