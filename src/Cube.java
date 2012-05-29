import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class Cube extends Polyhedron {
	
	Vector2Tuple[] vertices;
	//private String textureSrc;
	Texture texture=null;
	
	public static Cube createCube(Vector position, Vector axis, double angle, double size, String textureSrc) {
		Cube cube = new Cube(position, axis, angle, size, textureSrc);
		Object3D.registerObject(cube);
		return cube;
	}
	
	protected Cube(Vector position, Vector axis, double angle, double size, String textureSrc) {
		super(position, axis, angle);
		size = Math.abs(size);
		vertices = new Vector2Tuple[8];
		vertices[0] = new Vector2Tuple();
		vertices[1] = new Vector2Tuple();
		vertices[2] = new Vector2Tuple();
		vertices[3] = new Vector2Tuple();
		vertices[4] = new Vector2Tuple();
		vertices[5] = new Vector2Tuple();
		vertices[6] = new Vector2Tuple();
		vertices[7] = new Vector2Tuple();
		vertices[0].v = new Vector(-1.0*size,-1.0*size,-1.0*size);
		vertices[1].v = new Vector(1.0*size, -1.0*size,-1.0*size);
		vertices[2].v = new Vector(1.0*size, -1.0*size, 1.0*size);
		vertices[3].v = new Vector(-1.0*size,-1.0*size, 1.0*size);
		vertices[4].v = new Vector(-1.0*size, 1.0*size,-1.0*size);
		vertices[5].v = new Vector(1.0*size,  1.0*size,-1.0*size);
		vertices[6].v = new Vector(1.0*size,  1.0*size, 1.0*size);
		vertices[7].v = new Vector(-1.0*size, 1.0*size, 1.0*size);
		vertices[0].u = new Vector();
		vertices[1].u = new Vector();
		vertices[2].u = new Vector();
		vertices[3].u = new Vector();
		vertices[4].u = new Vector();
		vertices[5].u = new Vector();
		vertices[6].u = new Vector();
		vertices[7].u = new Vector();
		
		//this.textureSrc = textureSrc;
	
	
		try {
			texture = TextureIO.newTexture(new File( "textures/" +  textureSrc),true);
			//wood = TextureIO.newTexture(new File( "textures/wood.gif" ),true);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void draw(GLAutoDrawable gLDrawable) {
		final GL gl = gLDrawable.getGL();
		double x = orientation.getPosition().getX();
		double y = orientation.getPosition().getY();
		double z = orientation.getPosition().getZ();
		gl.glEnd();

		//Texture wood=null;

		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );   
        texture.bind();
		
		gl.glBegin(GL.GL_QUADS);
		//1
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glColor3f(1.0f, 0.5f, 0.0f);
		gl.glVertex3d(vertices[0].u.getX()+x, vertices[0].u.getY()+y, vertices[0].u.getZ()+z);
		
		gl.glTexCoord2f(1.0f, 0.0f); 
		gl.glColor3f(1.0f, 0.5f, 0.0f);
		gl.glVertex3d(vertices[1].u.getX()+x, vertices[1].u.getY()+y, vertices[1].u.getZ()+z);
		
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glColor3f(1.0f, 0.5f, 0.0f);
		gl.glVertex3d(vertices[2].u.getX()+x, vertices[2].u.getY()+y, vertices[2].u.getZ()+z);
		
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glColor3f(1.0f, 0.5f, 0.0f);
		gl.glVertex3d(vertices[3].u.getX()+x, vertices[3].u.getY()+y, vertices[3].u.getZ()+z);
		
		//2
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glColor3f(1.0f, 0.0f, 0.5f);
		gl.glVertex3d(vertices[0].u.getX()+x, vertices[0].u.getY()+y, vertices[0].u.getZ()+z);

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glColor3f(1.0f, 0.0f, 0.5f);
		gl.glVertex3d(vertices[1].u.getX()+x, vertices[1].u.getY()+y, vertices[1].u.getZ()+z);

		gl.glTexCoord2f(1.0f, 1.0f); 
		gl.glColor3f(1.0f, 0.0f, 0.5f);
		gl.glVertex3d(vertices[5].u.getX()+x, vertices[5].u.getY()+y, vertices[5].u.getZ()+z);
		
		gl.glTexCoord2f(0.0f, 1.0f); 
		gl.glColor3f(1.0f, 0.0f, 0.5f);
		gl.glVertex3d(vertices[4].u.getX()+x, vertices[4].u.getY()+y, vertices[4].u.getZ()+z);
		
		//3
		gl.glTexCoord2f(0.0f, 0.0f); 
		gl.glColor3f(0.5f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[1].u.getX()+x, vertices[1].u.getY()+y, vertices[1].u.getZ()+z);

		gl.glTexCoord2f(1.0f, 0.0f); 
		gl.glColor3f(0.5f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[2].u.getX()+x, vertices[2].u.getY()+y, vertices[2].u.getZ()+z);

		gl.glTexCoord2f(1.0f, 1.0f); 
		gl.glColor3f(0.5f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[6].u.getX()+x, vertices[6].u.getY()+y, vertices[6].u.getZ()+z);
		
		gl.glTexCoord2f(0.0f, 1.0f); 
		gl.glColor3f(0.5f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[5].u.getX()+x, vertices[5].u.getY()+y, vertices[5].u.getZ()+z);
		
		//4
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glColor3f(0.5f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[2].u.getX()+x, vertices[2].u.getY()+y, vertices[2].u.getZ()+z);

		gl.glTexCoord2f(1.0f, 0.0f); 
		gl.glColor3f(0.5f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[3].u.getX()+x, vertices[3].u.getY()+y, vertices[3].u.getZ()+z);

		gl.glTexCoord2f(1.0f, 1.0f); 
		gl.glColor3f(0.5f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[7].u.getX()+x, vertices[7].u.getY()+y, vertices[7].u.getZ()+z);
		
		gl.glTexCoord2f(0.0f, 1.0f); 
		gl.glColor3f(0.5f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[6].u.getX()+x, vertices[6].u.getY()+y, vertices[6].u.getZ()+z);
		
		//5
		gl.glTexCoord2f(0.0f, 0.0f); 
		gl.glColor3f(0.0f, 1.0f, 0.5f);
		gl.glVertex3d(vertices[3].u.getX()+x, vertices[3].u.getY()+y, vertices[3].u.getZ()+z);

		gl.glTexCoord2f(1.0f, 0.0f); 
		gl.glColor3f(0.0f, 1.0f, 0.5f);
		gl.glVertex3d(vertices[0].u.getX()+x, vertices[0].u.getY()+y, vertices[0].u.getZ()+z);

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glColor3f(0.0f, 1.0f, 0.5f);
		gl.glVertex3d(vertices[4].u.getX()+x, vertices[4].u.getY()+y, vertices[4].u.getZ()+z);
		
		gl.glTexCoord2f(0.0f, 1.0f); 
		gl.glColor3f(0.0f, 1.0f, 0.5f);
		gl.glVertex3d(vertices[7].u.getX()+x, vertices[7].u.getY()+y, vertices[7].u.getZ()+z);
		
		//6
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glColor3f(0.0f, 0.5f, 1.0f);
		gl.glVertex3d(vertices[4].u.getX()+x, vertices[4].u.getY()+y, vertices[4].u.getZ()+z);

		gl.glTexCoord2f(1.0f, 0.0f); 
		gl.glColor3f(0.0f, 0.5f, 1.0f);
		gl.glVertex3d(vertices[5].u.getX()+x, vertices[5].u.getY()+y, vertices[5].u.getZ()+z);

		gl.glTexCoord2f(1.0f, 1.0f); 
		gl.glColor3f(0.0f, 0.5f, 1.0f);
		gl.glVertex3d(vertices[6].u.getX()+x, vertices[6].u.getY()+y, vertices[6].u.getZ()+z);
		
		gl.glTexCoord2f(0.0f, 1.0f); 
		gl.glColor3f(0.0f, 0.5f, 1.0f);
		gl.glVertex3d(vertices[7].u.getX()+x, vertices[7].u.getY()+y, vertices[7].u.getZ()+z);
	}

	@Override
	protected Vector2Tuple[] getVertices() {
		return vertices;
	}
}
