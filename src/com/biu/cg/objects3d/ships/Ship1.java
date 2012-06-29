package com.biu.cg.objects3d.ships;

import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.main.Game;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Model3D;
import com.owens.oobjloader.builder.Face;

public class Ship1 extends Model3D {

	private Orientation cam;
	
	
	public Ship1(Orientation camera) {
		
		super("models/ship1/ship.obj" , "models/ship1/F02_512.jpg");
		this.cam = camera;	
	}
	
	public Ship1(Vector position, Orientation camera) {
		super(position, "models/ship1/ship.wng" , "models/ship1/F02_512.jpg");
		this.cam = camera;
		
	}
	
	public void lookAtCamera1(GLAutoDrawable gLDrawable){
		final GL gl = gLDrawable.getGL();
		Game.glu.gluPerspective(50f, 2, 2, 5000.0);
		
		
		Vector camPos = orientation.getPosition();
		Vector target = orientation.getTargetLookAtVector();
		Vector upVect = orientation.getUpVector();
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		Game.glu.gluLookAt(camPos.getX(), camPos.getY(), camPos.getZ(), 
					  target.getX(), target.getY(), target.getZ(), 
					  upVect.getX(), upVect.getY(), upVect.getZ());
	}

}
