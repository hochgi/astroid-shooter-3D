package com.biu.cg.objects3d.particles.sprites;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.media.opengl.GLException;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Particle;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public class Shot extends SpriteEmitter {

	private static Texture particleTex;
	private LinkedList<Spark> sparks;
	private boolean hasCollide;
	private Orientation cam;
	private Vector dir;
	private float vel;

	public Shot(Orientation camera, Vector direction, float velocity) {
		super(camera.getPosition());
		cam = camera;
		dir = direction.normalize();
		vel = velocity;
		sparks = new LinkedList<Spark>();
		hasCollide = false;
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
		return hasCollide;
	}


	public void collisionDetected() {
		hasCollide = true;
	}

	@Override
	protected void update() {
		for (Spark s : sparks) {
			s.update();
		}
	}
}
