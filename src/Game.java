import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.texture.*;


public class Game implements GLEventListener, KeyListener {

    static GLU glu = new GLU();
	private Orientation orientation = new Orientation(new Vector(0.0,1.0,0.0),
			  										  new Vector(1.0,0.0,0.0), 
			  										  new Vector(0.0,1.0,0.0),
													  new Vector(0.0,0.0,1.0));
	private double pTheta = Math.toRadians(0.72);
	private double nTheta = (Math.PI * 2) - pTheta;
	private double expansionFactor = 150.0;
	private Cube cube1,cube2,cube3,cube4;
	Texture ground = null;
	Texture stars = null;
	Texture wall = null;
	private Tetrahedron tetra;
	private boolean[] pressedKeys = new boolean[12];
	
	public Game() {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_ESCAPE) {
			Exercise3.exit();
		}
		
		registerKeysAction(code);
		executeKeysAction();
		Exercise3.miniMap.repaint();
	}

	private void registerKeysAction(int code) {
		switch(code){
		//////////////	
		//ROTATIONS://
		//////////////
			
		//pitch ++
		case KeyEvent.VK_I:
			pressedKeys[0] = true;
			break;
		//pitch --
		case KeyEvent.VK_K:
			pressedKeys[1] = true;
			break;
		//heading ++
		case KeyEvent.VK_L:
			pressedKeys[2] = true;
			break;
		//heading --
		case KeyEvent.VK_J:
			pressedKeys[3] = true;
			break;
		//roll ++
		case KeyEvent.VK_O:
			pressedKeys[4] = true;
			break;
		//roll --
		case KeyEvent.VK_U:
			pressedKeys[5] = true;
			break;
		
		/////////////////	
		//TRANSLATIONS://
		/////////////////
			
		//forwards
		case KeyEvent.VK_W:
			pressedKeys[6] = true;
			break;
		//backwards
		case KeyEvent.VK_S:
			pressedKeys[7] = true;
			break;
		//left
		case KeyEvent.VK_A:
			pressedKeys[8] = true;
			break;
		//right
		case KeyEvent.VK_D:
			pressedKeys[9] = true;
			break;
		//upwards
		case KeyEvent.VK_E:
			pressedKeys[10] = true;
			break;
		//downwards
		case KeyEvent.VK_Q:
			pressedKeys[11] = true;
			break;
		default:
			//nothing
		}
	}

	private void executeKeysAction() {
		if(pressedKeys[0]) {
			orientation.rotatePitch(pTheta);
		}
		if(pressedKeys[1]) {
			orientation.rotatePitch(nTheta);
		}
		if(pressedKeys[2]) {
			orientation.rotateHeading(pTheta);
		}
		if(pressedKeys[3]) {
			orientation.rotateHeading(nTheta);
		}
		if(pressedKeys[4]) {
			orientation.rotateRoll(nTheta);
		}
		if(pressedKeys[5]) {
			orientation.rotateRoll(pTheta);
		}
		if(pressedKeys[6]) {
			orientation.translateForward();
		}
		if(pressedKeys[7]) {
			orientation.translateBackward();
		}
		if(pressedKeys[8]) {
			orientation.translateLeftward();
		}
		if(pressedKeys[9]) {
			orientation.translateRightward();
		}
		if(pressedKeys[10]) {
			orientation.translateUpward();
		}
		if(pressedKeys[11]) {
			orientation.translateDownward();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch(code){
		//////////////	
		//ROTATIONS://
		//////////////
			
		//pitch ++
		case KeyEvent.VK_I:
			pressedKeys[0] = false;
			break;
		//pitch --
		case KeyEvent.VK_K:
			pressedKeys[1] = false;
			break;
		//heading ++
		case KeyEvent.VK_L:
			pressedKeys[2] = false;
			break;
		//heading --
		case KeyEvent.VK_J:
			pressedKeys[3] = false;
			break;
		//roll ++
		case KeyEvent.VK_O:
			pressedKeys[4] = false;
			break;
		//roll --
		case KeyEvent.VK_U:
			pressedKeys[5] = false;
			break;
		
		/////////////////	
		//TRANSLATIONS://
		/////////////////
			
		//forwards
		case KeyEvent.VK_W:
			pressedKeys[6] = false;
			break;
		//backwards
		case KeyEvent.VK_S:
			pressedKeys[7] = false;
			break;
		//left
		case KeyEvent.VK_A:
			pressedKeys[8] = false;
			break;
		//right
		case KeyEvent.VK_D:
			pressedKeys[9] = false;
			break;
		//upwards
		case KeyEvent.VK_E:
			pressedKeys[10] = false;
			break;
		//downwards
		case KeyEvent.VK_Q:
			pressedKeys[11] = false;
			break;
		default:
			//nothing
		}
	}

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
		
		float color[] = {1f, 0.f, 0.0f, 0.0f};
		float ambient[] = {1.0f, 1.0f, 1.0f, 0.5f};
		float position[] = {0.0f, 10.0f, 0.0f};
		
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient,1); 
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE,color , 1); 
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, color , 1); 
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 1);
		gl.glEnable(GL.GL_LIGHT0);
		
		Vector camPos = orientation.getPosition();
		Vector target = orientation.getTargetLookAtVector();
		Vector upVect = orientation.getUpVector();
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(camPos.getX(), camPos.getY(), camPos.getZ(), 
					  target.getX(), target.getY(), target.getZ(), 
					  upVect.getX(), upVect.getY(), upVect.getZ());
		
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glEnable(GL.GL_BLEND); 
		ground.bind();
		
		gl.glBegin(GL.GL_QUADS);
		
		//0
		gl.glTexCoord2f(0.0f, 0.0f); 
		gl.glColor3f(0.5f, 0.25f, 0.25f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		
		gl.glTexCoord2f(10.0f, 0.0f); 
		gl.glColor3f(0.25f, 0.5f, 0.25f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f);
		gl.glColor3f(0.25f, 0.25f, 0.5f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f);
		gl.glColor3f(0.33f, 0.33f, 0.33f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glEnd();

        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glEnable(GL.GL_BLEND); 
		wall.bind();
		
		gl.glBegin(GL.GL_QUADS);
		
		//1
		gl.glTexCoord2f(0.0f, 0.0f); 
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor,1.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f); 
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f); 
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f); 
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		//2
		
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f); 		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f); 
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f); 
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		//3
		gl.glTexCoord2f(0.0f, 0.0f); 
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f); 
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f); 
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f); 
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, -1.0*expansionFactor);
		
		//4
		gl.glTexCoord2f(0.0f, 0.0f); 
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f); 
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f); 
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 1.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f); 
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3d(-1.0*expansionFactor, 0.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glEnd();
		
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glEnable(GL.GL_BLEND); 
        stars.bind();
		
		gl.glBegin(GL.GL_QUADS);
		
		//5
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glColor3f(0.5f, 0.25f, 0.25f);
		gl.glVertex3d(-1.0*expansionFactor, 1.0*expansionFactor, -1.0*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f); 
		gl.glColor3f(0.25f, 0.5f, 0.25f);
		gl.glVertex3d(-1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f); 
		gl.glColor3f(0.25f, 0.25f, 0.5f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, 1.0*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f);
		gl.glColor3f(0.33f, 0.33f, 0.33f);
		gl.glVertex3d(1.0*expansionFactor, 1.0*expansionFactor, -1.0*expansionFactor);
		
		cube1.draw(gLDrawable);
		cube2.draw(gLDrawable);
		cube3.draw(gLDrawable);
		cube4.draw(gLDrawable);
		tetra.draw(gLDrawable);
		
		gl.glEnd();
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {}

	@Override
	public void init(GLAutoDrawable gLDrawable) {
		
		try {
			ground = TextureIO.newTexture(new File( "textures/ground.jpg" ),true);
			stars = TextureIO.newTexture(new File( "textures/stars.jpg" ),true);
			wall = TextureIO.newTexture(new File( "textures/wall.jpg" ),true);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cube1 = Cube.createCube(new Vector(0.0,17.5,50.0), new Vector(-1.0,1.0,1.0).normalize(), pTheta, 10.0, "wood.gif");
		cube2 = Cube.createCube(new Vector(0.0,17.5,-50.0), new Vector(1.0,1.0,1.0).normalize(), pTheta, 10.0, "wood.gif");
		cube3 = Cube.createCube(new Vector(50.0,17.5,0.0), new Vector(-1.0,1.0,-1.0).normalize(), pTheta, 10.0, "wood.gif");
		cube4 = Cube.createCube(new Vector(-50.0,17.5,0.0), new Vector(1.0,1.0,-1.0).normalize(), pTheta, 10.0, "wood.gif");
		tetra = Tetrahedron.createTetrahedron(new Vector(0.0,1.0*expansionFactor,0.0), new Vector(0.0,1.0,0.0).normalize(), pTheta * 2.0, 25.0);
		GL gl = gLDrawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glEnable(GL.GL_TEXTURE_2D);
		
		//gl.glEnable(gl.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);
		
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

	public void reverseCubesRotation() {
		cube1.getOrientation().reverseRotation();
		cube2.getOrientation().reverseRotation();
		cube3.getOrientation().reverseRotation();
		cube4.getOrientation().reverseRotation();
	}

	public void inverseCubesColors() {
		cube1.reverseColors();
		cube2.reverseColors();
		cube3.reverseColors();
		cube4.reverseColors();
	}

	public void changeTetrahedronSpeed() {
		tetra.changeSpeed();
	}

	public Vector getCamPos() {
		return orientation.getPosition();
	}

	public void resetOrientation() {
		orientation.reset(0.0,1.0,0.0,
				 		  1.0,0.0,0.0, 
				 		  0.0,1.0,0.0,
				 		  0.0,0.0,1.0);
	}
}
