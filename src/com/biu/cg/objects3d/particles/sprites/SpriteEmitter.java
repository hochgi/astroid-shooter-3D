package com.biu.cg.objects3d.particles.sprites;

import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;

public abstract class SpriteEmitter extends Sprite {
	
	public SpriteEmitter(Vector position) {
		super(position);
	}

	private static LinkedList<Sprite> newlyBorn = new LinkedList<Sprite>();
	private static LinkedList<Sprite> particles = new LinkedList<Sprite>();
	private static LinkedList<Sprite> graveyard = new LinkedList<Sprite>();
	private static Object pLock = new Object();

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
			particles.addAll(0, newlyBorn);
			graveyard.clear();
			newlyBorn.clear();
			
			for (Sprite s : particles) {
				if(s.isDead()){
					graveyard.add(s);
				}
				else {
					s.evolve();
				}
			}
		}
	}
	
	public static void renderSprites(GLAutoDrawable gLDrawable) {
		synchronized(pLock) {
			GL gl = gLDrawable.getGL();
	        // Enable blending and specify blending function.
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			
			for (Sprite s : particles) {
				if(s.isDrawable()) {
					s.draw(gLDrawable);
				}
			}
	
			gl.glDisable(GL.GL_BLEND);
		}
	}
	
	public static void registerObjectForNextIteration(Sprite sprite) {
		newlyBorn.addFirst(sprite);
	}

	@Override
	protected Vector[] getQuadBillboard() throws EmitterIsNotRenderableException {
		throw new EmitterIsNotRenderableException();
	}

	@Override
	protected Texture getTexture() throws EmitterIsNotRenderableException {
		throw new EmitterIsNotRenderableException();
	}
	
	@Override
	public boolean isDrawable() {return false;}
}
