package com.biu.cg.objects3d.particles.sprites;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Particle;
import com.sun.opengl.util.texture.Texture;


public abstract class Sprite extends Particle {

	public Sprite(Vector position) {
		super(position);
	}
	
	public void evolve() {
		update();
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		Vector[] bb = getQuadBillboard();
	    getTexture().bind();
	    gl.glColor4f(1f,1f,1f,1f);

	    gl.glBegin(GL.GL_QUADS);
	    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3d(bb[0].x, bb[0].y, bb[0].z);
	    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3d(bb[1].x, bb[1].y, bb[1].z);
	    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3d(bb[2].x, bb[2].y, bb[2].z);
	    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3d(bb[3].x, bb[3].y, bb[3].z);
	    gl.glEnd();
	}
	
	protected abstract Vector[] getQuadBillboard();

	protected abstract Texture getTexture();

}
