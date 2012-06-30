package com.biu.cg.objects3d.ships;

import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.Space;

public abstract class Ship extends Model3D {
	private float pTheta = (float)Math.toRadians(1.5);
	//negative angle (in radians)
	private float nTheta = ((float)Math.PI * 2f) - pTheta;
	private float step = 4f;
	private Space space;
	
	public Ship(Vector position ,String objFile, String texture) {
		super(position, objFile , texture);
		
		space = new Space("models/space/space.wng" , "models/space/space.jpg");
		space.setScale(4000);
	}
	
	public Ship(String objFile, String texture) {
		super(objFile, texture);
		
		space = new Space("models/space/space.wng" , "models/space/space.jpg");
		space.setScale(4000);
		// TODO Auto-generated constructor stub
	}

	
	
	public void rollRight(){
		orientation.rotateRoll(nTheta);
		space.getOrientation().rotateRoll(nTheta);
	}
	
	public void rollLeft(){
		orientation.rotateRoll(pTheta);
		space.getOrientation().rotateRoll(pTheta);
	}
	
	public void pitchUp(){
		orientation.rotatePitch(pTheta);
		space.getOrientation().rotatePitch(pTheta);
	}
	
	public void pitchDown(){
		orientation.rotatePitch(nTheta);
		space.getOrientation().rotatePitch(nTheta);
	}
	
	public void moveForward(){
		orientation.translateForward(step);
		space.getOrientation().translateForward(step);
	}
	
	public void moveBackward(){
		orientation.translateBackward(step);
		space.getOrientation().translateBackward(step);
	}
	
	public void turnRight(){
		orientation.rotateHeading(pTheta);
		space.getOrientation().rotateHeading(pTheta);
	}
	
	public void turnLeft(){
		orientation.rotateHeading(nTheta);
		space.getOrientation().rotateHeading(nTheta);
	}
	
	public void moveRight(){
		
		
		orientation.translateRightward(step);
		space.getOrientation().translateRightward(step);
	}
	
	public void moveLeft(){
		orientation.translateLeftward(step);
		space.getOrientation().translateLeftward(step);
	}
	
	public void moveUp(){
		orientation.translateUpward(step);
		space.getOrientation().translateUpward(step);
	}
	
	public void moveDown(){
		orientation.translateDownward(step);
		space.getOrientation().translateDownward(step);
	}
	
	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		space.draw(gLDrawable);
		super.synchronizedDraw(gLDrawable);
	}
	
}
