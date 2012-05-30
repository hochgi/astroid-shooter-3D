import java.awt.Frame;
import javax.media.opengl.GLCanvas;
import javax.swing.JButton;
import com.sun.opengl.util.Animator;

/**
 * main class.
 * GUI handling is done here.
 * @author gilad
 *
 */
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
    		
	public static void main(String[] args) {
		Game game = new Game();
		
		//canvas initialization
		canvas.addGLEventListener(game);
		canvas.addKeyListener(game);
		
		//minimap setup
		miniMap = new GPanel(game);
		miniMap.initialize();
		miniMap.setSize(128, 128);
		miniMap.setLocation(0, 0);
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
	
	/**
	 * exit
	 */
	public static void exit(){
		animator.stop();
		frame.dispose();
		System.exit(0);
    }
}
