package com.biu.cg.objects3d.particles.sprites;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Phase;
import com.sun.opengl.util.texture.Texture;

public class Glint extends Sprite {

	private float size;
	private Phase stage;
	private Texture tex;
	private float[] rgba;
	private float inflateFactor;
	private Vector expander;
	
	public Glint(Texture texture, Vector position, Orientation camera) {
		super(position, camera);
		tex = texture;
		size = 2f;
		stage = Phase.First;
		inflateFactor = 1.13f;
		rgba = new float[4];
		rgba[0] = 1f;
		rgba[1] = 0f;
		rgba[2] = 0f;
		rgba[3] = 0.25f;
		expander = new Vector((float)Math.random()*0.25f-0.125f,
							  (float)Math.random()*0.5f,
							  (float)Math.random()*0.25f-0.125f);
	}
	
	protected void changeBlendingFunc(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);	
	}
	
	@Override
	protected float[] getRGBA() {
		return rgba ;
	}

	@Override
	protected Vector[] getQuadBillboard() {
		return getCam().getOrthogonalQuadAtPosition(getPosition(), size);
	}

	@Override
	protected Texture getTexture() {
		return tex;
	}

	@Override
	public boolean isDead() {
		return rgba[3] < 0.01f && stage == Phase.Second;
	}

	@Override
	protected void update() {
		getPosition().addMutate(expander, 2.5f);
		switch(stage){
		case First:
			size *= inflateFactor;
			rgba[3] += 0.05f;
			if(rgba[3] > 0.9f){
				stage = Phase.Second;
			}
			break;
		case Second:
			rgba[3] -= 0.0025f;
		}
	}
}
