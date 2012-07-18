package com.biu.cg.objects3d.asteroids;

import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.main.Explosion;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.object3d.planets.Earth;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;
import com.owens.oobjloader.builder.Face;

public class Asteroid extends Model3D implements Collidable{

	boolean alive=true;
	Vector dest;
	Orientation camera;
	Random rand = new Random();
	
	public Asteroid(Earth earth , Orientation camera , Vector pos) {
		super(pos, "models/asteroids/astro01.wng" , null);
		this.camera = camera;
		Collidables.registerObject(this);
		dest = new Vector(earth.getPosition());
		dest.normalize();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collisionAction(Collidable collidable) {
		// TODO Auto-generated method stub
		switch(collidable.getType()){
		case ROCKET:
			alive=false;
			Collidables.unregisterObject(this);
			Asteroids.unregisterAsteroid(this);
			break;
		case PHOTON:
			alive=false;
			Collidables.unregisterObject(this);
			Asteroids.unregisterAsteroid(this);
			break;
		case EARTH:
			new Explosion(getPosition(),camera, false);
			Collidables.unregisterObject(this);
			Asteroids.unregisterAsteroid(this);
			alive=false;
			break;
		}
	}

	@Override
	public BoundingShape getBoundingShape() {
		// TODO Auto-generated method stub
		return new BoundingSphere(getVertices());
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.ASTEROID;
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		orientation.getPosition().x += 4*dest.x;
		orientation.getPosition().y += 4*dest.y;
		orientation.getPosition().z += 4*dest.z;
		
		orientation.rotateHeading(0.1f);
		orientation.rotatePitch(0.01f);
		orientation.rotateRoll(0.02f);
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		
		if(!alive)
			return;
		
		double x = orientation.getPosition().getX();
		double y = orientation.getPosition().getY();
		double z = orientation.getPosition().getZ();
		
		
		
		final GL gl = gLDrawable.getGL();
		
//		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
//        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );   
//		texture.bind();
		
		Orientation o = orientation;
		//int counter=0;
		for(Face f : builder.faces){
			//Random rand = new Random();
			switch(f.vertices.size()){
			case 4:
			{
				gl.glBegin(GL.GL_QUADS);
				for(int i=0 ; i<4 ; i++){
					
					Vector X = o.getAxis('x').mul(f.vertices.get(i).v.x);
					Vector Y = o.getAxis('y').mul(f.vertices.get(i).v.y);
					Vector Z = o.getAxis('z').mul(f.vertices.get(i).v.z);
					
					if(f.vertices.get(i)!=null){	
						//gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
						gl.glColor3f(1f, 1f, 1f);
						gl.glVertex3d((x + X.x + Y.x + Z.x)*scale , (y + X.y + Y.y + Z.y)*scale , (z + X.z + Y.z + Z.z)*scale);
					}
				}
				gl.glEnd();
				break;
			}
			case 3:
				gl.glBegin(GL.GL_TRIANGLES);
	//			gl.glColor3f(0.5f, 0.25f, 0.25f);
				for(int i=0 ; i<3 ; i++){
					
					Vector X = o.getAxis('x').mul(f.vertices.get(i).v.x);
					Vector Y = o.getAxis('y').mul(f.vertices.get(i).v.y);
					Vector Z = o.getAxis('z').mul(f.vertices.get(i).v.z);
					
					
					if(f.vertices.get(i)!=null){
						//gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
						gl.glColor3f(1f, 1f, 1f);
						gl.glVertex3d((x + X.x + Y.x + Z.x)*scale , (y + X.y + Y.y + Z.y)*scale , (z + X.z + Y.z + Z.z)*scale);
					}
				}	
				gl.glEnd();
				break;
			}
			
		}
	}

}
