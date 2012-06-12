package com.biu.cg.objects3d.particles;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Object3D;

public abstract class Particle extends Object3D {

	public Particle(Vector position) {
		super(position);
	}

	public abstract boolean isDead();
}
