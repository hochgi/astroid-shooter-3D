package com.biu.cg.objects3d.particles.sprites;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;


public class Flare extends Sprite {

	private float vel;
	private Vector dir;
	private float size;
	private float decay;
	private float[] rgba;
	private Texture tex;

	public Flare(Texture texture, Vector position, float velocity, Vector direction, Orientation camera) {
		super(position, camera);
		this.tex = texture;
		this.vel = velocity;
		this.dir = direction;
		this.rgba = new float[4];
		this.size = 0.25f;
		rgba[0] = 1.0f;
		rgba[1] = 0.9f;
		rgba[2] = 0.8f;
		rgba[3] = 1f;
		this.decay =  (float)Math.random() * 0.01f + 0.01f;
	}

	@Override
	public boolean isDead() {
		return rgba[3] <= 0.05f;
	}

	@Override
	protected float[] getRGBA() {
		return rgba;
	}

	@Override
	protected void update() {
		rgba[0] -= decay*1.1f;
		rgba[1] -= decay*1.2f;
		rgba[2] -= decay*1.3f;
		rgba[3] -= decay;
		size += decay*2f;
		Vector newPos = getPosition().add(dir, vel);
		setPosition(newPos);
	}

	@Override
	protected Vector[] getQuadBillboard() {
		Vector pos = getPosition();
		float size = this.size + ((float)Math.random() * 0.4f) - 0.2f;
		return getCam().getOrthogonalQuadAtPosition(pos, size);
	}

	@Override
	protected Texture getTexture() {
		return tex;
	}
}
