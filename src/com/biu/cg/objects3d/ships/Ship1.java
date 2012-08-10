package com.biu.cg.objects3d.ships;

import com.biu.cg.main.Exercise4;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Rotator;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.physics.boundingShapes.BoundingShape;
import com.biu.cg.object3d.physics.boundingShapes.BoundingSphere;
import com.biu.cg.objects3d.physics.Collidable;
import com.biu.cg.panels.FuelPanel;

public class Ship1 extends Ship implements Collidable {
	
	
	public Ship1() {
		
		super("models/ship1/ship.obj" , "models/ship1/F02_512.jpg");
	}
	
	public Ship1(Vector position) {
		super(position, "models/ship1/ship.wng" , "models/ship1/F02_512.jpg");
		
	}

	@Override
	public float getWingHeight() {
		return -1.25f;
	}

	@Override
	public float getWingWidth() {
		return 2.5f;
	}

	@Override
	public void collisionAction(Collidable collidable) {
		switch(collidable.getType()){
		case MOTHERSHIP:
			MotherShip m = (MotherShip)collidable;
			Vector n = m.getNormalPerpendicularToPlaneAt(this.getPosition());
			//float angle = (float)Math.asin(n.dot(getOrientation().getAxis('z')));
			Vector x = getOrientation().getAxis('x');
			Vector y = getOrientation().getAxis('y');
			Vector z = getOrientation().getAxis('z');
			Vector tmp = new Vector();
			Rotator.oneTimeRotatation(x, tmp, z, (float)Math.PI);
			Rotator.oneTimeRotatation(tmp, x, n, (float)Math.PI);
			
			Rotator.oneTimeRotatation(y, tmp, z, (float)Math.PI);
			Rotator.oneTimeRotatation(tmp, y, n, (float)Math.PI);
			
			Rotator.oneTimeRotatation(new Vector(z), z, n, (float)Math.PI);
			z.negMutate();
			speedDown();
			break;
		case RELOAD:
			fuel=600;
			Exercise4.fuelPanel.setFuel(fuel);
			Exercise4.fuelPanel.repaint();
			System.out.println("RELOAD");
			break;
		default:
			canMove=true;
			break;
		}
	}

	@Override
	public BoundingShape getBoundingShape() {
		return new BoundingSphere(getVertices());
	}

	@Override
	public Type getType() {
		return Type.PLAYER_SHIP;
	}
}
