package com.biu.cg.objects3d.particles.sprites;

import java.util.Collections;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Particle;
import com.sun.opengl.util.texture.Texture;


public abstract class Sprite extends Particle implements Comparable<Sprite> {

	private static LinkedList<Sprite> newlyBorn = new LinkedList<Sprite>();
	private static LinkedList<Sprite> particles = new LinkedList<Sprite>();
	private static LinkedList<Sprite> graveyard = new LinkedList<Sprite>();
	private static Object pLock = new Object();
	private static final float[] rgba = {1f,1f,1f,1f};
	private Orientation cam;

	/**
	 * order of adding is important!
	 * add further objects first!!!
	 * @param particle
	 */
	public static void registerObject(Sprite sprite){
		synchronized(pLock) {
			particles.addLast(sprite);
		}
	}
	
	public static void updateSprites() {
		synchronized(pLock) {
			particles.removeAll(graveyard);
			graveyard.clear();
			
			for (Sprite s : particles) {
				if(s.isDead()){
					graveyard.add(s);
				}
				else {
					s.update();
				}
			}
		}
	}
	
	public static void renderSprites(GLAutoDrawable gLDrawable) {
		synchronized(pLock) {
			Collections.sort(particles);
			
			GL gl = gLDrawable.getGL();
	        // Enable blending and specify blending function.
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);			
			
			for (Sprite s : particles) {
				s.draw(gLDrawable);
			}
	
			gl.glDisable(GL.GL_BLEND);
		}
	}
	
	public static void registerObjectForNextIteration(Sprite sprite) {
		newlyBorn.addFirst(sprite);
	}
	
	public Sprite(Vector position, Orientation camera) {
		super(position);
		this.cam = camera;
	}
	
	@Override
	public int compareTo(Sprite o) {
		double diff = cam.getPosition().sqrDistanceTo(o.getPosition())-cam.getPosition().sqrDistanceTo(getPosition());
		int rv = (int)diff;
		if(rv != 0){
			return rv;
		}
		else if(diff > 0) {
			return 1;
		}
		else if(diff < 0){
			return -1;
		}
		else{
			return 0;
		}
	}
	
	public Orientation getCam() {
		return cam;
	}

	public void setCam(Orientation cam) {
		this.cam = cam;
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
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
	}
	
	/**
	 * you may override the following method for different rendering.
	 * @return
	 */
	protected float[] getRGBA() {
		return rgba ;
	}

	protected abstract Vector[] getQuadBillboard();

	protected abstract Texture getTexture();
}
