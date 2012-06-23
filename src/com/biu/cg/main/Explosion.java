package com.biu.cg.main;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLException;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.particles.sprites.Flash;
import com.biu.cg.objects3d.particles.sprites.Shockwave;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public class Explosion {
	private static Texture shockwaveTex;
	private static Texture flash1Tex;
	private static Texture flash2Tex;
	private static Texture flash3Tex;
	private static Texture flash4Tex;

	public Explosion(Vector pos, Orientation camera) {
		Vector raise = new Vector(0,1,0);
		Sprite shockwave = new Shockwave(shockwaveTex, pos);
		Sprite.registerObject(shockwave);

		Sprite flash1 = new Flash(flash1Tex, pos.add(raise, 5).noise(2), camera);
		Sprite flash2 = new Flash(flash2Tex, pos.add(raise, 5).noise(2), camera);
		Sprite flash3 = new Flash(flash3Tex, pos.add(raise, 5).noise(2), camera);
		Sprite flash4 = new Flash(flash4Tex, pos.add(raise, 5).noise(2), camera);
		
		Sprite.registerObject(flash1);
		Sprite.registerObject(flash2);
		Sprite.registerObject(flash3);
		Sprite.registerObject(flash4);
	}

	public static void init() {
		try {
			shockwaveTex = TextureIO.newTexture(new File( "textures/shockwave_128X128.png" ),false);
			flash1Tex = TextureIO.newTexture(new File( "textures/flash_1_128X128.png" ),false);
			flash2Tex = TextureIO.newTexture(new File( "textures/flash_2_128X128.png" ),false);
			flash3Tex = TextureIO.newTexture(new File( "textures/flash_3_128X128.png" ),false);
			flash4Tex = TextureIO.newTexture(new File( "textures/flash_4_128X128.png" ),false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
