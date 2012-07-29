package com.biu.cg.panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FuelPanel extends ImagePanel {
	private BufferedImage[] images = new BufferedImage[7];
	public FuelPanel(){
		try {                
	          images[0] = ImageIO.read(new File("textures/cockpit/fuel0.png"));
	          images[1] = ImageIO.read(new File("textures/cockpit/fuel1.png"));
	          images[2] = ImageIO.read(new File("textures/cockpit/fuel2.png"));
	          images[3] = ImageIO.read(new File("textures/cockpit/fuel3.png"));
	          images[4] = ImageIO.read(new File("textures/cockpit/fuel4.png"));
	          images[5] = ImageIO.read(new File("textures/cockpit/fuel5.png"));
	          images[6] = ImageIO.read(new File("textures/cockpit/fuel6.png"));
	          
	          image = images[6];
	       } catch (IOException ex) {
	            // handle exception...
	       }
	}
	
	public void setFuel(int fuel){
		image = images[fuel];
	}
}
