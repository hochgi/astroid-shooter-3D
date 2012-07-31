package com.biu.cg.objects3d.particles.sprites;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLException;
import com.biu.cg.main.Explosion;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;
import com.biu.cg.sound.SoundPlayer;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class Photon extends Sprite implements Collidable {


	private static Texture particleTex;
	private static int coolOff = 0;
	
	private boolean hasCollide;
	private Vector dir;
	private float vel;
	private int age;
	
	public Photon(Vector position, Orientation camera, Vector direction, float velocity) {
		super(position, camera);
		dir = direction.normalize();
		vel = velocity;
		hasCollide = false;
		age = 20;
		
		Collidables.registerObject(this);
		
		SoundPlayer.photonShot();
		coolOff++;
	}

	public static void init() {
		try {
			particleTex = TextureIO.newTexture(new File( "textures/particle_32X32.png" ),false);
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
	
	@Override
	protected Vector[] getQuadBillboard() {
		return getCam().getOrthogonalQuadAtPosition(getPosition(), 1);
	}

	@Override
	protected Texture getTexture() {
		return particleTex;
	}

	@Override
	protected void update() {
		
		age--;
		if(isDead())
			Collidables.unregisterObject(this);
		setPosition(getPosition().add(dir, vel));
	}

	@Override
	public void collisionAction(Collidable collidable) {
		
		switch(collidable.getType()){
		case SHIP:
//			new Explosion(getPosition(),getCam(), false);
//			Collidables.unregisterObject(this);
			
			break;
		case MOTHERSHIP:
		case EARTH:
			new Explosion(getPosition(),getCam(), false);
			Collidables.unregisterObject(this);
			hasCollide = true;
			break;
		case ASTEROID:
			Collidables.unregisterObject(this);
			hasCollide = true;
			break;
		}
	}

	@Override
	public BoundingShape getBoundingShape() {
		return new BoundingSphere(getPosition() , 5);
	}

	public static boolean canShoot() {
		return coolOff  == 0;
	}

	@Override
	public Type getType() {
		return Type.PHOTON;
	}

	public static void incrementCoolOff() {
		coolOff = (coolOff + 1) % 5;
	}
}
