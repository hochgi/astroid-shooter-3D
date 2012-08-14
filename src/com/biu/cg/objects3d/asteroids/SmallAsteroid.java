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
import com.biu.cg.objects3d.particles.sprites.SparkEmitter;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;

/**
 * SmallAsteroid - extension of Asteroid.
 * @author Irzh
 *
 */
public class SmallAsteroid extends Asteroid {

	private final float baseLife = 8;
	private float life = baseLife;

	/**
	 * c'tor.
	 * @param earth
	 * @param camera
	 * @param pos
	 */
	public SmallAsteroid(Earth earth, Orientation camera, Vector pos) {
		super(earth, camera, pos, "models/asteroids/astro01.wng");
		
	}

	
	/**
	 * Decide which action to take on collision event.
	 */
	@Override
	public void collisionAction(Collidable collidable) {
		int damage=0;
		switch(collidable.getType()){
		case EARTH: // collision with EARTH.
			// destroy the asteroid.
			onDestroy();
			//reduce Earth Health.
			reduceEarthHealth(8);
			break;
		case ROCKET:	// collision with ROCKET.
			// destroy the asteroid.
			onDestroy();
			// add points.
			addPoints(8);
			break;	
		case PHOTON:	// collision with photon.
			damage=1;
			life-=damage;	// reduce asteroid's health.
			if(life<=0){	// asteroid's health is 0.
				// destroy the asteroid.
				onDestroy();
				// add points.
				addPoints(10);
			}else{
				// create photon - asteroid collision visual effects.
				new SparkEmitter(getPosition(), camera, 3);
				new SmokeEmitter(getPosition(), camera, 5);
			}
			break;
		case ATMOSPHERE:	// collision with ATMOSPHERE.
			// create a smoke trail.
			for (int i = 0; i < 4; i++)
				Sprite.registerObject(new Flare(Game.particleTex, new Vector(getPosition()), 9f * ((float)Math.random()*0.9f + 0.9f), direction.neg().mulMutate(0.3f).noise(0.05f), camera, 15f));
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
		return new BoundingSphere(getPosition(), 20);
	}



	/**
	 * What should happen when the asteroid is destroyed.
	 */
	@Override
	protected void onDestroy() {
		new Explosion(getPosition(),camera, 0.25f,  direction.neg());
		Collidables.unregisterObject(this);
		Asteroids.unregisterAsteroid(this);
		Object3D.unregisterAsteroid(this);
		alive=false;
		dismissLockedRockets();
		
	}

}
