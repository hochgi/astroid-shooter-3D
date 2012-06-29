package com.biu.cg.objects3d.ships;

import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

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
		super(position, "models/ship1/ship.obj" , "models/ship1/F02_512.jpg");
		this.cam = camera;
		
	}
	
//	@Override
//	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
//		// TODO: implement.
//		float x = cam.getPosition().x + 5*cam.getAxis('z').x;
//		float y = cam.getPosition().y + 5*cam.getAxis('z').y;
//		float z = cam.getPosition().z + 5*cam.getAxis('z').z + 20;
//		
//		Orientation o = getOrientation();
//		float xx = o.getAxis('x').neg().x;
//		float xy = o.getAxis('x').neg().y;
//		float xz = o.getAxis('x').neg().z;
//		
//		float yx = o.getAxis('y').neg().x;
//		float yy = o.getAxis('y').neg().y;
//		float yz = o.getAxis('y').neg().z;
//		
//		
//		final GL gl = gLDrawable.getGL();
//		
//		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
//        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );   
//		texture.bind();
//		
//		
//		
//		for(Face f : builder.faces){
//			Random rand = new Random();
//			switch(f.vertices.size()){
//			case 4:
//			{
//				gl.glBegin(GL.GL_QUADS);
//	//			gl.glColor3f(0.5f, 0.25f, 0.25f);
//				for(int i=0 ; i<4 ; i++){
//					gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
//					gl.glVertex3d(f.vertices.get(i).v.x + x, f.vertices.get(i).v.y + y, f.vertices.get(i).v.z  + z);
//				}
//				break;
//			}
//			case 3:
//				gl.glBegin(GL.GL_TRIANGLES);
//	//			gl.glColor3f(0.5f, 0.25f, 0.25f);
////				for(int i=0 ; i<3 ; i++){
////					
////					gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
////					gl.glVertex3d(f.vertices.get(i).v.x + x, f.vertices.get(i).v.y + y , f.vertices.get(i).v.z  + z);
////				}	
//				
//				gl.glTexCoord2f(f.vertices.get(0).t.u, f.vertices.get(0).t.v);
//				gl.glVertex3d(f.vertices.get(0).v.x -xx + x, f.vertices.get(0).v.y -xy + y , f.vertices.get(0).v.z -xz + z);
//				
//				gl.glTexCoord2f(f.vertices.get(1).t.u, f.vertices.get(1).t.v);
//				gl.glVertex3d(f.vertices.get(1).v.x + yx -xx + x, f.vertices.get(1).v.y + yy -xy + y , f.vertices.get(1).v.z  + yz -xz + z);
//				
//				gl.glTexCoord2f(f.vertices.get(2).t.u, f.vertices.get(2).t.v);
//				gl.glVertex3d(f.vertices.get(2).v.x + xx + x, f.vertices.get(2).v.y + xy + y , f.vertices.get(2).v.z  + xz + z);
//				break;
//			}
//			gl.glEnd();
//		}
//	}

}
