package com.biu.cg.main;

import java.awt.Frame;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JButton;
import com.biu.cg.gui.ButtonEnum;
import com.biu.cg.gui.GPanel;
import com.biu.cg.gui.GameActionListener;
import com.sun.opengl.util.Animator;

/**
 * main class.
 * GUI handling is done here.
 * @author gilad
 *
 */
public class Exercise4 {

    public static GLCanvas canvas = new GLCanvas(getCapabilities());
    static Frame frame = new Frame("Astroid Shooter - 0.1 Beta");
    static Animator animator = new Animator(canvas); 
    static JButton rButton = new JButton("RCR");
    static JButton cButton = new JButton("ICC");
    static JButton tButton = new JButton("CTS");
    static JButton pButton = new JButton("RCO");
    static JButton eButton = new JButton("EXP");
    public static GPanel  miniMap;
    		
	public static void main(String[] args) {
		Game game = new Game();
		
		//canvas initialization
		canvas.addGLEventListener(game);
		canvas.addKeyListener(game);
		
		//minimap setup
		miniMap = new GPanel(game);
		//miniMap.initialize(); //should we initialize it?
		miniMap.setSize(128, 128);
		miniMap.setLocation(0, 0);
		miniMap.addKeyListener(game);
		
		//reverse cube rotation button setup
		rButton.addActionListener(new GameActionListener(game, ButtonEnum.RCR));
		rButton.setLocation(0, 128);
		rButton.setSize(64, 64);
		rButton.setToolTipText("Reverse Cubes Rotation");
		
		//inverse cubes color button setup
		cButton.addActionListener(new GameActionListener(game, ButtonEnum.ICC));
		cButton.setLocation(64, 128);
		cButton.setSize(64, 64);
		cButton.setToolTipText("Inverse Cubes Color");

		//change tetrahedron speed button setup
		tButton.addActionListener(new GameActionListener(game, ButtonEnum.CTS));
		tButton.setLocation(0, 192);
		tButton.setSize(64, 64);
		tButton.setToolTipText("Change Tetrahedron Speed");
		
		//reset camera orientation button setup
		pButton.addActionListener(new GameActionListener(game, ButtonEnum.RCO));
		pButton.setLocation(64, 192);
		pButton.setSize(64, 64);
		pButton.setToolTipText("Reset Camera Orientation");
		
		//test explosion effect
		eButton.addActionListener(new GameActionListener(game, ButtonEnum.EXP));
		eButton.setLocation(0, 256);
		eButton.setSize(128, 64);
		eButton.setToolTipText("Test Explosion Effect");

		//add components to the frame
		frame.add(miniMap);
		frame.add(rButton);
		frame.add(cButton);
		frame.add(tButton);
		frame.add(pButton);
		frame.add(eButton);
		frame.add(canvas);
		//frame.setUndecorated(true);
		//sframe.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		animator.start();
		//make sure that the canvas gets the focus
		//so the keys will be responsive at once.
		while(!canvas.hasFocus()){
			canvas.requestFocus();
		}
	}
	
    private static GLCapabilities getCapabilities() {
        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);
        return caps;
    }
	
	/**
	 * exit (duh...!)
	 */
	public static void exit(){
		animator.stop();
		frame.dispose();
		System.exit(0);
    }
}
