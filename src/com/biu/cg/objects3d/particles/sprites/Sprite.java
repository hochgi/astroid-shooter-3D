package com.biu.cg.objects3d.particles.sprites;

import java.util.Collections;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Particle;
import com.sun.opengl.util.texture.Texture;

/**
 * this abstract class deals with, well... sprites.
 * all other classes in this package inherit Sprite.
 * i won't explain for every Sprite, since the Sprites
 * differ only on the color/alpha/age/size/etc'... specs.
 * here is where i'll explain most of the Sprite related issues.
 *  
 * @author gilad
 *
 */
public abstract class Sprite extends Particle implements Comparable<Sprite> {

	/*
	 * static lists belong to a common pattern we used throughout the whole program.
	 * when dealing with concurrency issues, it is better to separate the actions,
	 * different threads need to perform on the collections...
	 */
	private static LinkedList<Sprite> sprites = new LinkedList<Sprite>();
	private static LinkedList<Sprite> graveyard = new LinkedList<Sprite>();
	private static LinkedList<Sprite> newlyBorn = new LinkedList<Sprite>();
	private static Object nLock = new Object();
	//as default, all sprites have this rgba, which can be override
	private static final float[] rgba = {1f,1f,1f,1f};
	
	private Orientation cam;

	/**
	 * adding sprites to the rendering & updating loop
	 * @param particle
	 */
	public static void registerObject(Sprite sprite){
		Particle.registerObject(sprite);
		synchronized(nLock) {
			newlyBorn.add(sprite);
		}
	}
	
	/**
	 * method renders the sprites...
	 * should be invoked inside OpenGL display method.
	 * @param gLDrawable
	 */
	public static void renderSprites(GLAutoDrawable gLDrawable) {
		//first, dump dead sprites:
		sprites.removeAll(graveyard);
		graveyard.clear();
		//and add new ones:
		synchronized(nLock) {
			sprites.addAll(newlyBorn);
			newlyBorn.clear();
		}
		//sort particles by (used to bes quare, but now it's:) 'z' distance from camera
		//further away should be in the head of the list. (renders first)
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
				//render:
				s.draw(gLDrawable);
			}
		}

		gl.glDisable(GL.GL_BLEND);
	}
	
	/**
	 * simple constructor
	 * @param position
	 * @param camera
	 */
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

	/**
	 * this is the most important and complex method.
	 * the miracle happens here. read carefully.
	 * (renders this sprite)
	 */
	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		// we don't want heavy computation to be performed when unneeded
		// also, it can cause weird stuff we don't want to see... 
		if(outOfView()){
			return;
		}
		
		// each sprite has a different blending function.
		changeBlendingFunc(gLDrawable);
		
		// every sprite has to compute a quad "billboard" to render it's texture on it.
		// we get this with the method getQuadBillboard, and the conversion makes the
		// billboard to "sit" on the plane that is perpendicular to the camera, and
		// goes through the sprite position.  
		Vector[] bb = convertToOrthogonalToCameraPlane(getQuadBillboard());
		GL gl = gLDrawable.getGL();
		
		// sprites take care of their own texture
	    getTexture().bind();
	    
	    // sprite colors is given through thos method
	    float[] rgba = getRGBA();
	    gl.glColor4f(rgba[0],rgba[1],rgba[2],rgba[3]);
	    //draw the sprite on the computed quad
	    gl.glBegin(GL.GL_QUADS);
	    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3d(bb[0].x, bb[0].y, bb[0].z);
	    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3d(bb[1].x, bb[1].y, bb[1].z);
	    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3d(bb[2].x, bb[2].y, bb[2].z);
	    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3d(bb[3].x, bb[3].y, bb[3].z);
	    gl.glEnd();
	}
	
	/**
	 * just some linear algebra magic to determine if the sprite is on the front
	 * size of the camera, or on the back....
	 * @return
	 */
	private boolean outOfView() {
		Vector dir1 = getPosition().sub(cam.getPosition(),1).normalize();
		Vector dir2 = dir1.sub(dir1.projectionOnPlane(cam.getAxis('z')),1).normalize();

		return dir1.subMutate(dir2, 1).sqrDistanceTo(new Vector(0,0,0)) > 0.5f;
	}

	
	/**
	 * what is wanted, is for the sprites to not cut each other off.
	 * so we simulate the look of the sprite on a different plane.
	 * we got the idea from 3D street art.
	 * for example, this drawing looks great, like 3D:
	 * http://rense.com/1.imagesH/artt13.jpg
	 * but actually, it's stretched out, see it from a different angle here:
	 * http://rense.com/1.imagesH/artt12.jpg
	 * so basically, we converted a "real" sprite, to one that is fake,
	 * but is seen correctly from where the camera stands.
	 * also, when the sprites were "real", we needed square distance from the camera
	 * to compare (for the sorting - still, OpenGL is just a shader,
	 * and Z-Order makes difference...), after the fake conversion,
	 * we realized we needed to implement a 'z' distance, and not square distance.
	 * (implemented and used in the compare method)
	 * anyway, the idea of the implementation is explained nicely here:
	 * http://local.wasp.uwa.edu.au/~pbourke/geometry/planeline/
	 * @param qbb
	 * @return
	 */
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
	 * you may override the following method for different color & transparency rendering.
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

	/**
	 * retrieve the sprite's texture
	 * @return
	 */
	protected abstract Texture getTexture();
}
