package com.biu.cg.objects3d.ships;

import com.biu.cg.math3d.Vector;

public class Ship2 extends Ship  {

	public Ship2(String objFile, String texture) {
		super("models/ship2/ship.obj" , "models/ship1/F03_512.jpg");
		// TODO Auto-generated constructor stub
	}

	public Ship2(Vector position) {
		super(position, "models/ship2/ship.wng" , "models/ship2/F03_512.jpg");
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public float getWingHeight() {
		return 0;
	}

	@Override
	public float getWingWidth() {
		return 0;
	}
}
