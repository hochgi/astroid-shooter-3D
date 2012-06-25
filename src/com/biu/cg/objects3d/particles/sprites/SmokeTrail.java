package com.biu.cg.objects3d.particles.sprites;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;

public class SmokeTrail extends LineSprite {

	private Texture tex;
	private float[] rgba;
	private Vector dir;
	private float vel;

	public SmokeTrail(Texture texture, Vector position, Vector position2, Orientation camera) {
		super(position, position2, camera, 0.25f);
		tex = texture;
		dir = position.sub(position2, 1).normalize();
		dir.mulMutate(0.25f);
		vel = 4;
		rgba = new float[4];
		rgba[0] = 0.9f;
		rgba[1] = 0.75f;
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
		getPosition().addMutate(dir, vel + 0.25f);
//		rgba[0] -= 0.015f;
//		rgba[1] -= 0.01f;
//		rgba[2] -= 0.0005f;
		rgba[3] *= 0.97f;
		vel *= 0.96;
	}

}
