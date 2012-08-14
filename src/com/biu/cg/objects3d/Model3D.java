package com.biu.cg.objects3d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Polygon;
import com.biu.cg.math3d.Vector;
import com.owens.oobjloader.builder.Build;
import com.owens.oobjloader.builder.Face;
import com.owens.oobjloader.builder.FaceVertex;
import com.owens.oobjloader.parser.Parse;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public abstract class Model3D extends Object3D {

	protected Build builder;
	protected Texture texture=null;
	protected float scale=1;
	
	/**
	 * sets the scale factor of the object.
	 * @param scale
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	/**
	 * gets the scale factor of the object.
	 * @return
	 */
	public float getScale() {
		return scale;
	}
	
	/**
	 * c'tor - create a new 3D object.
	 * @param objFile - obj file.
	 * @param texture - texture file.
	 */
	public Model3D(String objFile, String texture) {
		super();
		generateModelFromFile(objFile, texture);
	}

	/**
	 * c'tor - create a new 3D object at position.
	 * @param position - position.
	 * @param objFile - obj file.
	 * @param texture - texture file.
	 */
	public Model3D(Vector position, String objFile, String texture) {
		super(position);
		generateModelFromFile(objFile, texture);
	}

	/**
	 * draws the object.
	 */
	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		double x = orientation.getPosition().getX();
		double y = orientation.getPosition().getY();
		double z = orientation.getPosition().getZ();
		
		final GL gl = gLDrawable.getGL();
		
		if(texture!=null){
			gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
	        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );   
			texture.bind();
		}
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
			for(int i=0 ; i<f.vertices.size() ; i++){
				// draw the vertices.
				Vector X = o.getAxis('x').mul(f.vertices.get(i).v.x);
				Vector Y = o.getAxis('y').mul(f.vertices.get(i).v.y);
				Vector Z = o.getAxis('z').mul(f.vertices.get(i).v.z);
				
				if(f.vertices.get(i).t!=null)	// check if the object has a texture.
					gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);	// draw the texture.
				gl.glNormal3f(f.vertices.get(i).n.x, f.vertices.get(i).n.y, f.vertices.get(i).n.z); // set the normals
				gl.glVertex3d(x + (X.x + Y.x + Z.x)*scale , y + (X.y + Y.y + Z.y)*scale , z + (X.z + Y.z + Z.z)*scale); // set the vertex geometry.
				
			}
			gl.glEnd();
			
		}
	}
	
	/**
	 * gets object's vertices.
	 * @return
	 */
	public ArrayList<Vector> getVertices(){
		ArrayList<Vector> res = new ArrayList<Vector>();
		// get all polygons.
		ArrayList<Polygon> polygons = getPolygons();
		
		for(Polygon p : polygons){
			for(Vector v : p.vertices)
				res.add(v);
		}
		
		return res;
	}
	
	
	/**
	 * gets object's polygons. (each polygon is a collection of vertices.)
	 * @return
	 */
	public ArrayList<Polygon> getPolygons(){
		ArrayList<Polygon> res = new ArrayList<Polygon>();	
		double x = orientation.getPosition().getX();
		double y = orientation.getPosition().getY();
		double z = orientation.getPosition().getZ();
		Orientation o = orientation;
		for(Face f : builder.faces){
			Polygon poly = new Polygon();
			for(FaceVertex fv : f.vertices){
				Vector X = o.getAxis('x').mul(fv.v.x);
				Vector Y = o.getAxis('y').mul(fv.v.y);
				Vector Z = o.getAxis('z').mul(fv.v.z);
				//if(fv!=null && fv.t!=null)
					poly.vertices.add(new Vector((float)(x + (X.x + Y.x + Z.x)*scale) , (float)(y + (X.y + Y.y + Z.y)*scale) , (float)(z + (X.z + Y.z + Z.z)*scale)));
			}
			res.add(poly);
		}
		return res;
	}
	
	/*
	 * loads obj file.
	 */
	private void generateModelFromFile(String objFile, String texturePath) {
		// TODO: remove String texture.
		builder = new Build();
        try {
			new Parse(builder, objFile);
			if(texturePath!=null)
				texture = TextureIO.newTexture(new File(texturePath),true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}        
	}
}
