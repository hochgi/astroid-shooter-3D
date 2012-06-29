package com.biu.cg.objects3d.particles.sprites;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;

public class Spark extends LineSprite {

	private Texture tex;
	private float[] rgba;
	private Vector dir;
	private float vel;
	private float mul;
	
	public Spark(Texture texture, Vector position, Vector position2, Orientation camera) {
		super(position, position2, camera, 0.25f);
		tex = texture;
		dir = position.sub(position2, 1).normalize();
		dir.mulMutate(0.25f);
		vel = 16;
		mul = 0.88f;
		rgba = new float[4];
		rgba[0] = 0.9f;
		rgba[1] = 0.9f;
		rgba[2] = 0.6f;
		rgba[3] = 1f;
	}

	@Override
	protected Texture getTexture() {
		return tex;
	}

	@Override
	public boolean isDead() {
		return rgba[3] < 0.025f;
	}
	
	@Override
	protected float[] getRGBA() {
		return rgba ;
	}

	@Override
	protected void update() {
		getPosition().addMutate(dir, vel + 1f);
		moveTail(dir.mul(vel*0.96f));
		rgba[3] *= mul;
		vel *= 0.9;
		if(rgba[3] < 0.333f){mul = 0.5f;}
	}
}
