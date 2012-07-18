package com.biu.cg.objects3d.particles.sprites;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.GLException;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.boundingShapes.BoundingShape;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class Photon extends Sprite implements Collidable {

	private static Timer timer;
	private static Texture particleTex;
	private static boolean wantToShoot = false;
	private static float sVel;
	private static Vector sPos;
	private static Vector sDir;
	private static Orientation sCam;
	private static Object lock = new Object();
	
	private static TimerTask task = new TimerTask() {
		@Override
		public void run() {
			synchronized(lock) {
				if(wantToShoot){
					Sprite.registerObject(new Photon(sPos, sCam, sDir, sVel));
					wantToShoot = false;
				}
			}
		}
	};
	
	static {
		timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 200);
	}
	
	private boolean hasCollide;
	private Vector dir;
	private float vel;
	private int age;

	
	public Photon(Vector position, Orientation camera, Vector direction, float velocity) {
		super(position, camera);
		dir = direction.normalize();
		vel = velocity;
		hasCollide = false;
		age = 100;
		
		Collidables.registerObject(this);
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
		setPosition(getPosition().add(dir, vel));
	}

	@Override
	public void collisionAction(Collidable collidable) {
		hasCollide = true;
	}

	@Override
	public BoundingShape getBoundingShape() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void createNewPhoton(Vector position, Orientation camera, Vector direction, float velocity) {
		synchronized(lock) {
			if(!wantToShoot){
				sPos = position;
				sCam = camera;
				sDir = direction;
				sVel = velocity;
				wantToShoot = true;
			}
		}
	}

	public static boolean canShoot() {
		return !wantToShoot;
	}
}
