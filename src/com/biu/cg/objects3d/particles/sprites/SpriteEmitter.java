package com.biu.cg.objects3d.particles.sprites;

import java.util.LinkedList;
import java.util.Random;

import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.Particle;

public abstract class SpriteEmitter extends Particle {
	
	private final Orientation cam;

	public SpriteEmitter(Vector position, Orientation camera) {
		super(position);
		this.cam = camera;
	}

	public Orientation getCamera() {
		return cam;
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {/*NOT USED! EMITTER IS NOT A RENDERABLE OBJECT!*/}
	
	protected static Random random = new Random();
	private static Object eLock = new Object();
	private static LinkedList<SpriteEmitter> sEmitters = new LinkedList<SpriteEmitter>();;
	private static LinkedList<SpriteEmitter> graveyard = new LinkedList<SpriteEmitter>();

	public static void registerObject(SpriteEmitter emitter){
		synchronized(eLock) {
			sEmitters.add(emitter);
		}
	}

	public static void updateSpriteEmitters() {
		synchronized(eLock) {
			sEmitters.removeAll(graveyard);
			graveyard.clear();
			
			for (SpriteEmitter se : sEmitters) {
				if(se.isDead()){
					graveyard.add(se);
				}
				else {
					se.update();
				}
			}
		}
	}
}
