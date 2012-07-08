package com.biu.cg.objects3d.ships;

import com.biu.cg.math3d.Vector;
public class Ship1 extends Ship {
	
	
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
}
