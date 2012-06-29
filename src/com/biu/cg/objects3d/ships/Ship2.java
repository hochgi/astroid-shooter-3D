package com.biu.cg.objects3d.ships;

import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Model3D;

public class Ship2 extends Model3D  {

	public Ship2(String objFile, String texture) {
		super("models/ship2/ship.obj" , "models/ship1/F03_512.jpg");
		// TODO Auto-generated constructor stub
	}

	public Ship2(Vector position) {
		super(position, "models/ship2/ship.wng" , "models/ship2/F03_512.jpg");
		// TODO Auto-generated constructor stub
		
	}
}
