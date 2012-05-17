import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.Animator;

/**
 * @author gilad
 *
 */
public class Exercise3 {

    float rotateT = 0.0f;
    static GLU glu = new GLU();
    static GLCanvas canvas = new GLCanvas();
    static Frame frame = new Frame("Exercise 3 - room navigation");
    static Animator animator = new Animator(canvas); 

	public static void main(String[] args) {
		Game game = new Game(animator, glu);
		canvas.addGLEventListener(game);
		canvas.addKeyListener(game);
		frame.add(canvas);
		frame.setSize(640, 480);
//		frame.setUndecorated(true);
//		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			    exit();
			}
		});
		frame.setVisible(true);
		animator.start();
		canvas.requestFocus();
	}
	
	public static void exit(){
		animator.stop();
		frame.dispose();
		System.exit(0);
    }
}
