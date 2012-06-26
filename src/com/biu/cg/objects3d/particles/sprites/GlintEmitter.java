package com.biu.cg.objects3d.particles.sprites;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GLException;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class GlintEmitter extends SpriteEmitter {

	private static Texture glintTex;

	public GlintEmitter(Vector pos, Orientation camera, int fieryGlints) {
		super(pos, camera);
		for (int i = 0; i < fieryGlints; i++) {
			Sprite.registerObject(new Glint(glintTex, new Vector(pos).noise(2), camera));
		}
	}

	@Override //NOT USED
	public boolean isDead() {return true;}

	@Override
	protected void update() {/*NOT USED*/}

	public static void init() {
		try {
			glintTex = TextureIO.newTexture(new File( "textures/flame_smoke_1_128X128.png" ),false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
