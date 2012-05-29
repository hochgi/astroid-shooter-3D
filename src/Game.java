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
	private Orientation orientation = new Orientation(new Vector(0.0,0.5,0.0),
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
	
	public Game() {}

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
		tetra = Tetrahedron.createTetrahedron(new Vector(0.0,1.0*expansionFactor,0.0), new Vector(0.0,1.0,0.0).normalize(), pTheta * 2.0, 10.0);
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

}
