package com.biu.cg.main;

import java.awt.Frame;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JButton;
import com.biu.cg.gui.ButtonEnum;
import com.biu.cg.gui.GPanel;
import com.biu.cg.gui.GameActionListener;
import com.biu.cg.panels.FuelPanel;
import com.biu.cg.panels.ImagePanel;
import com.biu.cg.panels.RocketPanel;
import com.biu.cg.panels.SpeedPanel;
import com.sun.opengl.util.Animator;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
    //static ClassLoader cl = this.getClass().getClassLoader();
    static ImageIcon img = new ImageIcon("textures/cockpit/panel.png");
    static JLabel label;
    static public SpeedPanel speedPanel;
    static public FuelPanel fuelPanel;
    static public RocketPanel rocketPanel;
    static public int points=0;
    static public int earthHealth=100;
    static private Label pointsLabel = new Label("Points: 0");
    static private Label earthHealthLabel = new Label("Earth health: " + earthHealth + "%");
    
    
    public static GPanel  miniMap;
    
    
    public static void addPoints(int pointsToAdd){
    	points+=pointsToAdd;
    	pointsLabel.setText("Points: " + points);
    }
    
    public static void reduceEarthHealth(int pointsToReduce){
    	earthHealth-=pointsToReduce;
    	earthHealthLabel.setText("Earth health: " + earthHealth + "%");
    }
    
    
    public static void showInfo(){
    	JOptionPane.showMessageDialog(frame, "SAVE PLANET EARTH FROM THE EVIL ASTEROIDS!!\n" +
    			"Don't let asteroids hit earth.\n" +
    			"============== BUTTON DESCRIPTION ==============\n" +
    			"I - look up\n" +
    			"K - look down\n" +
    			"J - turn left\n" +
    			"L - turn right\n" +
    			"U - roll left\n" +
    			"O - roll right\n" +
    			"0 - speed up\n" +
    			"9 - speed down\n" +
    			"W - turbo speed\n" +
    			"Space - shoot photon\n" +
    			"R - shoot rocket\n");
    }
    		
	public static void main(String[] args) {
		Game game = new Game();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//canvas initialization
		canvas.addGLEventListener(game);
		canvas.addKeyListener(game);
		
		//minimap setup
		miniMap = new GPanel(game);
		//miniMap.initialize(); //should we initialize it?
		miniMap.setSize(128, 128);
		miniMap.setLocation(0, 0);
		miniMap.addKeyListener(game);
		
		
		
		ImagePanel leftPanel = new ImagePanel("textures/cockpit/panel1.png");
		ImagePanel rightPanel = new ImagePanel("textures/cockpit/panel1.png");
		speedPanel = new SpeedPanel();
		
		speedPanel.setSize(109,109);
		speedPanel.setLocation(40, (int)(screenSize.height - 222+40));
		
		fuelPanel = new FuelPanel();
		fuelPanel.setSize(209,109);
		fuelPanel.setLocation(180, (int)(screenSize.height - 222+40));
		
		
		
		
		
		
		leftPanel.setLocation(0, (int)(screenSize.height - 222));
		leftPanel.setSize(395,193);
		leftPanel.add(earthHealthLabel);
		
		rocketPanel = new RocketPanel();
		rocketPanel.setSize(109, 109);
		rocketPanel.setLocation(screenSize.width - 395 + 260, (int)(screenSize.height - 222 + 40));
		
		rightPanel.setLocation(screenSize.width - 395, (int)(screenSize.height - 222));
		rightPanel.setSize(395,193);
		
		
		
		rightPanel.add(pointsLabel);
		
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
		frame.add(speedPanel);
		frame.add(fuelPanel);
		frame.add(rocketPanel);
		frame.add(leftPanel);
		frame.add(rightPanel);
		
		//add components to the frame
//		frame.add(miniMap);
//		frame.add(rButton);
//		frame.add(cButton);
//		frame.add(tButton);
//		frame.add(pButton);
//		frame.add(eButton);
		frame.add(canvas);
		
		frame.setUndecorated(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
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
	
	public static void exit(String msg){
		JOptionPane.showMessageDialog(frame, msg);
		exit();
    }
}
