package com.biu.cg.objects3d;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.owens.oobjloader.builder.Face;

/**
 * Space - a sphere with space texture that travels with playr's ship and creates a filling of infinite space.
 * @author Irzh
 *
 */
public class Space extends Model3D  {
	/**
	 * c'tor.
	 * @param position 
	 * @param objFile
	 * @param texture
	 */
	public Space(Vector position, String objFile, String texture) {
		super(position, objFile, texture);
	}
	
	/**
	 * c'tor.
	 * @param objFile
	 * @param texture
	 * @param orientation
	 */
	public Space(String objFile, String texture, Orientation orientation) {
		super(objFile, texture);
		this.orientation = orientation;
	}

	
	/**
	 * draws the object - does it a little different then Model3D.
	 */
	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		double x = orientation.getPosition().getX();
		double y = orientation.getPosition().getY();
		double z = orientation.getPosition().getZ();
		
		
		
		final GL gl = gLDrawable.getGL();
		
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );   
		texture.bind();
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
				if(f.vertices.get(i)!=null && f.vertices.get(i).t!=null){
						gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);	// draw the texture.
						gl.glNormal3f(f.vertices.get(i).n.x, f.vertices.get(i).n.y, f.vertices.get(i).n.z); // set the normals
						gl.glVertex3d(f.vertices.get(i).v.x*scale + x, f.vertices.get(i).v.y*scale + y , f.vertices.get(i).v.z*scale + z);	// set the vertex geometry.
				}
			}
			gl.glEnd();
		}
	}

	@Override
	protected void update() {}
	
}
