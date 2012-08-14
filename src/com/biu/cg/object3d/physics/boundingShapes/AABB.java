package com.biu.cg.object3d.physics.boundingShapes;

import java.util.ArrayList;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.Object3D;

/**
 * AABB - implementation of BoundingShape and an extension of Model3D (for debug purposes).
 * @author Irzh
 *
 */
public class AABB extends Model3D implements BoundingShape{
	
	//in and max values.
	public float minX;
	public float minY;
	public float minZ;
	
	public float maxX;
	public float maxY;
	public float maxZ;
	
	public float distX;
	public float distY;
	public float distZ;
	
	Vector center;
	
	/**
	 * gets the center of the AABB.
	 * @return
	 */
	public Vector getCenter() {
		return center;
	}
	
	public float getDistX() {
		return distX;
	}
	
	public float getDistY() {
		return distY;
	}
	
	public float getDistZ() {
		return distZ;
	}

	/**
	 * AABB c'tor.
	 * @param position - the position of the AABB.
	 * @param objFile - the obj file from where we are taking the box.
	 * @param scale - the scale of the box.
	 */
	public AABB(Vector position, String objFile, float scale) {
		super(position , objFile, null);

		this.scale = scale;
		
		ArrayList<Vector> vertices = getVertices();
		
		// calculate the mins and maxs. 
		maxX = minX = vertices.get(0).x;
		maxY = minY = vertices.get(0).y;
		maxZ = minZ = vertices.get(0).z;
		
		for(int i=1; i<vertices.size() ; i++){
			Vector v = vertices.get(i);
			
			if(v.x < minX)
				minX = v.x;
			
			if(v.y < minY)
				minY = v.y;
			
			if(v.z < minZ)
				minZ = v.z;
			
			if(v.x > maxX)
				maxX = v.x;
			
			if(v.y > maxY)
				maxY = v.y;
			
			if(v.z > maxZ)
				maxZ = v.z;
		}
		
		distX = maxX - minX;
		distY = maxY - minY;
		distZ = maxZ - minZ;
		
		center = new Vector(distX/2, distZ/2, distY/2);
	}

	/**
	 * intersection with AABB shape - according to the algorithm learned in class.
	 */
	@Override
	public boolean intersect(AABB shape) {		
		float dist = center.sqrDistanceTo(shape.getCenter());
		dist = (float) Math.sqrt((double)dist);
		
		if(dist < distX || dist < distY || dist < distZ)
			return true;
		
		return false;
	}

	
	/**
	 * intersection with BoundingSphere shape - according to the algorithm learned in class.
	 */
	@Override
	public boolean intersect(BoundingSphere shape) {
		float  dmin=0;
		
		if( shape.getCenter().x < minX ) 
			dmin += Math.pow(shape.getCenter().x - minX , 2); 
		else if( shape.getCenter().x > maxX )
			dmin +=  Math.pow( shape.getCenter().x - maxX , 2 );     
		
		if( shape.getCenter().y < minY ) 
			dmin +=  Math.pow(shape.getCenter().y - minY , 2 ); 
		else if( shape.getCenter().y > maxY )
			dmin +=  Math.pow( shape.getCenter().y - maxY , 2 );     
		
		if( shape.getCenter().z < minZ ) 
			dmin +=  Math.pow(shape.getCenter().z - minZ , 2 ); 
		else if( shape.getCenter().z > maxZ )
			dmin +=  Math.pow( shape.getCenter().z - maxZ , 2 );     
		
		
		if(dmin <= Math.pow(shape.getRadius(),2))
			return true;
		
		return false;
	}

	/**
	 * intersection with Dot shape - according to the algorithm learned in class.
	 */
	@Override
	public boolean intersect(Dot shape) {
		float  dmin=0;
		
		if( shape.getPos().x < minX ) 
			dmin += Math.pow(shape.getPos().x - minX , 2); 
		else if( shape.getPos().x > maxX )
			dmin +=  Math.pow( shape.getPos().x - maxX , 2 );     
		
		if( shape.getPos().y < minY ) 
			dmin +=  Math.pow(shape.getPos().y - minY , 2 ); 
		else if( shape.getPos().y > maxY )
			dmin +=  Math.pow( shape.getPos().y - maxY , 2 );     
		
		if( shape.getPos().z < minZ ) 
			dmin +=  Math.pow(shape.getPos().z - minZ , 2 ); 
		else if( shape.getPos().z > maxZ )
			dmin +=  Math.pow( shape.getPos().z - maxZ , 2 );     
		
		
		if(dmin <= 4)
			return true;
		
		return false;
	}

	@Override
	public Object3D getCreator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * intersection with AABBSuit shape (no need to implement).
	 */
	@Override
	public boolean intersect(AABBSuit shape) {
		// TODO Auto-generated method stub
		return false;
	}

}
