package com.biu.cg.gui.panels;

import javax.swing.JPanel;
import java.awt.*;   
import java.awt.image.BufferedImage;  

public class TiledImagePanel extends JPanel {
      
	private static final long serialVersionUID = 1L;
	Image tileImage;   
	int imageW;
	int imageH;
	int width;
	int height;
	int start;
	
	public TiledImagePanel(BufferedImage bufferedImage, int w, int h, int w2, int h2, int s) {   
	    tileImage = bufferedImage;  
	    imageW = w;
	    imageH = h;
	    width = w2;
	    height = h2;
	    start = s;
	}   
	
	protected void paintComponent(Graphics g) {    
	
	    // Tile the image to fill our area.   
	    for (int x = 0; x < width; x += imageW) {   
	        for (int y = start; y < height; y += imageH) {   
	            g.drawImage(tileImage, x, y, this);   
	        }   
	    }   
	}   
	
	public Dimension getPreferredSize() {   
	    return new Dimension(240, 240);   
	}

	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
	}  
}
