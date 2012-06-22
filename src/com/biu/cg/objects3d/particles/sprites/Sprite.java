package com.biu.cg.objects3d.particles.sprites;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Particle;
import com.sun.opengl.util.texture.Texture;


public abstract class Sprite extends Particle {

	private static final float[] rgba = {1f,1f,1f,1f};

	public Sprite(Vector position) {
		super(position);
	}
	
	public void evolve() {
		update();
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		try {
			Vector[] bb = getQuadBillboard();
			GL gl = gLDrawable.getGL();
			
		    getTexture().bind();
		    float[] rgba = getRGBA();
		    gl.glColor4f(rgba[0],rgba[1],rgba[2],rgba[3]);
	
		    gl.glBegin(GL.GL_QUADS);
		    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3d(bb[0].x, bb[0].y, bb[0].z);
		    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3d(bb[1].x, bb[1].y, bb[1].z);
		    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3d(bb[2].x, bb[2].y, bb[2].z);
		    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3d(bb[3].x, bb[3].y, bb[3].z);
		    gl.glEnd();
		} catch (EmitterIsNotRenderableException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * you may override the following method for different rendering.
	 * @return
	 */
	protected float[] getRGBA() {
		return rgba ;
	}

	protected abstract Vector[] getQuadBillboard() throws EmitterIsNotRenderableException;

	protected abstract Texture getTexture() throws EmitterIsNotRenderableException;

	public boolean isDrawable() {return true;}

}
