package com.biu.cg.objects3d.particles.sprites;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;


public class Spark extends Sprite {

	private float vel;
	private Vector dir;
	private float decay;
	private Orientation cam;
	private float[] rgba;
	private Texture tex;

	public Spark(Texture texture, Vector position, float velocity, Vector direction, Orientation camera) {
		super(position);
		this.cam = camera;
		this.tex = texture;
		this.vel = velocity;
		this.dir = direction;
		this.rgba = new float[4];
		rgba[0] = 1f;
		rgba[1] = 1f;
		rgba[2] = 1f;
		rgba[3] = 1f;
		this.decay =  (float)Math.random() * 0.025f + 0.025f;
	}

	@Override
	public boolean isDead() {
		return rgba[3] <= 0.05;
	}

	@Override
	protected float[] getRGBA() {
		return rgba;
	}

	@Override
	protected void update() {
		rgba[3] -= decay;
		Vector newPos = getPosition().add(dir, vel);
		setPosition(newPos);
	}

	@Override
	protected Vector[] getQuadBillboard() {
		Vector pos = getPosition();
		double size = 1 + (Math.random() * 0.4) - 0.2;
		return cam.getOrthogonalQuadAtPosition(pos, size);
	}

	@Override
	protected Texture getTexture() {
		return tex;
	}

}
