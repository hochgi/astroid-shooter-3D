package com.biu.cg.objects3d.particles.sprites;

import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;


public class Shockwave extends Sprite {

	private Texture texture;
	private float size;
	private float[] rgba;
	static final float decay = 0.96f;
	static final float growth = 1.05f;

	public Shockwave(Texture shockwaveTex, Vector pos) {
		super(pos, null);
		texture = shockwaveTex;
		size = 1f;
		rgba = new float[4];
		rgba[0] = 1f;
		rgba[1] = 1f;
		rgba[2] = 1f;
		rgba[3] = 1f;
	}
	
	@Override
	public int compareTo(Sprite o) {
		return -1;
	}

	@Override
	protected float[] getRGBA() {
		return rgba;
	}
	
	@Override
	protected void update() {
		size *= growth;
		rgba[3] *= decay;
	}
	
	public boolean isDead() {
		return rgba[3] < 0.1f;
	}

	@Override
	protected Vector[] getQuadBillboard() {
		Vector[] bb = new Vector[4];
		Vector position = getPosition();

	    bb[0] = new Vector(position.x-size, position.y, position.z-size);
	    bb[1] = new Vector(position.x+size, position.y, position.z-size);
	    bb[2] = new Vector(position.x+size, position.y, position.z+size);
	    bb[3] = new Vector(position.x-size, position.y, position.z+size);
		return bb;
	}

	@Override
	protected Texture getTexture() {
		return texture;
	}

}
