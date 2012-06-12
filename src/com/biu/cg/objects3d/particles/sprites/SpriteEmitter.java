package com.biu.cg.objects3d.particles.sprites;

import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Particle;

public abstract class SpriteEmitter extends Particle{
	
	public SpriteEmitter(Vector position) {
		super(position);
	}

	private static LinkedList<Sprite> particles = new LinkedList<Sprite>();
	private static LinkedList<Sprite> graveyard = new LinkedList<Sprite>();

	/**
	 * order of adding is important!
	 * add further objects first!!!
	 * @param particle
	 */
	public static void registerObject(Sprite sprite){
		particles.addLast(sprite);
	}
	
	public static void updateSprites() {
		particles.removeAll(graveyard);
		graveyard.clear();
		
		for (Sprite s : particles) {
			if(s.isDead()){
				graveyard.add(s);
			}
			else {
				s.evolve();
			}
		}
	}
	
	public static void renderSprites(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
        // Enable blending and specify blending function.
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		
		for (Sprite s : particles) {
			s.draw(gLDrawable);
		}

		gl.glDisable(GL.GL_BLEND);
	}
	
	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {}


}
