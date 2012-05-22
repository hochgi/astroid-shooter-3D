import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;


public class Game implements GLEventListener, KeyListener {
	private double transIndex = 0.025f;
	private double rotationIndex = 0.5f;

	private float pitch         = 0.0f;
	private float heading       = 0.0f;
	private float roll          = 0.0f;
	private float transForwardT = 0.0f;
	private float transSidewayT = 0.0f;
	private float transUpwardsT = 0.0f;
	private Animator animator;
	private GLU glu;
	private double camPosX = 0.0;
	private double camPosY = 0.5;
	private double camPosZ = -1.0;
	private double targetX = 0.0;
	private double targetY = 0.5;
	private double targetZ = 0.0;
	private double upX = 0.0;
	private double upY = 1.0;
	private double upZ = 0.0;
	private Orientation orientation = new Orientation(new Vector(0.0,0.0,0.0),
			  										  new Vector(1.0,0.0,0.0), 
			  										  new Vector(0.0,1.0,0.0),
													  new Vector(0.0,0.0,-1.0));
	private double theta = 0.2;
	private double expansionFactor = 100.0;
	
	private double COS(double arg){
		return Math.cos(Math.toRadians(arg));
	}
	
	private double SIN(double arg){
		return Math.sin(Math.toRadians(arg));
	}
	

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
			orientation.rotatePitch(theta);
			//pitch += rotationIndex;
			break;
		//pitch --
		case KeyEvent.VK_K:
			orientation.rotatePitch(theta*(-1.0));
			//pitch -= rotationIndex;
			break;
		//heading ++
		case KeyEvent.VK_L:
			orientation.rotateHeading(theta);
			//heading += rotationIndex;
			break;
		//heading --
		case KeyEvent.VK_J:
			orientation.rotateHeading(theta*(-1.0));
			//heading -= rotationIndex;
			break;
		//roll ++
		case KeyEvent.VK_O:
			orientation.rotateRoll(theta);
			//roll += rotationIndex;
			break;
		//roll --
		case KeyEvent.VK_U:
			orientation.rotateRoll(theta*(-1.0));
			//roll -= rotationIndex;
			break;
			
		/////////////////	
		//TRANSLATIONS://
		/////////////////
			
		//forwards
		case KeyEvent.VK_W:
			
			orientation.translateForward();
			//camPosX += transIndex*COS(heading);
			//targetY = camPosY + SIN()
			//camPosZ += transIndex*SIN(heading);
			
			break;
		//backwards
		case KeyEvent.VK_S:
			
			orientation.translateBackward();
			//camPosX -= transIndex*COS(heading);
			//targetY = camPosY + SIN()
			//camPosZ -= transIndex*SIN(heading);

			break;
		//left
		case KeyEvent.VK_A:
			
			orientation.translateLeftward();
			//camPosX -= transIndex*COS(heading+90);
			//targetY = camPosY + SIN()
			//camPosZ -= transIndex*SIN(heading+90);
			break;
		//right
		case KeyEvent.VK_D:
			
			orientation.translateRightward();
			//camPosX += transIndex*COS(heading+90);
			//targetY = camPosY + SIN()
			//camPosZ += transIndex*SIN(heading+90);
			break;
		//upwards
		case KeyEvent.VK_E:
			
			orientation.translateUpward();
			//camPosY -= transIndex;
			break;
		//downwards
		case KeyEvent.VK_Q:
			
			orientation.translateDownward();
			//camPosY += transIndex;
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
		//gl.glLoadIdentity();
		//gl.glViewport(0, 0, 1, 1);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();	
		glu.gluPerspective(50.0f, 1, 1.0, 1000.0);
		
		
		//targetX = camPosX + COS(heading);
		//targetY = camPosY + SIN()
		//targetZ = camPosZ + SIN(heading);
		
		//targetY = camPosY + SIN(pitch);
		//targetZ = camPosZ + COS(pitch);
		
		//an idea: (gilad)///////////////////////////////
		
		Vector camPos = orientation.getPosition();
		Vector target = orientation.getTargetLookAtVector();
		Vector upVect = orientation.getUpVector();
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(camPos.getX(), camPos.getY(), camPos.getZ(), 
					  target.getX(), target.getY(), target.getZ(), 
					  upVect.getX(), upVect.getY(), upVect.getZ());
		/////////////////////////////////////////////////
		/*
		glu.gluLookAt(camPosX, camPosY, camPosZ, targetX, targetY, targetZ, upX, upY, upZ);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		*/
		//gl.glTranslated(camPosX, camPosY, camPosZ);
	 
//		gl.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
//		gl.glRotatef(heading, 0.0f, 1.0f, 0.0f);
//		gl.glRotatef(roll, 0.0f, 0.0f, 1.0f);
	 
		gl.glBegin(GL.GL_QUADS);

		//1
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.5*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.5*expansionFactor, 1.0*expansionFactor);
		
		//2
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.5*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 0.5*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		//3
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 0.5*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 0.5*expansionFactor, -1.0*expansionFactor);
		
		//4
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 0.5*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.5*expansionFactor, -1.0*expansionFactor);
		
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
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
		
		
	}

}
