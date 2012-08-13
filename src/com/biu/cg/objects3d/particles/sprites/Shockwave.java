package com.biu.cg.objects3d.particles.sprites;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;


public class Shockwave extends Sprite {

	private Texture texture;
	private float size;
	private float[] rgba;
	private Vector yVec;
	private Vector xVec;
	static final float decay = 0.85f;
	static final float growth = 1.25f;

	public Shockwave(Texture shockwaveTex, Vector pos, Vector normal, Orientation camera, float size) {
		super(pos, camera);
		texture = shockwaveTex;
		this.size = size;
		this.yVec = camera.getAxis('y').projectionOnPlane(normal).normalize();
		this.xVec = Vector.cross(yVec, normal).normalize();
		rgba = new float[4];
		rgba[0] = 1f;
		rgba[1] = 1f;
		rgba[2] = 1f;
		rgba[3] = 1f;
	}
	
	@Override
	public int compareTo(Sprite o) {
		return Integer.MIN_VALUE;
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

	    bb[0] = position.sub(xVec, size).subMutate(yVec, size);
	    bb[1] = position.add(xVec, size).subMutate(yVec, size);
	    bb[2] = position.add(xVec, size).addMutate(yVec, size);
	    bb[3] = position.sub(xVec, size).addMutate(yVec, size);
		return bb;
	}

	@Override
	protected Texture getTexture() {
		return texture;
	}

}
