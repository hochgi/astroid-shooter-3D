package com.biu.cg.objects3d.particles.sprites;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;

public class SparkEmitter extends SpriteEmitter {

	public SparkEmitter(Vector position, Orientation camera, int sparks) {
		super(position, camera);
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}

}
