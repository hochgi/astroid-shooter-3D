package com.biu.cg.panels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RocketPanel extends ImagePanel {
	private BufferedImage[] images = new BufferedImage[4];
	private int rocketCounter=3;
	public RocketPanel(){
		try {                
	          images[0] = ImageIO.read(new File("textures/cockpit/rocket0.png"));
	          images[1] = ImageIO.read(new File("textures/cockpit/rocket1.png"));
	          images[2] = ImageIO.read(new File("textures/cockpit/rocket2.png"));
	          images[3] = ImageIO.read(new File("textures/cockpit/rocket3.png"));
	          
	          image = images[3];
	       } catch (IOException ex) {
	            // handle exception...
	       }
	}
	
	public void removeRocket(){
		if(rocketCounter>0)
			image = images[--rocketCounter];
	}
	
	public void restore(){
		rocketCounter = 3;
		image = images[rocketCounter];
	}
	
}
