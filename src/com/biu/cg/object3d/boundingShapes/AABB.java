package com.biu.cg.object3d.boundingShapes;

import com.biu.cg.math3d.Vector;

public class AABB implements BoundingShape{
	float minX;
	float minY;
	float minZ;
	
	float maxX;
	float maxY;
	float maxZ;
	
	float distX;
	float distY;
	float distZ;
	
	Vector center;
	
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

	public AABB(Vector[] vertices){	
		maxX = minX = vertices[0].x;
		maxY = minY = vertices[0].y;
		maxZ = minZ = vertices[0].z;
		
		for(int i=1; i<vertices.length ; i++){
			Vector v = vertices[i];
			
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
		
		center.x = distX/2;
		center.y = distZ/2;
		center.z = distY/2;
	}

	@Override
	public boolean intersect(AABB shape) {
		// TODO Auto-generated method stub
		
		float dist = center.sqrDistanceTo(shape.getCenter());
		dist = (float) Math.sqrt((double)dist);
		
		if(dist < distX || dist < distY || dist < distZ)
			return true;
		
		return false;
	}

}
