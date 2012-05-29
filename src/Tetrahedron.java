import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


public class Tetrahedron extends Polyhedron {

	private Vector2Tuple[] vertices;

	public Tetrahedron(Vector position, Vector axis, double angle, double size) {
		super(position, axis, angle);
		size = Math.abs(size);

		double sqrt_3 = Math.sqrt(3.0);
		double height = Math.sqrt(6.0) / 1.5;
		double center = sqrt_3 / 3.0;
		
		vertices = new Vector2Tuple[4];
		vertices[0] = new Vector2Tuple();
		vertices[1] = new Vector2Tuple();
		vertices[2] = new Vector2Tuple();
		vertices[3] = new Vector2Tuple();
		vertices[0].v = new Vector( 0.0 * size, (-height) * size, (sqrt_3 - center) * size);
		vertices[1].v = new Vector(-1.0 * size, (-height) * size, (-center) * size);
		vertices[2].v = new Vector( 1.0 * size, (-height) * size, (-center) * size);
		vertices[3].v = new Vector( 0.0 * size, 0.0 * size, 0.0 * size);
		vertices[0].u = new Vector();
		vertices[1].u = new Vector();
		vertices[2].u = new Vector();
		vertices[3].u = new Vector();
	}

	@Override
	protected Vector2Tuple[] getVertices() {
		return vertices;
	}

	@Override
	protected void draw(GLAutoDrawable gLDrawable) {
		final GL gl = gLDrawable.getGL();
		double x = orientation.getPosition().getX();
		double y = orientation.getPosition().getY();
		double z = orientation.getPosition().getZ();
		gl.glEnd();
		
		gl.glBegin(GL.GL_TRIANGLES);
		
		//1
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[0].u.getX()+x, vertices[0].u.getY()+y, vertices[0].u.getZ()+z);
		
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[1].u.getX()+x, vertices[1].u.getY()+y, vertices[1].u.getZ()+z);
		
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[2].u.getX()+x, vertices[2].u.getY()+y, vertices[2].u.getZ()+z);
		
		//2
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3d(vertices[0].u.getX()+x, vertices[0].u.getY()+y, vertices[0].u.getZ()+z);
		
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3d(vertices[1].u.getX()+x, vertices[1].u.getY()+y, vertices[1].u.getZ()+z);

		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3d(vertices[3].u.getX()+x, vertices[3].u.getY()+y, vertices[3].u.getZ()+z);
		
		//3
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[0].u.getX()+x, vertices[0].u.getY()+y, vertices[0].u.getZ()+z);
		
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[2].u.getX()+x, vertices[2].u.getY()+y, vertices[2].u.getZ()+z);
		
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[3].u.getX()+x, vertices[3].u.getY()+y, vertices[3].u.getZ()+z);
		
		//4
		gl.glColor3f(0.667f, 0.667f, 1.0f);
		gl.glVertex3d(vertices[1].u.getX()+x, vertices[1].u.getY()+y, vertices[1].u.getZ()+z);
		
		gl.glColor3f(0.667f, 1.0f, 0.667f);
		gl.glVertex3d(vertices[2].u.getX()+x, vertices[2].u.getY()+y, vertices[2].u.getZ()+z);
		
		gl.glColor3f(1.0f, 0.667f, 0.667f);
		gl.glVertex3d(vertices[3].u.getX()+x, vertices[3].u.getY()+y, vertices[3].u.getZ()+z);
	}

	public static Tetrahedron createTetrahedron(Vector position, Vector axis, double angle, double size) {
		Tetrahedron tetra = new Tetrahedron(position,axis,angle,size);
		Object3D.registerObject(tetra);
		return tetra;
	}

}
