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
	private Vector dir;
	private float vel;
	private int age;

	public Shot(Orientation camera, Vector direction, float velocity) {
		super(new Vector(camera.getPosition()), camera);
		dir = direction.normalize();
		vel = velocity;
		hasCollide = false;
		age = 400;
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

	@Override
	protected void update() {
		age--;
		setPosition(getPosition().add(dir, vel));
		for (int i = 0; i < 6; i++) {
			Sprite.registerObject(new Flare(particleTex, new Vector(getPosition()), vel * ((float)Math.random()*0.45f + 0.45f), new Vector(dir).noise(0.015f), getCamera()));
		}
	}
}
