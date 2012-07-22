package com.biu.cg.objects3d.particles.sprites;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLException;
import com.biu.cg.main.Explosion;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.asteroids.Asteroid;
import com.biu.cg.objects3d.asteroids.Asteroids;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;
import com.biu.cg.sound.SoundPlayer;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class Shot extends SpriteEmitter implements Collidable {

	private static Texture particleTex;
	private boolean hasCollide;
	private Vector dir;
	private float vel;
	private int age;
	private boolean locked=false;
	private int homingRadius=200;
	//private int homingDelay=10;
	
	private Asteroid target=null;

	public Shot(Vector position, Orientation camera, Vector direction, float velocity) {
		super(position, camera);
		dir = direction.normalize();
		vel = velocity;
		hasCollide = false;
		age = 400;
		
		Collidables.registerObject(this);
		
		SoundPlayer.rocketShot();
	}
	
	public static void init() {
		try {
			particleTex = TextureIO.newTexture(new File( "textures/round_particle_16X16.png" ),false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isDead() {
		return hasCollide || age <= 0;
	}

	public void collisionDetected() {
		hasCollide = true;
	}
	
	private BoundingSphere getHomingArea(){
		return new BoundingSphere(getPosition() , homingRadius);
	}

	@Override
	protected void update() {
		if(!isDead()){
			age--;
			if(!locked){
				//setPosition(getPosition().add(dir, vel));
				
				
				float dist=Float.MAX_VALUE;
				for(Asteroid a : Asteroids.getAsteroids()){
					if(getHomingArea().intersect((BoundingSphere)a.getBoundingShape())){
						float d = getPosition().sqrDistanceTo(a.getPosition());
						if(d<dist){
							locked=true;
							target=a;
							a.registerLockedMissle(this);
							dist=d;
						}
					}
				}
				homingRadius+=10;
				
			}else{
				float d = (float)Math.sqrt(target.getPosition().sqrDistanceTo(getPosition()));
				dir.addMutate(target.getPosition().sub(getPosition(),1f).normalize(),6f/d).normalize();
			}
			setPosition(getPosition().add(dir, vel));
			for (int i = 0; i < 8; i++) {
				Sprite.registerObject(new Flare(particleTex, new Vector(getPosition()), vel * ((float)Math.random()*0.45f + 0.45f), new Vector(dir).noise(0.005f), getCamera()));
			}
		}
	}

	@Override
	public void collisionAction(Collidable collidable) {	
//		System.out.println("Rocket Collided with " + collidable.getType());
		switch(collidable.getType()){
		case SHIP:
			//new Explosion(getPosition(),getCamera(), false);
			Collidables.unregisterObject(this);
			collisionDetected();
			
			break;
		case EARTH:
			new Explosion(getPosition(),getCamera(), false);
			Collidables.unregisterObject(this);
			collisionDetected();
			
			break;
		case ASTEROID:
			//new Explosion(getPosition(),getCamera(), false);
			Collidables.unregisterObject(this);
			collisionDetected();
			break;
		}
	}
	
	@Override
	public BoundingShape getBoundingShape() {
		return new BoundingSphere(getPosition(), 2);
	}

	@Override
	public Type getType() {
		return Type.ROCKET;
	}

	public void unlockRocket() {
		locked = false;
	}
}
