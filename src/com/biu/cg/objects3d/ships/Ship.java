package com.biu.cg.objects3d.ships;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.main.Exercise4;
import com.biu.cg.main.Game;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.Space;

public abstract class Ship extends Model3D {
	private float pTheta = (float)Math.toRadians(1.5);
	//negative angle (in radians)
	private float nTheta = ((float)Math.PI * 2f) - pTheta;
	private float step = 1f;
	private float turboStep = 8f;
	private Space space;
	private int wing = 1;
	private Vector lookAt;
	private boolean active = false;
	private boolean rotateLock = true;
	private boolean moveLock = true;
	protected int fuel=600;
	
	protected boolean canMove=true;
	protected int speed=0;
	
	protected final float baseHealth=100;
	protected float health=baseHealth;
	
	private float perspective = 50f;
	protected int rocketCounter=3;

	
	/**
	 * get the amount of rockets that the ship has.
	 * @return
	 */
	public int getRocketCounter() {
		return rocketCounter;
	}
	
	/**
	 * removes a rocket.
	 */
	public void removeRocket(){
		rocketCounter--;
	}
	
	/**
	 * set the number of rockets.
	 * @param rocketCounter
	 */
	public void setRocketCounter(int rocketCounter) {
		this.rocketCounter = rocketCounter;
	}
	
	/**
	 * c'tor.
	 * @param position
	 * @param objFile
	 * @param texture
	 */
	public Ship(Vector position ,String objFile, String texture) {
		super(position, objFile , texture);
		lookAt = new Vector(orientation.getAxis('z'));
		space = new Space("models/space/space.wng" , "models/space/space.jpg", getOrientation());
		space.setScale(4000);
	}
	
	/**
	 * reset location.
	 * @param xf
	 * @param yf
	 * @param zf
	 */
	public void reset(float xf, float yf, float zf) {
		getOrientation().reset(xf,yf,zf,
							   1f,0f,0f,
							   0f,1f,0f,
							   0f,0f,1f);
	}
	
	
	/**
	 * disable smooth camera rotation.
	 */
	public synchronized void disableRotateUpdate() {
		rotateLock  = false;
	}
	
	/**
	 * enable smooth camera rotation.
	 */
	public synchronized void enableRotateUpdate() {
		rotateLock  = true;
	}
	
	/**
	 * disable smooth camera movement.
	 */
	public synchronized void disableMoveUpdate() {
		moveLock  = false;
	}
	
	/**
	 * enable smooth camera movement.
	 */
	public synchronized void enableMoveUpdate() {
		moveLock  = true;
	}
	
	/**
	 * c'tor.
	 * @param objFile
	 * @param texture
	 */
	public Ship(String objFile, String texture) {
		super(objFile, texture);
		space = new Space("models/space/space.wng" , "models/space/space.jpg", getOrientation());
		space.setScale(4000);
	}

	/**
	 * set if this ship is player's ship.
	 * @param b
	 */
	public void setActive(boolean b) {
		active = b;
	}
	
	
	//////////////////////////////////// Movement methods ///////////////////////////////////
	public void rollRight(){
		orientation.rotateRoll(nTheta);
	}
	
	public void rollLeft(){
		orientation.rotateRoll(pTheta);
	}
	
	public void pitchUp(){
		innerUpdate();
		orientation.rotatePitch(pTheta);
	}
	
	public void pitchDown(){
		innerUpdate();
		orientation.rotatePitch(nTheta);
	}
	
	public void moveForward(){
		orientation.translateForward(step*speed);
	}
	
	public void turboForward(){
		if(fuel>0){	
			perspective = Math.min(perspective+1, 80f);
			orientation.translateForward(turboStep);
		}
	}
	public void moveBackward(){
		orientation.translateBackward(step);
	}
	
	public void turnRight(){
		innerUpdate();
		orientation.rotateHeading(pTheta);
	}
	
	public void turnLeft(){
		innerUpdate();
		orientation.rotateHeading(nTheta);
	}
	
	public void moveRight(){
		orientation.translateRightward(step);
	}
	
	public void moveLeft(){
		orientation.translateLeftward(step);
	}
	
	public void moveUp(){
		orientation.translateUpward(step);
	}
	
	public void moveDown(){
		orientation.translateDownward(step);
	}
	
////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * increase speed.
 	 */
	public void speedUp(){
		speed = Math.min(speed+1, 4);
	}
	
	/**
	 * decrease speed.
	 */
	public void speedDown(){
		speed = Math.max(speed-1, 0);
	}
	
	/**
	 * draw the space sphere.
	 * @param gLDrawable
	 */
	public void drawSpace(GLAutoDrawable gLDrawable){
		if(active){
			space.draw(gLDrawable);
		}
	}
	
	/**
	 * get the wings position. (used to determine the location of the shot launch).
	 * @return
	 */
	public Vector getWingPosition() {
		float w = getWingWidth();
		float h = getWingHeight();
		wing *= -1;
		Vector x = getOrientation().getAxis('x');
		Vector y = getOrientation().getAxis('y');
		return getPosition().add(x, wing*w).addMutate(y, h);
	}
	
	/**
	 * get wing height
	 * @return
	 */
	public abstract float getWingHeight();

	/**
	 * get wing width
	 * @return
	 */
	public abstract float getWingWidth();
	
	
	/**
	 * in case you want a sequential update
	 * for your class at given intervals, 
	 * implement the specific update as this method.
	 * it will be invoked by a timer task that runs
	 * every 40 ms.
	 */
	@Override
	protected synchronized void update() {
		// smooth camera movement and panel updates ///////
		if(rotateLock){
			innerUpdate();
		}
		if(moveLock || fuel==0){
			perspective = Math.max(perspective-1f, 50f);
			Exercise4.speedPanel.setSpeed(speed);
			Exercise4.speedPanel.repaint();
			
		}
		else{	
			Exercise4.speedPanel.setSpeed(5);
			Exercise4.speedPanel.repaint();
			fuel = Math.max(fuel-1, 0);
			
			if(fuel!=0)
				Exercise4.fuelPanel.setFuel((fuel/100)+1);
			else
				Exercise4.fuelPanel.setFuel(0);
			Exercise4.fuelPanel.repaint();
		}	
		///////////////////////////////////////////////////
		
		// auto health restore.
		if(health>0)
			health = (float) Math.min(health+0.5, 100);
	}
	
	/**
	 * smooth camera rotation.
	 */
	private void innerUpdate() {
		Vector z = orientation.getAxis('z');
		lookAt.addMutate(z, 0.25f).normalize();
	}
	
	/**
	 * sets the lookat for this ship.
	 * @param gLDrawable
	 */
	public void lookAtCamera1(GLAutoDrawable gLDrawable){
		final GL gl = gLDrawable.getGL();
		
		Game.glu.gluPerspective(perspective, 2, 2, 5000.0);
		
		
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
