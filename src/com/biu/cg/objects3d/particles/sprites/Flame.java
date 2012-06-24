package com.biu.cg.objects3d.particles.sprites;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Rotator;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Phase;
import com.sun.opengl.util.texture.Texture;

public class Flame extends Sprite {
	
	private float size;
	private float angle;
	private float accumulatedAngle;
	private Phase stage;
	private Vector[] bb;
	private Texture texture;
	private float[] rgba;
	private float inflateFactor;
	private Vector expander;

	public Flame(Texture tex, Vector position, Orientation camera) {
		super(position, camera);
		texture = tex;
		size = 0.5f;
		angle = 0.01f*(float)Math.random()+0.01f;
		accumulatedAngle = 0;
		stage = Phase.First;
		inflateFactor = 1.075f;
		bb = new Vector[4]; //bb stands for billboards
		bb[0] = new Vector();
		bb[1] = new Vector();
		bb[2] = new Vector();
		bb[3] = new Vector();
		rgba = new float[4];
		rgba[0] = 0.8f;
		rgba[1] = 0.4f;
		rgba[2] = 0.005f;
		rgba[3] = 0f;
		expander = new Vector((float)Math.random()*0.25f-0.125f,(float)Math.random()*0.25f,(float)Math.random()*0.25f-0.125f);
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
		Vector pos = getPosition();
		Vector[] q = getCam().getOrthogonalQuadAtPosition(pos, size);
		
		q[0].subMutate(pos, 1);
		q[1].subMutate(pos, 1);
		q[2].subMutate(pos, 1);
		q[3].subMutate(pos, 1);
		
		accumulatedAngle += angle;
		if(accumulatedAngle >= ((float)Math.PI * 2)) {
			accumulatedAngle -= ((float)Math.PI * 2);
    	}
		
		Vector pltp = getCam().perpendicularLineToPlane(getPosition()).normalize();
		Rotator.oneTimeRotatation(q[0], bb[0], pltp, accumulatedAngle);
		Rotator.oneTimeRotatation(q[1], bb[1], pltp, accumulatedAngle);
		Rotator.oneTimeRotatation(q[2], bb[2], pltp, accumulatedAngle);
		Rotator.oneTimeRotatation(q[3], bb[3], pltp, accumulatedAngle);

		bb[0].addMutate(pos, 1);
		bb[1].addMutate(pos, 1);
		bb[2].addMutate(pos, 1);
		bb[3].addMutate(pos, 1);
		
		return bb;
	}

	@Override
	protected Texture getTexture() {
		return texture;
	}

	@Override
	public boolean isDead() {
		return stage == Phase.Second && rgba[3] < 0.025f;
	}

	@Override
	protected void update() {
		getPosition().addMutate(expander, 1);
		inflate();
		switch(stage){
		case First:
			rgba[0] += 0.00625f;
			rgba[1] += 0.005f;
			rgba[2] += 0.0045f;
			rgba[3] += 0.025f;
			if(rgba[3] >= 0.95){
				stage = Phase.Second;
				inflateFactor = 1.0375f;
			}
			break;
		case Second:
			rgba[0] -= 0.03f;
			rgba[1] -= 0.02f;
			rgba[2] -= 0.0075f;
			rgba[3] -= 0.025f;
			break;
		default:
			//do nothing
		}
	}

	private void inflate() {
		size *= inflateFactor;
	}

}