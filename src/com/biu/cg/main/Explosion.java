package com.biu.cg.main;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLException;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.sprites.FlameEmitter;
import com.biu.cg.objects3d.particles.sprites.FlashEmitter;
import com.biu.cg.objects3d.particles.sprites.Shockwave;
import com.biu.cg.objects3d.particles.sprites.SmokeEmitter;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public class Explosion {
	private static Texture shockwaveTex;

	public Explosion(Vector pos, Orientation camera) {
		Sprite shockwave = new Shockwave(shockwaveTex, pos);
		Sprite.registerObject(shockwave);
		new FlashEmitter(pos, camera, 6);
		new FlameEmitter(pos, camera, 6);
		new SmokeEmitter(pos, camera, 20);
		//new SparkEmitter(pos, camera, 20);
		//new GlintEmitter(pos, camera, 3); //round sparks
	}

	public static void init() {
		FlashEmitter.init();
		FlameEmitter.init();
		SmokeEmitter.init();
		try {
			shockwaveTex = TextureIO.newTexture(new File( "textures/shockwave_128X128.png" ),false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
