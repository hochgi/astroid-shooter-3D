package com.biu.cg.objects3d.particles.sprites;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Rotator;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Phase;
import com.sun.opengl.util.texture.Texture;


public class Flash extends Sprite {

	private double size;
	private Phase stage;
	private Orientation cam;
	private double angle;
	private double accumulatedAngle;
	private Vector[] bb;
	private Texture texture;

	public Flash(Texture tex, Vector position, Orientation camera) {
		super(position);
		texture = tex;
		size = 0.1;
		stage = Phase.First;
		cam = camera;
		angle = 0.2*Math.random();
		accumulatedAngle = 0;
		bb = new Vector[4]; //bb stands for billboards
		bb[0] = new Vector();
		bb[1] = new Vector();
		bb[2] = new Vector();
		bb[3] = new Vector();
	}

	@Override
	public boolean isDead() {
		return stage == Phase.Dead;
	}

	@Override
	protected void update() {
		switch(stage){
		case First:
			inflate();
			break;
		case Second:
			shrink();
			break;
		case Dead:
		default:
			//do nothing
		}
	}

	private void shrink() {
		size *= 0.8;
		if(size < 0.1){
			stage = Phase.Dead;
		}
	}

	private void inflate() {
		size *= 1.2;
		if(size > 7.5){
			stage = Phase.Second;
		}
	}

	@Override
	protected Vector[] getQuadBillboard() {
		Vector pos = getPosition();
		Vector[] q = cam.getOrthogonalQuadAtPosition(pos, size);
		accumulatedAngle += angle;
		if(accumulatedAngle >= (Math.PI * 2)) {
			accumulatedAngle -= (Math.PI * 2);
    	}
		Vector pltp = cam.perpendicularLineToPlane(getPosition()).normalize();
		Rotator.oneTimeRotatation(q[0], bb[0], pltp, accumulatedAngle);
		Rotator.oneTimeRotatation(q[1], bb[1], pltp, accumulatedAngle);
		Rotator.oneTimeRotatation(q[2], bb[2], pltp, accumulatedAngle);
		Rotator.oneTimeRotatation(q[3], bb[3], pltp, accumulatedAngle);
		
		bb[0] = bb[0].add(pos, 1);
		bb[1] = bb[1].add(pos, 1);
		bb[2] = bb[2].add(pos, 1);
		bb[3] = bb[3].add(pos, 1);
		
		return bb;
	}

	@Override
	protected Texture getTexture() {
		// TODO Auto-generated method stub
		return texture;
	}

}
