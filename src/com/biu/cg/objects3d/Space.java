package com.biu.cg.objects3d;

import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.owens.oobjloader.builder.Face;

public class Space extends Model3D  {

	Orientation rOrientation = new Orientation();
	
	public Orientation getRotationOrientation() {
		return rOrientation;
	}
	
	public Space(Vector position, String objFile, String texture) {
		super(position, objFile, texture);
		// TODO Auto-generated constructor stub
	}
	
	public Space(String objFile, String texture) {
		super(objFile, texture);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		// TODO: implement.
		double x = orientation.getPosition().getX();
		double y = orientation.getPosition().getY();
		double z = orientation.getPosition().getZ();
		
		
		
		final GL gl = gLDrawable.getGL();
		
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );   
		texture.bind();
		
		Orientation o = rOrientation;
		int counter=0;
		for(Face f : builder.faces){
			Random rand = new Random();
			switch(f.vertices.size()){
			case 4:
			{
				gl.glBegin(GL.GL_QUADS);
				for(int i=0 ; i<4 ; i++){
					
					Vector X = o.getAxis('x').mul(f.vertices.get(i).v.x);
					Vector Y = o.getAxis('y').mul(f.vertices.get(i).v.y);
					Vector Z = o.getAxis('z').mul(f.vertices.get(i).v.z);
					
					if(f.vertices.get(i)!=null && f.vertices.get(i).t!=null){
							gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
							gl.glVertex3d((x + X.x + Y.x + Z.x)*scale , (y + X.y + Y.y + Z.y)*scale , (z + X.z + Y.z + Z.z)*scale);
					}
				}
				break;
			}
			case 3:
				gl.glBegin(GL.GL_TRIANGLES);
	//			gl.glColor3f(0.5f, 0.25f, 0.25f);
				for(int i=0 ; i<3 ; i++){
					
					Vector X = o.getAxis('x').mul(f.vertices.get(i).v.x);
					Vector Y = o.getAxis('y').mul(f.vertices.get(i).v.y);
					Vector Z = o.getAxis('z').mul(f.vertices.get(i).v.z);
					
					
					if(f.vertices.get(i)!=null && f.vertices.get(i).t!=null){
						gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
						gl.glVertex3d((x + X.x + Y.x + Z.x)*scale , (y + X.y + Y.y + Z.y)*scale , (z + X.z + Y.z + Z.z)*scale);
					}
				}	
				break;
			}
			gl.glEnd();
		}
	}
	
}
