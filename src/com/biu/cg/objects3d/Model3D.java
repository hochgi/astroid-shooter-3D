package com.biu.cg.objects3d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.owens.oobjloader.builder.Build;
import com.owens.oobjloader.builder.Face;
import com.owens.oobjloader.parser.Parse;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public abstract class Model3D extends Object3D {

	protected Build builder;
	protected Texture texture;
	protected float scale=1;
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public float getScale() {
		return scale;
	}
	
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
					
					if(f.vertices.get(i)!=null && f.vertices.get(i).t!=null){
							gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
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
					
					
					if(f.vertices.get(i)!=null && f.vertices.get(i).t!=null){
						gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
						gl.glVertex3d((x + X.x + Y.x + Z.x)*scale , (y + X.y + Y.y + Z.y)*scale , (z + X.z + Y.z + Z.z)*scale);
					}
				}	
				gl.glEnd();
				break;
			}
			
		}
	}
	
	
	
	public ArrayList<Vector> getVertices(){
		ArrayList<Vector> res = new ArrayList<Vector>();
		double x = orientation.getPosition().getX();
		double y = orientation.getPosition().getY();
		double z = orientation.getPosition().getZ();
		Orientation o = orientation;
		for(Face f : builder.faces){
			//Random rand = new Random();
			switch(f.vertices.size()){
			case 4:
			{			
				for(int i=0 ; i<4 ; i++){					
					Vector X = o.getAxis('x').mul(f.vertices.get(i).v.x);
					Vector Y = o.getAxis('y').mul(f.vertices.get(i).v.y);
					Vector Z = o.getAxis('z').mul(f.vertices.get(i).v.z);					
					if(f.vertices.get(i)!=null && f.vertices.get(i).t!=null){
							res.add(new Vector((float)(x + X.x + Y.x + Z.x)*scale , (float)(y + X.y + Y.y + Z.y)*scale , (float)(z + X.z + Y.z + Z.z)*scale));
					}
				}
				break;
			}
			case 3:
				for(int i=0 ; i<3 ; i++){
					
					Vector X = o.getAxis('x').mul(f.vertices.get(i).v.x);
					Vector Y = o.getAxis('y').mul(f.vertices.get(i).v.y);
					Vector Z = o.getAxis('z').mul(f.vertices.get(i).v.z);
					if(f.vertices.get(i)!=null && f.vertices.get(i).t!=null){
						res.add(new Vector((float)(x + X.x + Y.x + Z.x)*scale , (float)(y + X.y + Y.y + Z.y)*scale , (float)(z + X.z + Y.z + Z.z)*scale));
					}
				}	
				break;
			}
		}
		return res;
	}
	
	
	
	private void generateModelFromFile(String objFile, String texturePath) {
		// TODO: remove String texture.

		builder = new Build();
        try {
			new Parse(builder, objFile);
			texture = TextureIO.newTexture(new File(texturePath),true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}        
        
        
	}

}
