package com.biu.cg.objects3d.ships;

import java.util.ArrayList;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.AABB;
import com.biu.cg.object3d.physics.boundingShapes.AABBSuit;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.objects3d.physics.Collidables;

public class MotherShip extends Model3D implements Collidable {

	AABBSuit aabbs;
	ArrayList<AABB> aabbarray = new ArrayList<AABB>();
	static final Vector xn = new Vector(-1, 0, 0);
	static final Vector yn = new Vector(0, -1, 0);
	static final Vector zn = new Vector(0, 0, -1);
	static final Vector xp = new Vector(1, 0, 0);
	static final Vector yp = new Vector(0, 1, 0);
	static final Vector zp = new Vector(0, 0, 1);
	
	public MotherShip(Vector position, float scale) {
		super(position, "models/mothership/mothership.wng" , "models/mothership/mothership.jpg");
		this.scale = scale;
		
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b1.wng" , scale));
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b2.wng" , scale));
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b3.wng" , scale));
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b4.wng" , scale));
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b5.wng" , scale));
		aabbarray.add(new AABB(position, "models/mothership/BoundingBoxs/b6.wng" , scale));
		
		aabbs = new AABBSuit(aabbarray);
		
		Vector v = new Vector(position);
		v.y+=150;
		
		Collidables.registerObject(new ReloadPoint(v));
	}

	@Override
	protected void update() {}

	@Override
	public void collisionAction(Collidable collidable) {}

	@Override
	public BoundingShape getBoundingShape() {
		return aabbs;
	}

	@Override
	public Type getType() {
		return Type.MOTHERSHIP;
	}

	public Vector getNormalPerpendicularToPlaneAt(Vector position) {
		// TODO determine what normal to return according to collision position
		return new Vector(xp);
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override
//	protected void synchronizedDraw(GLAutoDrawable gLDrawable) {
//		// TODO: implement.
//		
//		
//		for(AABB a : aabbarray){
//			a.draw(gLDrawable);
//		}
//		
//		
//		double x = orientation.getPosition().getX();
//		double y = orientation.getPosition().getY();
//		double z = orientation.getPosition().getZ();
//		
//		
//		
//		final GL gl = gLDrawable.getGL();
//		
//		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT );
//        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT );   
//		texture.bind();
//		
//		Orientation o = orientation;
//		//int counter=0;
//		for(Face f : builder.faces){
//			//Random rand = new Random();
//			switch(f.vertices.size()){
//			case 4:
//			{
//				gl.glBegin(GL.GL_QUADS);
//				for(int i=0 ; i<4 ; i++){
//					
//					Vector X = o.getAxis('x').mul(f.vertices.get(i).v.x);
//					Vector Y = o.getAxis('y').mul(f.vertices.get(i).v.y);
//					Vector Z = o.getAxis('z').mul(f.vertices.get(i).v.z);
//					
//					if(f.vertices.get(i).t!=null)	
//						gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
//					gl.glVertex3d(x + (X.x + Y.x + Z.x)*scale , y + (X.y + Y.y + Z.y)*scale , z + (X.z + Y.z + Z.z)*scale);
//					
//				}
//				gl.glEnd();
//				break;
//			}
//			case 3:
//				gl.glBegin(GL.GL_TRIANGLES);
//	//			gl.glColor3f(0.5f, 0.25f, 0.25f);
//				for(int i=0 ; i<3 ; i++){
//					
//					Vector X = o.getAxis('x').mul(f.vertices.get(i).v.x);
//					Vector Y = o.getAxis('y').mul(f.vertices.get(i).v.y);
//					Vector Z = o.getAxis('z').mul(f.vertices.get(i).v.z);
//					
//					
//					if(f.vertices.get(i).t!=null)
//						gl.glTexCoord2f(f.vertices.get(i).t.u, f.vertices.get(i).t.v);
//					gl.glVertex3d(x + (X.x + Y.x + Z.x)*scale , y + (X.y + Y.y + Z.y)*scale , z + (X.z + Y.z + Z.z)*scale);
//					
//				}	
//				gl.glEnd();
//				break;
//			}
//			
//		}
//	}

}
