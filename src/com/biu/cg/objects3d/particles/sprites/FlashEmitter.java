package com.biu.cg.objects3d.particles.sprites;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLException;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class FlashEmitter extends SpriteEmitter {

	public FlashEmitter(Vector position, Orientation camera, int flashes, float size) {
		super(position, camera);
		for (int i = 0; i < flashes; i++) {
			Sprite.registerObject(new Flash(flashTex[random.nextInt(4)], new Vector(position).noise(2), camera, size));
		}
	}

	private static Texture[] flashTex;

	public static void init() {
		try {
			flashTex = new Texture[4];
			flashTex[0] = TextureIO.newTexture(new File( "textures/flash_burst_1_128X128.png" ),false);
			flashTex[1] = TextureIO.newTexture(new File( "textures/flash_burst_2_128X128.png" ),false);
			flashTex[2] = TextureIO.newTexture(new File( "textures/flash_burst_3_128X128.png" ),false);
			flashTex[3] = TextureIO.newTexture(new File( "textures/flash_burst_4_128X128.png" ),false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override //NOT USED
	public boolean isDead() {return true;}

	@Override
	protected void update() {/*NOT USED*/}

}
