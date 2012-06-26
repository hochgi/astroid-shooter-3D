package com.biu.cg.objects3d.particles.sprites;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GLException;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class SparkEmitter extends SpriteEmitter {

	private static Texture sparkTex;
	
	public SparkEmitter(Vector position, Orientation camera, int sparks) {
		super(position, camera);
		Vector direction = null;
		for (int i = 0; i < sparks; i++) {			
			direction = new Vector(position).noise(2);
			/* uncomment if always orthogonal to ground (XZ plane)
			if(direction.getY() < 0) {
				direction.neg('y');
			}*/
			Sprite.registerObject(new Spark(sparkTex, new Vector(position), direction, camera));
		}
	}

	@Override //NOT USED
	public boolean isDead() {return true;}

	@Override
	protected void update() {/*NOT USED*/}

	public static void init() {
		try {
			sparkTex = TextureIO.newTexture(new File( "textures/flying_spark_128X16.png" ),false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
