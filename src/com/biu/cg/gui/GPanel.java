package com.biu.cg.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.biu.cg.main.Game;
import com.biu.cg.math3d.Vector;

/**
 * this class is for the minimap.
 * @author gilad
 *
 */
//TODO: there are endless ideas on how to add cool features here.
//		starting from drawing the room, and maybe adding a key 
//		which can be toggled on and off for viewing the room, and
//		maybe even the cubes and tetrahedron. in case we want to 
//		draw the top view with lines for the cubes and tetrahedron
//		we can retrieve it the same way we get the position of the
//		camera. one thing to notice, if we want to preserve animation,
// 		is to get either the timer thread on Object3D class or another
//		(new) thread [FOOTNOTE_0] to repaint the minimap sequentially 
//		in order to see the animation.
//		
//		FOOTNOTE_0:
//			in that case, it should be synchronized both with
//			the timer thread on Object3D, and with the main thread.
//			i.e. most of the fields and methods of this class should be
//			synchronized with locks, and should notify() on exit, etc'...
public class GPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Game game;

	/**
	 * simple setter constructor
	 * @param game
	 */
	public GPanel(Game game) {
		this.game = game;
	}

//	/**
//	 * not sure about initialization of the component...
//	 * for the time being, it is commented out. in case
//	 * we want to do something more complicated, which 
//	 * requires initialization, we will uncomment this.
//	 */
//	public void initialize() {
//		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
//		setBackground(Color.white);
//	}
	
	/**
	 * method inherited from JPanel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		//clear the screen from previous data
		super.paintComponent(g);  
		
		//compute the location on map
		Vector pos = game.getCamPos();
		int x = (int)(pos.getX()/3) - 3 + (this.getSize().width / 2);
		int y = (int)(pos.getZ()/3) - 3 + (this.getSize().height / 2);
		
		//if in borders: draw a red circle with crossed lines
		if(x > -2 && x < getWidth() + 2 && y > -2 && y < getHeight() + 2) {
			g.setColor(Color.red);
			g.drawOval(x, y, 6, 6);
			g.drawLine(x, y+3, x+5, y+3);
			g.drawLine(x+3, y, x+3, y+5);
		}
		//if not in the map, just draw: "out of map"
		else {
			g.setColor(Color.black);
			g.drawString("out of map", getWidth() / 2 - 27, getHeight() / 2 + 4);
		}
	}
}
