package com.biu.cg.objects3d;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;

public class TempShip extends Object3D {


	private Orientation cam;

	public TempShip(Vector pos, Orientation camera) {
		super(pos);
		this.cam = camera;
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		
		GL gl = gLDrawable.getGL();
		
		float x = cam.getPosition().x + 5*cam.getAxis('z').x;
		float y = cam.getPosition().y + 5*cam.getAxis('z').y;
		float z = cam.getPosition().z + 5*cam.getAxis('z').z;
		
		Orientation o = getOrientation();
		float xx = o.getAxis('x').neg().x;
		float xy = o.getAxis('x').neg().y;
		float xz = o.getAxis('x').neg().z;
		
		float yx = o.getAxis('y').neg().x;
		float yy = o.getAxis('y').neg().y;
		float yz = o.getAxis('y').neg().z;
		
		gl.glBegin(GL.GL_QUADS);
		
		//back
		gl.glColor3f(0.75f, 0.75f, 0.75f);
		gl.glVertex3f( -xx + x, -xy + y, -xz + z);
		
		gl.glColor3f(0.75f, 0.75f, 0.75f);
		gl.glVertex3f(yx -xx + x,yy -xy + y,yz -xz + z);
		
		gl.glColor3f(0.75f, 0.75f, 0.75f);
		gl.glVertex3f(yx + xx + x,yy + xy + y,yz + xz + z);
		
		gl.glColor3f(0.75f, 0.75f, 0.75f);
		gl.glVertex3f(xx + x,xy + y,xz + z);
		
		gl.glEnd();
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}

}
