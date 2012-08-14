package com.biu.cg.objects3d.ships;

import java.util.ArrayList;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.AABB;
import com.biu.cg.object3d.physics.boundingShapes.AABBSuit;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;

/**
 * MotherShip.
 * @author Irzh
 *
 */
public class MotherShip extends Model3D implements Collidable {

	AABBSuit aabbs;
	ArrayList<AABB> aabbarray = new ArrayList<AABB>();
	static final Vector x = new Vector(1, 0, 0);
	static final Vector y = new Vector(0, 1, 0);
	static final Vector z = new Vector(0, 0, 1);
	
	/**
	 * c'tor.
	 * @param position
	 * @param scale
	 */
	public MotherShip(Vector position, float scale) {
		super(position, "models/mothership/mothership.wng" , "models/mothership/mothership.jpg");
		
		//set the scale.
		this.scale = scale;
		
		// load AABBSuit.
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b1.wng" , scale));
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b2.wng" , scale));
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b3.wng" , scale));
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b4.wng" , scale));
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b5.wng" , scale));
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b6.wng" , scale));
		
		aabbs = new AABBSuit(aabbarray);
		
		Vector v = new Vector(position);
		
		
		// register ReloadPoint as collidable.
		v.y+=150;
		Collidables.registerObject(new ReloadPoint(v));
	}

	@Override
	protected void update() {}

	@Override
	public void collisionAction(Collidable collidable) {}

	/**
	 * gets object's bounding shape.
	 */
	@Override
	public BoundingShape getBoundingShape() {
		return aabbs;
	}

	/**
	 * gets the type of the object.
	 */
	@Override
	public Type getType() {
		return Type.MOTHERSHIP;
	}

	/**
	 * return the Normal Perpendicular To Plane. (used to react on collision between player's ship and mothership.)
	 * @param position
	 * @param shape
	 * @return
	 */
	public Vector getNormalPerpendicularToPlaneAt(Vector position, BoundingSphere shape) {
		AABB col=null;
		
		for(AABB b : aabbs.getAabbs()){
			if(b.intersect(shape)){
				col=b;
				break;
			}
		}
		
		if(position.y < col.maxY && position.y > col.minY && position.z < col.maxZ && position.z > col.minZ) 
			return new Vector(x);
		if(position.x < col.maxX && position.x > col.minX && position.z < col.maxZ && position.z > col.minZ) 
			return new Vector(y);
		if(position.y < col.maxY && position.y > col.minY && position.x < col.maxX && position.x > col.minX) 
			return new Vector(z);
		if(position.y < col.maxY && position.y > col.minY) 
			return new Vector(x.add(z, 1).normalize());
		if(position.x < col.maxX && position.x > col.minX) 
			return new Vector(y.add(z, 1).normalize());
		if(position.y < col.maxY && position.y > col.minY) 
			return new Vector(z.add(x, 1).normalize());
		
		return new Vector(x.add(y, 1).normalize().add(z, 1).normalize());
	}
}
