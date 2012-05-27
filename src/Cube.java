import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class Cube extends Polyhedron {
	
	Vector[] vertices;
	
	public static Cube createCube(Vector position, Vector axis, double angle, double size) {
		Cube cube = new Cube(position, axis, angle, size);
		Polyhedron.registerPolyhedron(cube);
		return cube;
	}
	
	protected Cube(Vector position, Vector axis, double angle, double size) {
		super(position, axis, angle);
		size = Math.abs(size);
		vertices = new Vector[8];
		vertices[0] = new Vector(-1.0*size,-1.0*size,-1.0*size);
		vertices[1] = new Vector(1.0*size, -1.0*size,-1.0*size);
		vertices[2] = new Vector(1.0*size, -1.0*size, 1.0*size);
		vertices[3] = new Vector(-1.0*size,-1.0*size, 1.0*size);
		vertices[4] = new Vector(-1.0*size, 1.0*size,-1.0*size);
		vertices[5] = new Vector(1.0*size,  1.0*size,-1.0*size);
		vertices[6] = new Vector(1.0*size,  1.0*size, 1.0*size);
		vertices[7] = new Vector(-1.0*size, 1.0*size, 1.0*size);
	}

	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		final GL gl = gLDrawable.getGL();
		double x = orientation.getPosition().getX();
		double y = orientation.getPosition().getY();
		double z = orientation.getPosition().getZ();
		gl.glBegin(GL.GL_QUADS);

		//1
		gl.glColor3f(1.0f, 0.5f, 0.0f);
		gl.glVertex3d(vertices[0].getX()+x, vertices[0].getY()+y, vertices[0].getZ()+z);
		
		gl.glColor3f(1.0f, 0.5f, 0.0f);
		gl.glVertex3d(vertices[1].getX()+x, vertices[1].getY()+y, vertices[1].getZ()+z);
		
		gl.glColor3f(1.0f, 0.5f, 0.0f);
		gl.glVertex3d(vertices[2].getX()+x, vertices[2].getY()+y, vertices[2].getZ()+z);
		
		gl.glColor3f(1.0f, 0.5f, 0.0f);
		gl.glVertex3d(vertices[3].getX()+x, vertices[3].getY()+y, vertices[3].getZ()+z);
		
		//2
		gl.glColor3f(1.0f, 0.0f, 0.5f);
		gl.glVertex3d(vertices[0].getX()+x, vertices[0].getY()+y, vertices[0].getZ()+z);

		gl.glColor3f(1.0f, 0.0f, 0.5f);
		gl.glVertex3d(vertices[1].getX()+x, vertices[1].getY()+y, vertices[1].getZ()+z);

		gl.glColor3f(1.0f, 0.0f, 0.5f);
		gl.glVertex3d(vertices[5].getX()+x, vertices[5].getY()+y, vertices[5].getZ()+z);
		
		gl.glColor3f(1.0f, 0.0f, 0.5f);
		gl.glVertex3d(vertices[4].getX()+x, vertices[4].getY()+y, vertices[4].getZ()+z);
		
		//3
		gl.glColor3f(0.5f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[1].getX()+x, vertices[1].getY()+y, vertices[1].getZ()+z);

		gl.glColor3f(0.5f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[2].getX()+x, vertices[2].getY()+y, vertices[2].getZ()+z);

		gl.glColor3f(0.5f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[6].getX()+x, vertices[6].getY()+y, vertices[6].getZ()+z);
		
		gl.glColor3f(0.5f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[5].getX()+x, vertices[5].getY()+y, vertices[5].getZ()+z);
		
		//4
		gl.glColor3f(0.5f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[2].getX()+x, vertices[2].getY()+y, vertices[2].getZ()+z);

		gl.glColor3f(0.5f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[3].getX()+x, vertices[3].getY()+y, vertices[3].getZ()+z);

		gl.glColor3f(0.5f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[7].getX()+x, vertices[7].getY()+y, vertices[7].getZ()+z);
		
		gl.glColor3f(0.5f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[6].getX()+x, vertices[6].getY()+y, vertices[6].getZ()+z);
		
		//5
		gl.glColor3f(0.0f, 1.0f, 0.5f);
		gl.glVertex3d(vertices[3].getX()+x, vertices[3].getY()+y, vertices[3].getZ()+z);

		gl.glColor3f(0.0f, 1.0f, 0.5f);
		gl.glVertex3d(vertices[0].getX()+x, vertices[0].getY()+y, vertices[0].getZ()+z);

		gl.glColor3f(0.0f, 1.0f, 0.5f);
		gl.glVertex3d(vertices[4].getX()+x, vertices[4].getY()+y, vertices[4].getZ()+z);
		
		gl.glColor3f(0.0f, 1.0f, 0.5f);
		gl.glVertex3d(vertices[7].getX()+x, vertices[7].getY()+y, vertices[7].getZ()+z);
		
		//6
		gl.glColor3f(0.0f, 0.5f, 1.0f);
		gl.glVertex3d(vertices[4].getX()+x, vertices[4].getY()+y, vertices[4].getZ()+z);

		gl.glColor3f(0.0f, 0.5f, 1.0f);
		gl.glVertex3d(vertices[5].getX()+x, vertices[5].getY()+y, vertices[5].getZ()+z);

		gl.glColor3f(0.0f, 0.5f, 1.0f);
		gl.glVertex3d(vertices[6].getX()+x, vertices[6].getY()+y, vertices[6].getZ()+z);
		
		gl.glColor3f(0.0f, 0.5f, 1.0f);
		gl.glVertex3d(vertices[7].getX()+x, vertices[7].getY()+y, vertices[7].getZ()+z);
	}

	@Override
	protected Vector[] getVertices() {
		return vertices;
	}
}
