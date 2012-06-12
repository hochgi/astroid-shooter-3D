package com.biu.cg.objects3d;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.biu.cg.math3d.Vector;
import com.biu.cg.math3d.Vector2Tuple;

/**
 * a class for the rotating "pyramid"
 * @author gilad
 *
 */
public class Tetrahedron extends Polyhedron {

	private Vector2Tuple[] vertices;
	private double originalAngle;
	private int counter = 1;
	private Object tLock = new Object();

	/**
	 * setter constructor
	 * @param position
	 * @param axis
	 * @param angle
	 * @param size
	 */
	public Tetrahedron(Vector position, Vector axis, double angle, double size) {
		super(position, axis, angle);
		size = Math.abs(size);
		
		originalAngle = angle;

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

	/**
	 * inherited from Polyhedron
	 */
	@Override
	protected Vector2Tuple[] getVertices() {
		return vertices;
	}

	/**
	 * inherited from Object3D.
	 * deals with the drawing of the tetrahedron
	 */
	@Override
	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
		final GL gl = gLDrawable.getGL();
		double x,y,z;
		//since other threads touch the orientation,
		//it is better to synchronize it
		synchronized (tLock ) {
			x = orientation.getPosition().getX();
			y = orientation.getPosition().getY();
			z = orientation.getPosition().getZ();
		}
		
		gl.glBegin(GL.GL_TRIANGLES);
		
		//start drawing the faces:
		
		//face 1
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[0].u.getX()+x, vertices[0].u.getY()+y, vertices[0].u.getZ()+z);
		
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[1].u.getX()+x, vertices[1].u.getY()+y, vertices[1].u.getZ()+z);
		
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3d(vertices[2].u.getX()+x, vertices[2].u.getY()+y, vertices[2].u.getZ()+z);
		
		//face 2
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3d(vertices[0].u.getX()+x, vertices[0].u.getY()+y, vertices[0].u.getZ()+z);
		
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3d(vertices[1].u.getX()+x, vertices[1].u.getY()+y, vertices[1].u.getZ()+z);

		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3d(vertices[3].u.getX()+x, vertices[3].u.getY()+y, vertices[3].u.getZ()+z);
		
		//face 3
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[0].u.getX()+x, vertices[0].u.getY()+y, vertices[0].u.getZ()+z);
		
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[2].u.getX()+x, vertices[2].u.getY()+y, vertices[2].u.getZ()+z);
		
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex3d(vertices[3].u.getX()+x, vertices[3].u.getY()+y, vertices[3].u.getZ()+z);
		
		//face 4
		gl.glColor3f(0.667f, 0.667f, 1.0f);
		gl.glVertex3d(vertices[1].u.getX()+x, vertices[1].u.getY()+y, vertices[1].u.getZ()+z);
		
		gl.glColor3f(0.667f, 1.0f, 0.667f);
		gl.glVertex3d(vertices[2].u.getX()+x, vertices[2].u.getY()+y, vertices[2].u.getZ()+z);
		
		gl.glColor3f(1.0f, 0.667f, 0.667f);
		gl.glVertex3d(vertices[3].u.getX()+x, vertices[3].u.getY()+y, vertices[3].u.getZ()+z);
		
		gl.glEnd();
	}

	/**
	 * factory method. after creation,
	 * it registers the object to Object3D updates...
	 * @param position
	 * @param axis
	 * @param angle
	 * @param size
	 * @return
	 */
	public static Tetrahedron createTetrahedron(Vector position, Vector axis, double angle, double size) {
		Tetrahedron tetra = new Tetrahedron(position,axis,angle,size);
		Object3D.registerObject(tetra);
		return tetra;
	}

	/**
	 * change the speed of rotation
	 */
	public void changeSpeed() {
		counter  = (counter + 1) % 4;
		synchronized (tLock ) {
			setAngle(counter * originalAngle);
		}
	}

	/**
	 * inherited from Object3D.
	 * invokes super (Polyhedron) method synchronously 
	 */
	@Override
	protected void update() {
		synchronized (tLock) {
			super.update();
		}
	};
}
