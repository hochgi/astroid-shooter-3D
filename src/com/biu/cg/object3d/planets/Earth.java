package com.biu.cg.object3d.planets;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class Earth extends Model3D implements Collidable {

	private BoundingSphere bs;
	private Orientation camera;
	private static Texture tex;
	static {
		try {
			tex = TextureIO.newTexture(new File( "textures/atmosphere_256X256.png" ),false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	public Earth(Orientation camera) {
		super(new Vector(3000, 0 , 0),"models/earth/earth.wng" , "models/earth/earth.jpg");
		Collidables.registerObject(new Atmosphere(this));
		this.bs = new BoundingSphere(getVertices() , 10 , 1);
		this.camera = camera;
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		float size = bs.getRadius() * 1.75f;
		Vector[] bb = camera.getOrthogonalQuadAtPosition(getPosition(), size);
		GL gl = gLDrawable.getGL();
		gl.glEnable(GL.GL_BLEND);	
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);	
	    tex.bind();
	    gl.glColor4f(0.8f,0.9f,1,0.6f);
	    gl.glBegin(GL.GL_QUADS);
	    gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3d(bb[0].x, bb[0].y, bb[0].z);
	    gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3d(bb[1].x, bb[1].y, bb[1].z);
	    gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3d(bb[2].x, bb[2].y, bb[2].z);
	    gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3d(bb[3].x, bb[3].y, bb[3].z);
	    gl.glEnd();
	    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);	
	    gl.glDisable(GL.GL_BLEND);	
		super.synchronizedDraw(gLDrawable);
	}
	
	@Override
	public void collisionAction(Collidable collidable) {
		
	}

	@Override
	public BoundingShape getBoundingShape() {
		return bs;
	}
	
	@Override
	public Type getType() {
		return Type.EARTH;
	}

	@Override
	protected void update() {
		orientation.rotateHeading(0.001f);
	}
	
	

}
