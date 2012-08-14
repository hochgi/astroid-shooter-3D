package com.biu.cg.objects3d.ships;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.main.Exercise4;
import com.biu.cg.main.Explosion;
import com.biu.cg.main.Game;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Rotator;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.particles.sprites.GlintEmitter;
import com.biu.cg.objects3d.physics.Collidable;

public class Ship1 extends Ship implements Collidable {
	
	
	private float colorRatio=0f;

	public Ship1() {
		
		super("models/ship1/ship.obj" , "models/ship1/F02_512.jpg");
	}
	
	public Ship1(Vector position) {
		super(position, "models/ship1/ship.wng" , "models/ship1/F02_512.jpg");
		
	}

	@Override
	public float getWingHeight() {
		return -1.25f;
	}

	@Override
	public float getWingWidth() {
		return 2.5f;
	}

	@Override
	public void collisionAction(Collidable collidable) {
		Orientation newOrientation = new Orientation(getPosition(), orientation.getAxis('x'), orientation.getAxis('y'), orientation.getAxis('z') );
		newOrientation.translateForward(100);
		
		switch(collidable.getType()){
		case MOTHERSHIP:
			MotherShip m = (MotherShip)collidable;
			Vector n = m.getNormalPerpendicularToPlaneAt(this.getPosition(), (BoundingSphere)getBoundingShape());
			//float angle = (float)Math.asin(n.dot(getOrientation().getAxis('z')));
			Vector x = getOrientation().getAxis('x');
			Vector y = getOrientation().getAxis('y');
			Vector z = getOrientation().getAxis('z');
			Vector tmp = new Vector();
			
			Rotator.oneTimeRotatation(x, tmp, z, (float)Math.PI);
			Rotator.oneTimeRotatation(tmp, x, n, (float)Math.PI);

//			Rotator.oneTimeRotatation(y, tmp, z, (float)Math.PI);
//			Rotator.oneTimeRotatation(tmp, y, n, (float)Math.PI);
//			
			Rotator.oneTimeRotatation(new Vector(z), z, n, (float)Math.PI);
//			Rotator.oneTimeRotatation(new Vector(z), z, y, (float)Math.PI);
			z.negMutate();	
			tmp = Vector.cross(x, z);
			y.x = tmp.x;
			y.y = tmp.y;
			y.z = tmp.z;
			x.negMutate();
			
			speedDown();
			break;
		case RELOAD:
			fuel=600;
			rocketCounter=3;
			Exercise4.rocketPanel.restore();
			Exercise4.fuelPanel.setFuel(fuel);
			Exercise4.fuelPanel.repaint();
			health=baseHealth;
			
			System.out.println("RELOAD");
			break;
		case ATMOSPHERE:
			health=Math.max(health-1, 0);
			
			new GlintEmitter(newOrientation.getPosition(), orientation, 2);
			
			if(health<=0){
				new Explosion(newOrientation.getPosition(),orientation, 3f, null);
				Exercise4.exitWithMessage();
			}
			break;
		case EARTH:
			health=0;
			new Explosion(newOrientation.getPosition(),orientation, 3f, null);
			Exercise4.exitWithMessage();
			break;
//		case ASTEROID:
//			health=0;
//			new Explosion(newOrientation.getPosition(),orientation, 3f, null);
//			Exercise4.exit("You are dead :(");
//			
//			break;
		default:
			canMove=true;
			break;
		}
	}

	@Override
	public BoundingShape getBoundingShape() {
		return new BoundingSphere(getVertices());
	}

	@Override
	public Type getType() {
		return Type.PLAYER_SHIP;
	}
	
	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable){
		if(health==0)
			return;
		final GL gl = gLDrawable.getGL();
		
		float	ambient[] = {1f,1f,1f,1f};
    	
    	ambient[1] = (health/baseHealth);
    	ambient[2] = (health/baseHealth);
    	
		
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambient,0); 
		
		super.synchronizedDraw(gLDrawable);
		
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, Game.defaultAmbient,0); 
	}
	
}
