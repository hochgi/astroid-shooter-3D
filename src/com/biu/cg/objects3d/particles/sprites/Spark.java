package com.biu.cg.objects3d.particles.sprites;

import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;


public class Spark extends Sprite {

	public Spark(Vector position) {
		super(position);
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Vector[] getQuadBillboard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Texture getTexture() {
		// TODO Auto-generated method stub
		return null;
	}

}
