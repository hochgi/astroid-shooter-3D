package com.biu.cg.objects3d.particles.sprites;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GLException;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class SmokeEmitter extends SpriteEmitter {

	private static Texture[] smokeTrailTex;

	public SmokeEmitter(Vector position, Orientation camera, int trails) {
		super(position, camera);
		Vector direction = null;
		for (int i = 0; i < trails; i++) {
			/* uncomment if always orthogonal to ground
			direction = new Vector(position).noise(2);
			if(direction.getY() < 0) {
				direction.neg('y');
			}
			*/
			Sprite.registerObject(new SmokeTrail(smokeTrailTex[random.nextInt(4)],
								  direction, new Vector(position), camera));
		}
	}

	@Override //NOT USED
	public boolean isDead() {return true;}

	@Override
	protected void update() {/*NOT USED*/}

	public static void init() {
		try {
			smokeTrailTex = new Texture[4];
			smokeTrailTex[0] = TextureIO.newTexture(new File( "textures/smoke_trail_1_256X64.png" ),false);
			smokeTrailTex[1] = TextureIO.newTexture(new File( "textures/smoke_trail_2_256X64.png" ),false);
			smokeTrailTex[2] = TextureIO.newTexture(new File( "textures/smoke_trail_3_256X64.png" ),false);
			smokeTrailTex[3] = TextureIO.newTexture(new File( "textures/smoke_trail_4_256X64.png" ),false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
