package com.biu.cg.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
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
		LinkedList<Vector> asteroids = game.getAsteroidsInSquareRange(625);
		game.convertAbsoluteVectorToShipRelative(asteroids);
		
//		float dist;
		int x,y;
//		for (Vector v : asteroids) {
//			dist = v.sqrDistanceTo(game.getCamPos());
//			if(dist <= 10000f){
//				g.setColor(Color.black);
//			}
//			else if(dist <= 40000f){
//				g.setColor(Color.darkGray);
//			}
//			else if(dist <= 90000f){
//				g.setColor(Color.gray);
//			}
//			else{
//				g.setColor(Color.lightGray);
//			}//g.setColor(Color.black);
//
//			x = (int)(v.getX()/400) - 2 + (this.getSize().width / 2);
//			y = (int)(v.getZ()/400) - 2 + (this.getSize().height / 2);
//			System.out.println("draw asteroid at mm: (" +x+ "," +y+ ")");
//			System.out.println("asteroid properties: "  +  v.toString());
//			System.out.println("camera vectors base: "+game.getCamera());
//			
//			g.fillOval(x, y, 4, 4);
//		}
		
		x = (int)(this.getSize().width / 2) -3;
		y = (int)(this.getSize().height / 2) -3;

		g.setColor(Color.red);
		g.drawOval(x, y, 6, 6);
		g.drawLine(x, y+3, x+5, y+3);
		g.drawLine(x+3, y, x+3, y+5);
	}
}
