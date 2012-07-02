package com.biu.cg.objects3d.ships;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.main.Game;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.Space;

public abstract class Ship extends Model3D {
	private float pTheta = (float)Math.toRadians(1.5);
	//negative angle (in radians)
	private float nTheta = ((float)Math.PI * 2f) - pTheta;
	private float step = 4f;
	private Space space;
	private int wing = 1;
	private Vector lookAt;
	private boolean active = false;
	
	public Ship(Vector position ,String objFile, String texture) {
		super(position, objFile , texture);
		lookAt = new Vector(orientation.getAxis('z'));
		space = new Space("models/space/space.wng" , "models/space/space.jpg");
		space.setScale(4000);
	}
	
	public Ship(String objFile, String texture) {
		super(objFile, texture);
		space = new Space("models/space/space.wng" , "models/space/space.jpg");
		space.setScale(4000);
	}

	public void setActive(boolean b) {
		active = b;
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
		synchronized(lock) {
			orientation.rotateHeading(nTheta);
			space.getOrientation().rotateHeading(nTheta);
		}
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
		super.synchronizedDraw(gLDrawable);
		if(active){
			space.draw(gLDrawable);
		}
	}
	
	public Vector getWingPosition() {
		float w = getWingWidth();
		float h = getWingHeight();
		wing *= -1;
		Vector x = getOrientation().getAxis('x');
		Vector y = getOrientation().getAxis('y');
		return getPosition().add(x, wing*w).addMutate(y, h);
	}
	
	public abstract float getWingHeight();

	public abstract float getWingWidth();

	@Override
	protected void update() {
		Vector z = orientation.getAxis('z');
		lookAt.addMutate(z, 0.25f).normalize();
	}
	
	public void lookAtCamera1(GLAutoDrawable gLDrawable){
		final GL gl = gLDrawable.getGL();
		Game.glu.gluPerspective(50f, 2, 2, 5000.0);
		
		
		Vector camPos = orientation.getPosition();
		Vector target = lookAt.add(getPosition(), 1);
		Vector upVect = orientation.getUpVector();
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		Game.glu.gluLookAt(camPos.getX(), camPos.getY(), camPos.getZ(), 
					  target.getX(), target.getY(), target.getZ(), 
					  upVect.getX(), upVect.getY(), upVect.getZ());
	}
	
}
