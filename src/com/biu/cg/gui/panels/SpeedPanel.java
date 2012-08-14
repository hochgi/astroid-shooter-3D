package com.biu.cg.gui.panels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpeedPanel extends ImagePanel {
	
	private static final long serialVersionUID = 1L;
	private BufferedImage[] images = new BufferedImage[8];
	public SpeedPanel(){
		try {                
	          images[0] = ImageIO.read(new File("textures/cockpit/speed-2.png"));
	          images[1] = ImageIO.read(new File("textures/cockpit/speed-1.png"));
	          images[2] = ImageIO.read(new File("textures/cockpit/speed0.png"));
	          images[3] = ImageIO.read(new File("textures/cockpit/speed1.png"));
	          images[4] = ImageIO.read(new File("textures/cockpit/speed2.png"));
	          images[5] = ImageIO.read(new File("textures/cockpit/speed3.png"));
	          images[6] = ImageIO.read(new File("textures/cockpit/speed4.png"));
	          images[7] = ImageIO.read(new File("textures/cockpit/speed5.png"));
	       } catch (IOException ex) {
	            // handle exception...
	       }
	}
	
	public void setSpeed(int speed){
		image = images[speed+2];
	}
}
