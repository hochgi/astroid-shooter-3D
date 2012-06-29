package com.biu.cg.objects3d.particles.sprites;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Rotator;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Phase;
import com.sun.opengl.util.texture.Texture;


public class Flash extends Sprite {

	private float size;
	private Phase stage;
	private float angle;
	private float accumulatedAngle;
	private Vector[] bb;
	private Texture texture;
	private float[] rgba;

	public Flash(Texture tex, Vector position, Orientation camera) {
		super(position, camera);
		texture = tex;
		size = 0.25f;
		stage = Phase.First;
		angle = 0.01f*(float)Math.random()+0.01f;
		accumulatedAngle = 0;
		bb = new Vector[4]; //bb stands for billboards
		bb[0] = new Vector();
		bb[1] = new Vector();
		bb[2] = new Vector();
		bb[3] = new Vector();
		rgba = new float[4];
		rgba[0] = 1f;
		rgba[1] = 0.65f;
		rgba[2] = 0.3f;
		rgba[3] = 0.5f;
	}

	@Override
	public boolean isDead() {
		return stage == Phase.Dead;
	}

	@Override
	protected void update() {
		switch(stage){
		case First:
			brighten();
			inflate();
			break;
		case Second:
			darken();
			shrink();
			break;
		case Dead:
		default:
			//do nothing
		}
	}

	private void inflate() {
		size *= 1.6f;
		if(size > 12f){
			stage = Phase.Second;
			rgba[1] -= 0.05;  
			rgba[2] -= 0.1;  
		}
	}
	
	private void brighten() {
		rgba[1] *= 1.01;  
		rgba[2] *= 1.01;  
		rgba[3] *= 1.01;  
	}

	private void shrink() {
		size *= 0.9f;
		if(size < 0.1f){
			stage = Phase.Dead;
		}
	}

	private void darken() {
		rgba[1] *= 0.8;  
		rgba[2] *= 0.7;  
		rgba[3] *= 0.6;
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

	protected void changeBlendingFunc(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);	
	}
	
	@Override
	protected Texture getTexture() {
		return texture;
	}

}
