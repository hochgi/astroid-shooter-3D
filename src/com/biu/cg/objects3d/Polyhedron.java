package com.biu.cg.objects3d;

import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Rotator;
import com.biu.cg.math3d.Vector;
import com.biu.cg.math3d.Vector2Tuple;

/**
 * class to serve as base class for polyhedrons
 * (such as cubes or tetrahedrons)
 * @author gilad
 *
 */
public abstract class Polyhedron extends Object3D {
	
	private Vector fixedAxis;
	private float fixedAngle = 0f;
	private float accumulatedAngle;

	/**
	 * yet another setter constructor
	 * @param position
	 * @param axis
	 * @param angle
	 */
	protected Polyhedron(Vector position, Vector axis, float angle) {
		super(position);
		orientation = new Orientation(position);
		setFixedRotation(axis, angle);
	}
	
	private void setFixedRotation(Vector axis, float angle) {
		while(angle < 0) {
			angle += ((float)Math.PI * 2);
		}
		while(angle >= ((float)Math.PI * 2)) {
			angle -= ((float)Math.PI * 2);
    	}
		accumulatedAngle = fixedAngle  = angle;
		fixedAxis = axis;
	}
	
	/**
	 * inherited from Object3D - updates the vertices
	 */
	@Override
	protected void update() {
		Vector2Tuple[] vertices = getVertices();
		for (Vector2Tuple t : vertices) {
			rotateAtPredefinedAxisAndAngle(t.v,t.u);
		}
	}
	
	/**
	 * reverse rotation
	 */
	public void reverseRotation() {
		fixedAngle = ((float)Math.PI * 2f) - fixedAngle;
	}
	
	/**
	 * simple setter (fixed angle for fixed rotation)
	 * @param d - angle
	 */
	public void setAngle(float d) {
		fixedAngle = d;
	}
	
	/**
	 * an updater method. rotates a vector.
	 * @param v - original vector
	 * @param u - vector to store results in
	 */
	public void rotateAtPredefinedAxisAndAngle(Vector v, Vector u) {
		if(accumulatedAngle >= ((float)Math.PI * 2f)) {
			accumulatedAngle -= ((float)Math.PI * 2f);
    	}
		Rotator.oneTimeRotatation(v,u,fixedAxis,accumulatedAngle);
		accumulatedAngle += fixedAngle;
	}
	
	/**
	 * a polyhedron is composed of polygons,
	 * so it is defind by it's vertices and,
	 * this method should retrieve all the
	 * vertices of the polyhedron, as a tuple.
	 * first argument in the tuple should be original
	 * point, and the second is for update to put
	 * results in. (it is done for efficiency,
	 * to prevent a hell of a lot allocations...) 
	 * @return
	 */
	protected abstract Vector2Tuple[] getVertices();
}
