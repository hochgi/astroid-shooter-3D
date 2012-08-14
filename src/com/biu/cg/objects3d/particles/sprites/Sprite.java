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

	private static LinkedList<Sprite> sprites = new LinkedList<Sprite>();
	private static LinkedList<Sprite> graveyard = new LinkedList<Sprite>();
	private static LinkedList<Sprite> newlyBorn = new LinkedList<Sprite>();
	private static Object nLock = new Object();
	private static final float[] rgba = {1f,1f,1f,1f};
	
	private Orientation cam;

	/**
	 * order of adding is important!
	 * add further objects first!!!
	 * @param particle
	 */
	public static void registerObject(Sprite sprite){
		Particle.registerObject(sprite);
		synchronized(nLock) {
			newlyBorn.add(sprite);
		}
	}
	
	public boolean isPerpendicularToCamera() {
		return true;
	}
	
	public static void renderSprites(GLAutoDrawable gLDrawable) {
		sprites.removeAll(graveyard);
		graveyard.clear();
		synchronized(nLock) {
			sprites.addAll(newlyBorn);
			newlyBorn.clear();
		}
		//sort particles by square distance from camera
		//further away should be in the head of the list
		Collections.sort(sprites);
		
		GL gl = gLDrawable.getGL();
        // Enable blending
		//blending function will be invoked differently for every sprite
		gl.glEnable(GL.GL_BLEND);		
		
		for (Sprite s : sprites) {
			if(s.isDead()){
				graveyard.add(s);
			}
			else {
				s.draw(gLDrawable);
			}
		}

		gl.glDisable(GL.GL_BLEND);
	}
	
	public Sprite(Vector position, Orientation camera) {
		super(position);
		this.cam = camera;
	}
	
	@Override
	public int compareTo(Sprite o) {
		//old implementation:
//		float diff = cam.getPosition().sqrDistanceTo(o.getPosition())-cam.getPosition().sqrDistanceTo(getPosition());
		//new generic to be implemented solution:
//		Vector zVec = cam.getAxis('z');
//		float diff = cam.getPosition().vecDistanceTo(o.getPosition(), zVec)-cam.getPosition().vecDistanceTo(getPosition(), zVec);
		//or, the new implementaion:
		float diff = cam.zedDistanceTo(o.getPosition())-cam.zedDistanceTo(getPosition());
		int rv = (int)diff;
		if(rv != 0){
			return rv;
		}
//		else if(isPerpendicularToCamera() && !o.isPerpendicularToCamera()) {
//			return 1;
//		}
//		else if(!isPerpendicularToCamera() && o.isPerpendicularToCamera()) {
//			return -1;
//		}
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
		changeBlendingFunc(gLDrawable);
		Vector[] bb = convertToOrthogonalToCameraPlane(getQuadBillboard());
		GL gl = gLDrawable.getGL();
		
	    getTexture().bind();
	    float[] rgba = getRGBA();
	    gl.glColor4f(rgba[0],rgba[1],rgba[2],rgba[3]);
	    gl.glDisable(GL.GL_LIGHTING);
	    gl.glBegin(GL.GL_QUADS);
	    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3d(bb[0].x, bb[0].y, bb[0].z);
	    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3d(bb[1].x, bb[1].y, bb[1].z);
	    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3d(bb[2].x, bb[2].y, bb[2].z);
	    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3d(bb[3].x, bb[3].y, bb[3].z);
	    gl.glEnd();
	    gl.glEnable(GL.GL_LIGHTING);
	}
	
	//the idea of the implementation is explained nicely here: http://local.wasp.uwa.edu.au/~pbourke/geometry/planeline/
	private Vector[] convertToOrthogonalToCameraPlane(Vector[] qbb) {
		if(qbb.length < 4){
			//throw new ArrayIsTooSmallException("billboard needs to have 4 vertices in it")
		}
		
		Vector[] rv = new Vector[4];
		Vector N = cam.getAxis('z');
		Vector P1 = cam.getPosition();
		Vector P2 = null;
		Vector P3 = getPosition();
		float u = 0;

		{
			P2 = qbb[0];
			u = N.dot(P3.sub(P1, 1)) / N.dot(P2.sub(P1, 1));
			rv[0] = P1.add(P2.subMutate(P1, 1).mulMutate(u), 1);
		}{
			P2 = qbb[1];
			u = N.dot(P3.sub(P1, 1)) / N.dot(P2.sub(P1, 1));
			rv[1] = P1.add(P2.subMutate(P1, 1).mulMutate(u), 1);
		}{
			P2 = qbb[2];
			u = N.dot(P3.sub(P1, 1)) / N.dot(P2.sub(P1, 1));
			rv[2] = P1.add(P2.subMutate(P1, 1).mulMutate(u), 1);
		}{
			P2 = qbb[3];
			u = N.dot(P3.sub(P1, 1)) / N.dot(P2.sub(P1, 1));
			rv[3] = P1.add(P2.subMutate(P1, 1).mulMutate(u), 1);
		}
		return rv;
	}

	/**
	 * you may override the following method for different blending.
	 * @return
	 */
	protected void changeBlendingFunc(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);	
	}

	/**
	 * you may override the following method for different rendering.
	 * @return
	 */
	protected float[] getRGBA() {
		return rgba ;
	}

	/**
	 * order of the points is very important:
	 * v[0] will be the texture coordinate of (0,0)
	 * v[1] will be the texture coordinate of (1,0)
	 * v[2] will be the texture coordinate of (1,1)
	 * v[3] will be the texture coordinate of (0,1)
	 * @return
	 */
	protected abstract Vector[] getQuadBillboard();

	protected abstract Texture getTexture();
}
