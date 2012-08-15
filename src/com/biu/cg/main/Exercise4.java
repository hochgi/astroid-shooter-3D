package com.biu.cg.main;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.imageio.ImageIO;   
import javax.swing.*; 
import com.biu.cg.gui.GPanel;
import com.biu.cg.gui.panels.FuelPanel;
import com.biu.cg.gui.panels.RocketPanel;
import com.biu.cg.gui.panels.SpeedPanel;
import com.biu.cg.gui.panels.TiledImagePanel;
import com.sun.opengl.util.Animator;
import java.awt.*;   
import java.awt.image.BufferedImage;   
import java.io.*;   
 
/**
 * main class.
 * GUI handling is done here.
 * @author gilad
 *
 */
public class Exercise4 {
    public static GLCanvas canvas = new GLCanvas(getCapabilities());
    static public Frame frame = new Frame("Astroid Shooter - 0.1 Beta");
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
    
    public static Record getRec() {
		return Record.readFile();
	}
    
    public static void setRec(int rec) {
		Record.writeFile(new Record(rec));
	}
    
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
    			"Stay away from Earth's atmosphere - it realy burns!\n" +
    			"You can refill at any time at your mothership.\n" +
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
    			"R - shoot rocket\n" +
    			"================================================\n" +
    			"Current record: " + getRec().getRecord());
    }
    		
	public static void main(String[] args) {
		Game game = new Game();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = screenSize.width;
		int h = screenSize.height;
		TiledImagePanel tilePanel = null;
		try {
			BufferedImage image = ImageIO.read(new File("textures/cockpit/panel_all_tile_80X200.jpg"));
			tilePanel = new TiledImagePanel(image, 80, 200, w, h, h - 200);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}  
		
		//canvas initialization
		canvas.addGLEventListener(game);
		canvas.addKeyListener(game);
		canvas.setBounds(0,0, w, h - 200);
//		canvas.doLayout();
		
		//minimap setup
		miniMap = new GPanel(game);
		//miniMap.initialize(); //should we initialize it?
		miniMap.setSize(256, 160);
		miniMap.setLocation(400, h - 180);
		miniMap.addKeyListener(game);

		//miniMap.setVisible(false);
		
		pointsLabel.setBounds((w / 2) - 55, h - 110, 100, 20);
		pointsLabel.doLayout();
		earthHealthLabel.setBounds((w / 2) + 55, h - 110, 130, 20);
		earthHealthLabel.doLayout();
		
		speedPanel = new SpeedPanel();
		speedPanel.setSize(109,109);
		speedPanel.setLocation(45, h - 154);
		speedPanel.doLayout();
		
		fuelPanel = new FuelPanel();
		fuelPanel.setSize(209,109);
		fuelPanel.setLocation(180, h - 154);
		fuelPanel.doLayout();

		rocketPanel = new RocketPanel();
		rocketPanel.setSize(109, 109);
		rocketPanel.setLocation(w - 154,h - 154);
		rocketPanel.doLayout();
		
		//tilePanel.add(miniMap);
		tilePanel.setBounds(0,h - 200, w, 200);
		tilePanel.doLayout();
		frame.add(miniMap);
		frame.add(canvas);
		frame.add(pointsLabel);
		frame.add(earthHealthLabel);
		frame.add(speedPanel);
		frame.add(fuelPanel);
		frame.add(rocketPanel);
		frame.add(tilePanel);		
		
		
		frame.setUndecorated(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		frame.invalidate();
		
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
	
	public static void exitWithMessage(){
		if(points>getRec().getRecord()){
			setRec(points);
			JOptionPane.showMessageDialog(frame, "Game over!!\n" + "NEW RECORD: " + points);
		}else{
			JOptionPane.showMessageDialog(frame, "What a shame... You didn't save earth :( \n" +
					"but at least you earned " + Exercise4.points + " points :)");
		}
		exit();
    }
}
