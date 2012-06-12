package com.biu.cg.main;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import com.biu.cg.gui.MultiKeysAdapter;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.objects3d.Cube;
import com.biu.cg.objects3d.Tetrahedron;
import com.biu.cg.objects3d.particles.sprites.Shot;
import com.biu.cg.objects3d.particles.sprites.SpriteEmitter;
import com.sun.opengl.util.texture.*;

/**
 * Game class:
 * this class contains the main logic of the "game"
 * @author gilad
 *
 */
public class Game extends MultiKeysAdapter implements GLEventListener {

    static GLU glu = new GLU();
    //define the camera orientation in the position (0,1,0),and 
    //camera orthogonal base vectors as (1,0,0),(0,1,0),(0,0,1)
    //i.e. the unit vectors.
	private Orientation orientation = new Orientation(new Vector(0.0,1.0,0.0),
			  										  new Vector(1.0,0.0,0.0), 
			  										  new Vector(0.0,1.0,0.0),
													  new Vector(0.0,0.0,1.0));
	//positive angle (in radians)
	private double pTheta = Math.toRadians(0.72);
	//negative angle (in radians)
	private double nTheta = (Math.PI * 2) - pTheta;
	//expansionFactor defines the size of the room
	private double expansionFactor = 150.0;
	//all the cubes i'll be using
	private Cube cube1,cube2,cube3,cube4;
	//textures...
	Texture ground = null;
	Texture stars = null;
	Texture wall = null;
	//a tetrahedron that will be hanging from the ceiling
	private Tetrahedron tetra;
	private int fire;

	/**
	 * constructor.
	 * make MultiKeysAdapter timer to execute every 40 ms
	 */
	public Game() {
		super(40);
	}


	/**
	 * implementation of key listener interface
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Exercise4.exit();
		}

		//update the minimap
		Exercise4.miniMap.repaint();
		Exercise4.canvas.requestFocus();
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		super.keyReleased(e);
		fire = 0;
	}

	/**
	 * execute the action that were set in the pressedKeys array
	 */
	@Override
	public void executeKeysAction() {
		if(isKeyPressed(MultiKeysAdapter.LOOK_UP)) {
			orientation.rotatePitch(pTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_DOWN)) {
			orientation.rotatePitch(nTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_RIGHT)) {
			orientation.rotateHeading(pTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_LEFT)) {
			orientation.rotateHeading(nTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_ROLL_CW)) {
			orientation.rotateRoll(nTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_ROLL_CCW)) {
			orientation.rotateRoll(pTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_FORWARD)) {
			orientation.translateForward(1.0);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_BACKWARD)) {
			orientation.translateBackward(1.0);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_LEFT)) {
			orientation.translateLeftward(1.0);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_RIGHT)) {
			orientation.translateRightward(1.0);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_UP)) {
			orientation.translateUpward(1.0);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_DOWN)) {
			orientation.translateDownward(1.0);
		}
		if(isKeyPressed(MultiKeysAdapter.FIRE)) {
			shoot();
		}
	}

	
	private void shoot() {
		fire = (fire + 1) % 5;
		if(fire == 1) {
			new Shot(orientation, new Vector(orientation.getAxis('z')), 2);
		}
	}


	/**
	 * display method inherited from GLEventListener.
	 * here we make all the magic happen. i.e. drawing
	 * of the various elements on the scene.
	 */
	//TODO: Consider making another class for the "room"
	//		instead of putting all the boilerplate code 
	//		in here.class game is big enough as it is...
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
	
		
		//applying ground texture
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glEnable(GL.GL_BLEND); 
		ground.bind();
		
		//starting to draw the room
		gl.glBegin(GL.GL_QUADS);
		
		//floor
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

		//applying wall texture
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glEnable(GL.GL_BLEND); 
		wall.bind();
		
		gl.glBegin(GL.GL_QUADS);
		
		//wall 1
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
		
		//wall 2
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
		
		//wall 3
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
		
		//wall 4
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
		
		//applying stars texture
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glEnable(GL.GL_BLEND); 
        stars.bind();
		
		gl.glBegin(GL.GL_QUADS);
		
		//ceiling
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
		
		gl.glEnd();
		
		//drawing the elements (polyhedrons):
		cube1.draw(gLDrawable);
		cube2.draw(gLDrawable);
		cube3.draw(gLDrawable);
		cube4.draw(gLDrawable);
		tetra.draw(gLDrawable);
		
		SpriteEmitter.updateSprites();
		SpriteEmitter.renderSprites(gLDrawable);
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {/*NOT USED*/}

	/**
	 * init method inherited from GLEventListener
	 * this is where the initialization takes place.
	 */
	@Override
	public void init(GLAutoDrawable gLDrawable) {
		
		Explosion.init();
		Shot.init();
		//loading textures
		try {
			ground = TextureIO.newTexture(new File( "textures/ground.jpg" ),true);
			stars = TextureIO.newTexture(new File( "textures/stars.jpg" ),true);
			wall = TextureIO.newTexture(new File( "textures/wall.jpg" ),true);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//polyhedrons construction from factory methods
		cube1 = Cube.createCube(new Vector(0.0,17.5,50.0), new Vector(-1.0,1.0,1.0).normalize(), pTheta, 10.0, "wood.gif");
		cube2 = Cube.createCube(new Vector(0.0,17.5,-50.0), new Vector(1.0,1.0,1.0).normalize(), pTheta, 10.0, "wood.gif");
		cube3 = Cube.createCube(new Vector(50.0,17.5,0.0), new Vector(-1.0,1.0,-1.0).normalize(), pTheta, 10.0, "wood.gif");
		cube4 = Cube.createCube(new Vector(-50.0,17.5,0.0), new Vector(1.0,1.0,-1.0).normalize(), pTheta, 10.0, "wood.gif");
		tetra = Tetrahedron.createTetrahedron(new Vector(0.0,1.0*expansionFactor,0.0), new Vector(0.0,1.0,0.0).normalize(), pTheta * 2.0, 25.0);
		GL gl = gLDrawable.getGL();
		
		
        gl.glShadeModel(GL.GL_SMOOTH);
        // Set the background / clear color.
        gl.glClearColor(0f, 0f, 0f, 1f);
        // Clear the depth
        gl.glClearDepth(1.0);
        // Disable depth testing.
        //gl.glDisable(GL.GL_DEPTH_TEST);        
        // Get nice perspective calculations. 
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        // Nice point smoothing.
        gl.glHint(GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST);
        // Enable texture mapping.
        gl.glEnable(GL.GL_TEXTURE_2D);

		gl.glEnable(GL.GL_DEPTH_TEST);
		
		//gl.glDepthFunc(GL.GL_LEQUAL);
		//gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		//gl.glEnable(GL.GL_TEXTURE_2D);
		
		//TODO:
		//UNCOMMENT THE FOLLOWING LINE OF CODE
		//FOR REALISTIC COLORS. NOTE THAT ICC
		//BUTTON WON'T CHANGE THE COLORS ANYMORE.

		//gl.glEnable(GL.GL_LIGHTING);

		//two (soft) lights (red and blue) setup.
		//each in an opposite corner of the room.
		float color0[] = {1f, 0f, 0f, 0.5f};
		float ambient0[] = {1.0f, 1.0f, 1.0f, 0.5f};
		float position0[] = {(float) (0.95*expansionFactor), (float) (0.95*expansionFactor), (float) (0.95*expansionFactor)};

		float color1[] = {0f, 0f, 1f, 0.5f};
		float ambient1[] = {1.0f, 1.0f, 1.0f, 0.5f};
		float position1[] = {(float) (0.95*expansionFactor), (float) (0.95*expansionFactor), (float) (0.95*expansionFactor)};
		
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient0,1); 
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE,color0 , 1); 
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, color0 , 1); 
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position0, 1);
		
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, ambient1,1); 
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE,color1 , 1); 
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, color1 , 1); 
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, position1, 1);
		
		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_LIGHT1);
		
		start();
	}

	/**
	 * inherited from GLEventListener
	 */
	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		GL gl = gLDrawable.getGL();
		if(height <= 0) {
		    height = 1;
		}
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
	}

	/**
	 * as the name suggested,
	 * this methods reverses the
	 * direction of rotation of the cubes
	 */
	public void reverseCubesRotation() {
		cube1.reverseRotation();
		cube2.reverseRotation();
		cube3.reverseRotation();
		cube4.reverseRotation();
	}

	/**
	 * guess what?
	 * this method inverts the colors of the cubes...
	 */
	public void inverseCubesColors() {
		cube1.reverseColors();
		cube2.reverseColors();
		cube3.reverseColors();
		cube4.reverseColors();
	}

	/**
	 * and here? i wonder what this might do...
	 * oh.. maybe it changes the speed of rotation
	 * for the weird tetrahedron (which is just
	 * a fancy word for triangular pyramid).
	 */
	public void changeTetrahedronSpeed() {
		tetra.changeSpeed();
	}

	/**
	 * hmmm.... i think this one here is for
	 * retrieving the camera position...
	 * @return
	 */
	public Vector getCamPos() {
		return orientation.getPosition();
	}

	/**
	 * well, here's the reset button implementation.
	 * in case we got into a gimble-lock because of
	 * precision error for the vectors,
	 * this button might save your arse...
	 */
	public void resetOrientation() {
		orientation.reset(0.0,1.0,0.0,
				 		  1.0,0.0,0.0, 
				 		  0.0,1.0,0.0,
				 		  0.0,0.0,1.0);
	}


	public void testExplosionEffect() {
		new Explosion(new Vector(0.0,0.2,0.0),orientation);
	}
}