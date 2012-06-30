package com.biu.cg.objects3d;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.math3d.Vector;
import com.owens.oobjloader.builder.Face;

public class Space extends Model3D  {
	
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

		for(Face f : builder.faces){
			//Random rand = new Random();
			switch(f.vertices.size()){
			case 4:
			{
				gl.glBegin(GL.GL_QUADS);
				for(int i=0 ; i<4 ; i++){
					if(f.vertices.get(i)!=null && f.vertices.get(i).t!=null){
							gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
							gl.glVertex3d(f.vertices.get(i).v.x*scale + x, f.vertices.get(i).v.y*scale + y , f.vertices.get(i).v.z*scale + z);
					}
				}
				break;
			}
			case 3:
				gl.glBegin(GL.GL_TRIANGLES);
	//			gl.glColor3f(0.5f, 0.25f, 0.25f);
				for(int i=0 ; i<3 ; i++){
					if(f.vertices.get(i)!=null && f.vertices.get(i).t!=null){
						gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
						gl.glVertex3d(f.vertices.get(i).v.x*scale + x, f.vertices.get(i).v.y*scale + y , f.vertices.get(i).v.z*scale + z);
					}
				}	
				break;
			}
			gl.glEnd();
		}
	}
	
}
