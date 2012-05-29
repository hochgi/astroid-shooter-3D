import java.awt.Cursor;
import java.awt.Frame;
import javax.media.opengl.GLCanvas;
import javax.swing.JButton;

import com.sun.opengl.util.Animator;

public class Exercise3 {

    float rotateT = 0.0f;
    static GLCanvas canvas = new GLCanvas();
    static Frame frame = new Frame("Exercise 3 - room navigation");
    static Animator animator = new Animator(canvas); 
    static JButton rButton = new JButton("RCR");
    static JButton cButton = new JButton("ICC");
    static JButton tButton = new JButton("CTS");
    static JButton pButton = new JButton("RCO");
    static GPanel  miniMap;
//    static JPanel rotInfo = new JPanel();
//    static JPanel posInfo = new JPanel();
//	static JLabel[] rotInfoLabels;
//	static JLabel[] posInfoLabels;
    		
	public static void main(String[] args) {
		Game game = new Game();
		
		canvas.addGLEventListener(game);
		canvas.addKeyListener(game);
		
		miniMap = new GPanel(game);
		miniMap.initialize();
		miniMap.setSize(128, 128);
		miniMap.setLocation(0, 0);
		miniMap.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		miniMap.addKeyListener(game);
		
		rButton.addActionListener(new GameActionListener(game, ButtonEnum.RCR));
		rButton.setLocation(0, 128);
		rButton.setSize(64, 64);
		rButton.setToolTipText("Reverse Cubes Rotation");
		
		cButton.addActionListener(new GameActionListener(game, ButtonEnum.ICC));
		cButton.setLocation(64, 128);
		cButton.setSize(64, 64);
		cButton.setToolTipText("Inverse Cubes Color");

		tButton.addActionListener(new GameActionListener(game, ButtonEnum.CTS));
		tButton.setLocation(0, 192);
		tButton.setSize(64, 64);
		tButton.setToolTipText("Change Tetrahedron Speed");
		
		pButton.addActionListener(new GameActionListener(game, ButtonEnum.RCP));
		pButton.setLocation(64, 192);
		pButton.setSize(64, 64);
		pButton.setToolTipText("Reset Camera Orientation");

		frame.add(miniMap);
		frame.add(rButton);
		frame.add(cButton);
		frame.add(tButton);
		frame.add(pButton);
		frame.add(canvas);
//		frame.setSize(640, 480);
		frame.setUndecorated(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//		frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//			    exit();
//			}
//		});
		frame.setVisible(true);
		animator.start();
		while(!canvas.hasFocus()){
			canvas.requestFocus();
		}
	}
	
	public static void exit(){
		animator.stop();
		frame.dispose();
		System.exit(0);
    }
}
