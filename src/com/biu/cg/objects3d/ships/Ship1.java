package com.biu.cg.objects3d.ships;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Model3D;

public class Ship1 extends Model3D {

	public Ship1() {
		super("models/ship1/ship.obj" , "models/ship1/F02_512.jpg");
		// TODO Auto-generated constructor stub
		
	}
	
	public Ship1(Vector position) {
		super(position, "models/ship1/ship.obj" , "models/ship1/F02_512.jpg");
		// TODO Auto-generated constructor stub
		
	}

}
