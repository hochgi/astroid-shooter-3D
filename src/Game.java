import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;


public class Game implements GLEventListener, KeyListener {


	private float pitch         = 0.0f;
	private float heading       = 0.0f;
	private float roll          = 0.0f;
	private float transForwardT = 0.0f;
	private float transSidewayT = 0.0f;
	private float transUpwardsT = 0.0f;
	private Animator animator;
	private GLU glu;

	public Game(Animator animator, GLU glu) {
		this.animator = animator;
		this.glu = glu;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_ESCAPE:
			Exercise3.exit();
			break;
			
		//////////////	
		//ROTATIONS://
		//////////////
			
		//pitch ++
		case KeyEvent.VK_I:
			pitch += 0.2f;
			break;
		//pitch --
		case KeyEvent.VK_K:
			pitch -= 0.2f;
			break;
		//heading ++
		case KeyEvent.VK_L:
			heading += 0.2f;
			break;
		//heading --
		case KeyEvent.VK_J:
			heading -= 0.2f;
			break;
		//roll ++
		case KeyEvent.VK_O:
			roll += 0.2f;
			break;
		//roll --
		case KeyEvent.VK_U:
			roll -= 0.2f;
			break;
			
		/////////////////	
		//TRANSLATIONS://
		/////////////////
			
		//forwards
		case KeyEvent.VK_W:
			break;
		//backwards
		case KeyEvent.VK_S:
			break;
		//left
		case KeyEvent.VK_A:
			break;
		//right
		case KeyEvent.VK_D:
			break;
		//upwards
		case KeyEvent.VK_E:
			break;
		//downwards
		case KeyEvent.VK_Q:
			break;
		default:
			//illegal key was typed
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL gl = gLDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
	 
		gl.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(heading, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(roll, 0.0f, 0.0f, 1.0f);
		//gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
	 
		gl.glBegin(GL.GL_QUADS);

		//1
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3f(-1.0f, 0.5f, -1.0f);
		
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3f(-1.0f, 0.0f, -1.0f);
		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3f(-1.0f, 0.0f, 1.0f);
		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3f(-1.0f, 0.5f, 1.0f);
		
		//2
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3f(-1.0f, 0.0f, 1.0f);
		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3f(-1.0f, 0.5f, 1.0f);
		
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3f(1.0f, 0.5f, 1.0f);
		
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3f(1.0f, 0.0f, 1.0f);
		
		//3
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3f(1.0f, 0.5f, 1.0f);
		
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3f(1.0f, 0.0f, 1.0f);
		
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3f(1.0f, 0.0f, -1.0f);
		
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3f(1.0f, 0.5f, -1.0f);
		
		//4
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3f(1.0f, 0.0f, -1.0f);
		
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3f(1.0f, 0.5f, -1.0f);
		
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3f(-1.0f, 0.5f, -1.0f);
		
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3f(-1.0f, 0.0f, -1.0f);
		
		gl.glEnd();
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gLDrawable.addKeyListener(this);
	}

	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		GL gl = gLDrawable.getGL();
		if(height <= 0) {
		    height = 1;
		}
		float h = (float)width / (float)height;
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(50.0f, h, 1.0, 1000.0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

}
