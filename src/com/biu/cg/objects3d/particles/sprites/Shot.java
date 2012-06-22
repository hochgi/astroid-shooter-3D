package com.biu.cg.objects3d.particles.sprites;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLException;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public class Shot extends SpriteEmitter {

	private static Texture particleTex;
	private boolean hasCollide;
	private Orientation cam;
	private Vector dir;
	private float vel;
	private int age;

	public Shot(Orientation camera, Vector direction, float velocity) {
		super(new Vector(camera.getPosition()));
		cam = camera;
		dir = direction.normalize();
		vel = velocity;
		hasCollide = false;
		age = 1000;
	}


	public static void init() {
		try {
			particleTex = TextureIO.newTexture(new File( "textures/particle_128X128.png" ),false);
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

	@Override
	protected void update() {
		age--;
		setPosition(getPosition().add(dir, vel));
		for (int i = 0; i < 10; i++) {
			SpriteEmitter.registerObjectForNextIteration(new Spark(particleTex, new Vector(getPosition()), vel * ((float)Math.random()*0.5f + 0.5f), new Vector(dir).noise(0.05f), cam));
		}
	}
}
