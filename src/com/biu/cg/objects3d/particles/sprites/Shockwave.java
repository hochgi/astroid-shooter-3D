package com.biu.cg.objects3d.particles.sprites;

import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;


public class Shockwave extends Sprite{

	private Texture texture;
	private double age;
	private double size;
	static final double decay = 0.95;
	static final double growth = 1.06;

	public Shockwave(Texture shockwaveTex, Vector pos) {
		super(pos);
		texture = shockwaveTex;
		age = 1.0;
		size = 1.0;
	}

	@Override
	protected void update() {
		size *= growth;
		age *= decay;
	}
	
	public boolean isDead() {
		return age < 0.1;
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
