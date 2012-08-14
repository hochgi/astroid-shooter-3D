package com.biu.cg.objects3d.asteroids;

import java.util.LinkedList;
import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.main.Exercise4;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.planets.Earth;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.particles.sprites.Shot;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;
import com.owens.oobjloader.builder.Face;

/**
 * Asteroid abstract class.
 * @author Irzh
 *
 */
public abstract class Asteroid extends Model3D implements Collidable{
	float speed;
	boolean alive=true;
	Vector direction;
	Orientation camera;
	Random rand = new Random();
	protected Earth earth;
	private LinkedList<Shot> rockets = new LinkedList<Shot>();
	protected float colorRatio=1;
	
	public boolean isAlive() {
		return alive;
	}
	
	public Asteroid(Earth earth , Orientation camera , Vector pos , String objPath) {
		super(pos, objPath , null);
		this.earth = earth;
		this.camera = camera;
		Collidables.registerObject(this);
		direction = earth.getPosition().sub(pos, 1).normalize();
		
//		Random rand = new Random();
		
		//speed = 1 + (float)rand.nextInt(11) / 10f;
		speed = 3;
	}


	@Override
	public Type getType() {
		return Type.ASTEROID;
	}

	@Override
	protected void update() {
		orientation.getPosition().addMutate(direction, speed);
		orientation.rotateHeading(0.1f);
		orientation.rotatePitch(0.01f);
		orientation.rotateRoll(0.02f);
	}

	
	/**
	 * draws the object - does it a little different then Model3D.
	 */
	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		
		if(!alive)
			return;
		final GL gl = gLDrawable.getGL();
		double x = orientation.getPosition().getX();
		double y = orientation.getPosition().getY();
		double z = orientation.getPosition().getZ();
		
		float	ambient[] = {0f,0f,0f,1f};
    	
    	ambient[0] = 1-colorRatio;
    	ambient[1] = colorRatio;
		
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambient,0); 
       
		Orientation o = orientation;
		// check for each face of the object if it is a quad or a triangle.
		for(Face f : builder.faces){
			switch(f.vertices.size()){
			case 4:	// quad.
			{
				gl.glBegin(GL.GL_QUADS);	
				break;
			}
			case 3:	// triangle.
				gl.glBegin(GL.GL_TRIANGLES);
				break;
			}
			// draw the vertices.
			for(int i=0 ; i<f.vertices.size() ; i++){
				
				Vector X = o.getAxis('x').mul(f.vertices.get(i).v.x);
				Vector Y = o.getAxis('y').mul(f.vertices.get(i).v.y);
				Vector Z = o.getAxis('z').mul(f.vertices.get(i).v.z);
				
				if(f.vertices.get(i)!=null){	
					gl.glNormal3f(f.vertices.get(i).n.x, f.vertices.get(i).n.y, f.vertices.get(i).n.z);
					gl.glVertex3d((x + X.x + Y.x + Z.x)*scale , (y + X.y + Y.y + Z.y)*scale , (z + X.z + Y.z + Z.z)*scale);
				}
			}
			gl.glEnd();
		}
	}

	/**
	 * set that a Missle is locked on this asteroid.
	 * @param shot
	 */
	public void registerLockedMissle(Shot shot) {
		rockets.add(shot);
	}

	/**
	 * set that all the missiles aren't locked on this asteroid any more. 
	 */
	public void dismissLockedRockets(){
		for(Shot r : rockets){
			r.unlockRocket();
		}
	}
	
	/**
	 * add points - used when player destroys an asteroid.
	 * @param points
	 */
	protected void addPoints(int points){
		Exercise4.addPoints(points);
	}
	
	/**
	 * Reduces earth's health when the asteroid hit Earth.
	 * @param points
	 */
	protected void reduceEarthHealth(int points){
		Exercise4.reduceEarthHealth(points);
	}
	
	/**
	 * What should happen when the asteroid is destroyed.
	 */
	protected abstract void onDestroy();
}
