package com.biu.cg.objects3d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Vector;
import com.owens.oobjloader.builder.Build;
import com.owens.oobjloader.builder.Face;
import com.owens.oobjloader.parser.Parse;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public class Model3D extends Object3D {

	protected Build builder;
	protected Texture texture;
	
	
	public Model3D(String objFile, String texture) {
		super();
		generateModelFromFile(objFile, texture);
	}

	public Model3D(Vector position, String objFile, String texture) {
		super(position);
		generateModelFromFile(objFile, texture);
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
			Random rand = new Random();
			switch(f.vertices.size()){
			case 4:
			{
				gl.glBegin(GL.GL_QUADS);
	//			gl.glColor3f(0.5f, 0.25f, 0.25f);
				for(int i=0 ; i<4 ; i++){
					gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
					gl.glVertex3d(f.vertices.get(i).v.x + x, f.vertices.get(i).v.y + y, f.vertices.get(i).v.z  + z);
				}
				break;
			}
			case 3:
				gl.glBegin(GL.GL_TRIANGLES);
	//			gl.glColor3f(0.5f, 0.25f, 0.25f);
				for(int i=0 ; i<3 ; i++){
					
					gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
					gl.glVertex3d(f.vertices.get(i).v.x + x, f.vertices.get(i).v.y + y , f.vertices.get(i).v.z  + z);
				}	
				break;
			}
			gl.glEnd();
		}
	}

	@Override
	protected void update() {
		// TODO: implement.

	}
	
	private void generateModelFromFile(String objFile, String texturePath) {
		// TODO: remove String texture.
		// TODO: implement. make use of loader: http://darksleep.com/oobjloader/
		
		
		
		builder = new Build();
        try {
			new Parse(builder, objFile);
			texture = TextureIO.newTexture(new File(texturePath),true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        
        
	}

}
