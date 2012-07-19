package com.biu.cg.main;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLException;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.sprites.Shockwave;
import com.biu.cg.objects3d.particles.sprites.FlameEmitter;
import com.biu.cg.objects3d.particles.sprites.FlashEmitter;
import com.biu.cg.objects3d.particles.sprites.GlintEmitter;
import com.biu.cg.objects3d.particles.sprites.SmokeEmitter;
import com.biu.cg.objects3d.particles.sprites.SparkEmitter;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.biu.cg.sound.SoundPlayer;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public class Explosion {
	private static Texture shockwaveTex;

	public Explosion(Vector pos, Orientation camera, boolean withShockwave) {
		if(withShockwave){
			Sprite shockwave = new Shockwave(shockwaveTex, pos);
			Sprite.registerObject(shockwave);
		}
		new FlashEmitter(pos, camera, 10);
		new FlameEmitter(pos, camera, 40);
		new SmokeEmitter(pos, camera, 50);
		new SparkEmitter(pos, camera, 100);
		new GlintEmitter(pos, camera, 20); //round sparks
		
		SoundPlayer.explosion();
	}

	public static void init() {
		FlashEmitter.init();
		FlameEmitter.init();
		SmokeEmitter.init();
		SparkEmitter.init();
		GlintEmitter.init();
		try {
			shockwaveTex = TextureIO.newTexture(new File( "textures/shockwave_128X128.png" ),false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
