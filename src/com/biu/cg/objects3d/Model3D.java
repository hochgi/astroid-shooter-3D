package com.biu.cg.objects3d;

import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Vector;


public class Model3D extends Object3D {

	public Model3D(String objFile) {
		super();
		generateModelFromFile(objFile);
	}

	public Model3D(Vector position, String objFile) {
		super(position);
		generateModelFromFile(objFile);
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		// TODO: implement.

	}

	@Override
	protected void update() {
		// TODO: implement.

	}
	
	private void generateModelFromFile(String objFile) {
		// TODO: implement. make use of loader: http://darksleep.com/oobjloader/
		
	}

}
