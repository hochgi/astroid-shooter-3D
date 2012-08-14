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
import com.biu.cg.objects3d.particles.sprites.GlintEmitter;
import com.biu.cg.objects3d.particles.sprites.SmokeEmitter;
import com.biu.cg.objects3d.particles.sprites.SparkEmitter;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;

public class SmallAsteroid extends Asteroid {

	private final float baseLife = 8;
	private float life = baseLife;

	public SmallAsteroid(Earth earth, Orientation camera, Vector pos) {
		super(earth, camera, pos, "models/asteroids/astro01.wng");
		
	}

	
	
	@Override
	public void collisionAction(Collidable collidable) {
		Vector dir = direction.neg();
		int damage=0;
		switch(collidable.getType()){
		case MOTHERSHIP:
			//dir = collision normal on wall... (if you want)
			onDestroy();
		case EARTH:
			onDestroy();
			reduceEarthHealth(8);
			break;
		case ROCKET:
			damage=20;
			life-=damage;
			if(life<=0){
				onDestroy();
				addPoints(8);
			}else{
				new SparkEmitter(getPosition(), camera, 3);
				new SmokeEmitter(getPosition(), camera, 5);
			}
			break;
		case PHOTON:
			damage=1;
			life-=damage;
			if(life<=0){
				onDestroy();
				addPoints(10);
			}else{
				new SparkEmitter(getPosition(), camera, 3);
				new SmokeEmitter(getPosition(), camera, 5);
			}
			break;
		case ATMOSPHERE:
			for (int i = 0; i < 4; i++)
				Sprite.registerObject(new Flare(Game.particleTex, new Vector(getPosition()), 9f * ((float)Math.random()*0.9f + 0.9f), direction.neg().mulMutate(0.3f).noise(0.05f), camera, 15f));
			break;
		}
		colorRatio=life/baseLife;
	}
	
	@Override
	public BoundingShape getBoundingShape() {
		return new BoundingSphere(getPosition(), 20);
	}



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
