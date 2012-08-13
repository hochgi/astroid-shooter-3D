package com.biu.cg.objects3d.particles.sprites;

import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLException;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class FlameEmitter extends SpriteEmitter {

	private static Texture[] flameTex;

	public FlameEmitter(Vector pos, Orientation camera, int flames, float size) {
		super(pos, camera);
		for (int i = 0; i < flames; i++) {
			Sprite.registerObject(new Flame(flameTex[random.nextInt(4)], new Vector(pos).noise(2), camera, size));
		}
	}

	public static void init() {
		try {
			flameTex = new Texture[4];
			flameTex[0] = TextureIO.newTexture(new File( "textures/flame_smoke_1_128X128.png" ),false);
			flameTex[1] = TextureIO.newTexture(new File( "textures/flame_smoke_2_128X128.png" ),false);
			flameTex[2] = TextureIO.newTexture(new File( "textures/flame_smoke_3_128X128.png" ),false);
			flameTex[3] = TextureIO.newTexture(new File( "textures/flame_smoke_4_128X128.png" ),false);
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
