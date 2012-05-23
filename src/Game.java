import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;


public class Game implements GLEventListener, KeyListener {

	private GLU glu;
	private Orientation orientation = new Orientation(new Vector(0.0,0.5,0.0),
			  										  new Vector(1.0,0.0,0.0), 
			  										  new Vector(0.0,1.0,0.0),
													  new Vector(0.0,0.0,1.0));
	private double pTheta = Math.toRadians(0.72);
	private double nTheta = (Math.PI * 2) - pTheta;
	private double expansionFactor = 100.0;

	

	public Game(Animator animator, GLU glu) {
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
			orientation.rotatePitch(pTheta);
			break;
		//pitch --
		case KeyEvent.VK_K:
			orientation.rotatePitch(nTheta);
			break;
		//heading ++
		case KeyEvent.VK_L:
			orientation.rotateHeading(pTheta);
			break;
		//heading --
		case KeyEvent.VK_J:
			orientation.rotateHeading(nTheta);
			break;
		//roll ++
		case KeyEvent.VK_O:
			orientation.rotateRoll(nTheta);
			break;
		//roll --
		case KeyEvent.VK_U:
			orientation.rotateRoll(pTheta);
			break;
			
		/////////////////	
		//TRANSLATIONS://
		/////////////////
			
		//forwards
		case KeyEvent.VK_W:
			orientation.translateForward();
			break;
		//backwards
		case KeyEvent.VK_S:
			orientation.translateBackward();
			break;
		//left
		case KeyEvent.VK_A:
			orientation.translateLeftward();
			break;
		//right
		case KeyEvent.VK_D:
			orientation.translateRightward();
			break;
		//upwards
		case KeyEvent.VK_E:
			orientation.translateUpward();
			break;
		//downwards
		case KeyEvent.VK_Q:
			orientation.translateDownward();
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

		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();	
		glu.gluPerspective(50.0f, 1, 1.0, 1000.0);
		
		Vector camPos = orientation.getPosition();
		Vector target = orientation.getTargetLookAtVector();
		Vector upVect = orientation.getUpVector();
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(camPos.getX(), camPos.getY(), camPos.getZ(), 
					  target.getX(), target.getY(), target.getZ(), 
					  upVect.getX(), upVect.getY(), upVect.getZ());
	 
		gl.glBegin(GL.GL_QUADS);

		//0
		gl.glColor3f(0.5f, 0.25f, 0.25f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(0.25f, 0.5f, 0.25f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.25f, 0.25f, 0.5f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.33f, 0.33f, 0.33f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		//1
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor,1.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		//2
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		//3
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, -1.0*expansionFactor);
		
		//4
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 1.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		//5
		gl.glColor3f(0.5f, 0.25f, 0.25f);
		gl.glVertex3d(-1.0*expansionFactor, 1.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(0.25f, 0.5f, 0.25f);
		gl.glVertex3d(-1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.25f, 0.25f, 0.5f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.33f, 0.33f, 0.33f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glEnd();
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {}

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
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
	}

}
