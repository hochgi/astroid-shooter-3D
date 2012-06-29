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
import com.biu.cg.objects3d.Model3D;
import com.biu.cg.objects3d.Space;
import com.biu.cg.objects3d.Tetrahedron;
import com.biu.cg.objects3d.particles.sprites.Shot;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.biu.cg.objects3d.particles.sprites.SpriteEmitter;
import com.biu.cg.objects3d.ships.MotherShip;
import com.biu.cg.objects3d.ships.Ship1;
import com.biu.cg.objects3d.ships.Ship2;
import com.sun.opengl.util.texture.*;

/**
 * Game class:
 * this class contains the main logic of the "game"
 * @author gilad
 *
 */
public class Game extends MultiKeysAdapter implements GLEventListener {

    public static GLU glu = new GLU();
    //define the camera orientation in the position (0,1,0),and 
    //camera orthogonal base vectors as (1,0,0),(0,1,0),(0,0,1)
    //i.e. the unit vectors.
//	private Orientation orientation = new Orientation(new Vector(0,1,0),
//			  										  new Vector(1,0,0), 
//			  										  new Vector(0,1,0),
//													  new Vector(0,0,1));
    private Orientation orientation;
	//positive angle (in radians)
	private float pTheta = (float)Math.toRadians(1.5);
	//negative angle (in radians)
	private float nTheta = ((float)Math.PI * 2f) - pTheta;
	private float step = 4f;
	//expansionFactor defines the size of the room
	private float expansionFactor = 0f;
	//all the cubes i'll be using
	private Cube cube1,cube2,cube3,cube4;
	private Ship1 ship1;
	private Ship2 ship2;
	private MotherShip motherShip;
	private Space space;
	private Model3D earth;
	//textures...
	Texture ground = null;
	Texture stars = null;
	Texture wall = null;
	//a tetrahedron that will be hanging from the ceiling
	private Tetrahedron tetra;

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

		switch(e.getKeyCode()){
		case KeyEvent.VK_ESCAPE:
			Exercise4.exit();
			break;
		case KeyEvent.VK_SPACE:
			shoot();
			break;
		case KeyEvent.VK_F1:
			help();
			break;
		default:
				//do nothing
		}

		//update the minimap
		Exercise4.miniMap.repaint();
		Exercise4.canvas.requestFocus();
	}
	
	private void help() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e){
		super.keyReleased(e);
	}

	/**
	 * execute the action that were set in the pressedKeys array
	 */
	@Override
	public void executeKeysAction() {
		if(isKeyPressed(MultiKeysAdapter.LOOK_UP)) {
			orientation.rotatePitch(pTheta);
			space.getOrientation().rotatePitch(pTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_DOWN)) {
			orientation.rotatePitch(nTheta);
			space.getOrientation().rotatePitch(nTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_RIGHT)) {
			orientation.rotateHeading(pTheta);
			space.getOrientation().rotateHeading(pTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_LEFT)) {
			orientation.rotateHeading(nTheta);
			space.getOrientation().rotateHeading(nTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_ROLL_CW)) {
			orientation.rotateRoll(nTheta);
			space.getOrientation().rotateRoll(nTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_ROLL_CCW)) {
			orientation.rotateRoll(pTheta);
			space.getOrientation().rotateRoll(pTheta);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_FORWARD)) {
			orientation.translateForward(step);
			space.getOrientation().translateForward(step);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_BACKWARD)) {
			orientation.translateBackward(step);
			space.getOrientation().translateBackward(step);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_LEFT)) {
			orientation.translateLeftward(step);
			space.getOrientation().translateLeftward(step);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_RIGHT)) {
			orientation.translateRightward(step);
			space.getOrientation().translateRightward(step);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_UP)) {
			orientation.translateUpward(step);
			space.getOrientation().translateUpward(step);
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_DOWN)) {
			orientation.translateDownward(step);
			space.getOrientation().translateDownward(step);
		}
	}

	//TODO: cooloff. don't let high rate firing... 
	//		add a timer that sets cooledOff to true
	//		every once in a while (1-2 seconds?),
	//		and before firing, check cooledOff, if
	//		true, set to false and shoot! =)
	private void shoot() {
		SpriteEmitter.registerObject(new Shot(orientation, new Vector(orientation.getAxis('z')), 6f));
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
//		glu.gluPerspective(50.0f, 1, 1.0, 1000.0);
//		
//		Vector camPos = orientation.getPosition();
//		Vector target = orientation.getTargetLookAtVector();
//		Vector upVect = orientation.getUpVector();
//		
//		gl.glMatrixMode(GL.GL_MODELVIEW);
//		gl.glLoadIdentity();
//		glu.gluLookAt(camPos.getX(), camPos.getY(), camPos.getZ(), 
//					  target.getX(), target.getY(), target.getZ(), 
//					  upVect.getX(), upVect.getY(), upVect.getZ());
		
		ship1.lookAtCamera1(gLDrawable);
	
		
		//applying ground texture
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
		ground.bind();
		
		//starting to draw the room
		gl.glBegin(GL.GL_QUADS);
		
		//floor
		gl.glTexCoord2f(0.0f, 0.0f); 
		gl.glColor3f(0.5f, 0.25f, 0.25f);
		gl.glVertex3f(-1f*expansionFactor, 0f*expansionFactor, -1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f); 
		gl.glColor3f(0.25f, 0.5f, 0.25f);
		gl.glVertex3f(-1f*expansionFactor, 0f*expansionFactor, 1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f);
		gl.glColor3f(0.25f, 0.25f, 0.5f);
		gl.glVertex3f(1f*expansionFactor, 0f*expansionFactor, 1f*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f);
		gl.glColor3f(0.33f, 0.33f, 0.33f);
		gl.glVertex3f(1f*expansionFactor, 0f*expansionFactor, -1f*expansionFactor);
		
		gl.glEnd();

		//applying wall texture
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
		wall.bind();
		
		gl.glBegin(GL.GL_QUADS);
		
		//wall 1
		gl.glTexCoord2f(0.0f, 0.0f); 
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3f(-1f*expansionFactor,1f*expansionFactor, -1f*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f); 
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3f(-1f*expansionFactor, 0f*expansionFactor, -1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f); 
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3f(-1f*expansionFactor, 0f*expansionFactor, 1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f); 
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3f(-1f*expansionFactor, 1f*expansionFactor, 1f*expansionFactor);
		
		//wall 2
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3f(-1f*expansionFactor, 0f*expansionFactor, 1f*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f); 		
		gl.glColor3f(0.5f, 1.0f, 0.5f);
		gl.glVertex3f(-1f*expansionFactor, 1f*expansionFactor, 1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f); 
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3f(1f*expansionFactor, 1f*expansionFactor, 1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f); 
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3f(1f*expansionFactor, 0f*expansionFactor, 1f*expansionFactor);
		
		//wall 3
		gl.glTexCoord2f(0.0f, 0.0f); 
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3f(1f*expansionFactor, 1f*expansionFactor, 1f*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f); 
		gl.glColor3f(0.5f, 0.5f, 1.0f);
		gl.glVertex3f(1f*expansionFactor, 0f*expansionFactor, 1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f); 
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3f(1f*expansionFactor, 0f*expansionFactor, -1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f); 
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3f(1f*expansionFactor, 1f*expansionFactor, -1f*expansionFactor);
		
		//wall 4
		gl.glTexCoord2f(0.0f, 0.0f); 
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3f(1f*expansionFactor, 0f*expansionFactor, -1f*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f); 
		gl.glColor3f(0.67f, 0.67f, 0.67f);
		gl.glVertex3f(1f*expansionFactor, 1f*expansionFactor, -1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f); 
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3f(-1f*expansionFactor, 1f*expansionFactor, -1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f); 
		gl.glColor3f(1.0f, 0.5f, 0.5f);
		gl.glVertex3f(-1f*expansionFactor, 0f*expansionFactor, -1f*expansionFactor);
		
		gl.glEnd();
		
		//applying stars texture
        gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        stars.bind();
		
		gl.glBegin(GL.GL_QUADS);
		
		//ceiling
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glColor3f(0.5f, 0.25f, 0.25f);
		gl.glVertex3f(-1f*expansionFactor, 1f*expansionFactor, -1f*expansionFactor);
		
		gl.glTexCoord2f(0.0f, 10.0f); 
		gl.glColor3f(0.25f, 0.5f, 0.25f);
		gl.glVertex3f(-1f*expansionFactor, 1f*expansionFactor, 1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 10.0f); 
		gl.glColor3f(0.25f, 0.25f, 0.5f);
		gl.glVertex3f(1f*expansionFactor, 1f*expansionFactor, 1f*expansionFactor);
		
		gl.glTexCoord2f(10.0f, 0.0f);
		gl.glColor3f(0.33f, 0.33f, 0.33f);
		gl.glVertex3f(1f*expansionFactor, 1f*expansionFactor, -1f*expansionFactor);
		
		gl.glEnd();
		
		//drawing the elements (polyhedrons):
//		cube1.draw(gLDrawable);
//		cube2.draw(gLDrawable);
//		cube3.draw(gLDrawable);
//		cube4.draw(gLDrawable);
//		tetra.draw(gLDrawable);
		ship1.draw(gLDrawable);
		ship2.draw(gLDrawable);
		motherShip.draw(gLDrawable);
		motherShip.setScale(5);
		space.draw(gLDrawable);
		earth.draw(gLDrawable);
//		SpriteEmitter.updateSpriteEmitters();
//		Sprite.updateSprites();
		Sprite.renderSprites(gLDrawable);
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
		cube1 = Cube.createCube(new Vector(0f,17.5f,50f), new Vector(-1f,1f,1f).normalize(), pTheta, 10f, "wood.gif");
		cube2 = Cube.createCube(new Vector(0f,17.5f,-50f), new Vector(1f,1f,1f).normalize(), pTheta, 10f, "wood.gif");
		cube3 = Cube.createCube(new Vector(50f,17.5f,0f), new Vector(-1f,1f,-1f).normalize(), pTheta, 10f, "wood.gif");
		cube4 = Cube.createCube(new Vector(-50f,17.5f,0f), new Vector(1f,1f,-1f).normalize(), pTheta, 10f, "wood.gif");
		tetra = Tetrahedron.createTetrahedron(new Vector(0f,1f*expansionFactor,0f), new Vector(0f,1f,0f).normalize(), pTheta * 2f, 25f);
		ship1 = new Ship1(new Vector(0, 10 , 0), orientation);
		ship2 = new Ship2(new Vector(50, 0 , 80));
		motherShip = new MotherShip(new Vector(0, -38 , 40));
		space = new Space("models/space/space.wng" , "models/space/space.jpg");
		space.setScale(4000);
		earth = new Model3D(new Vector(3, 0 , 0),"models/earth/earth.wng" , "models/earth/earth.jpg");
		earth.setScale(1000);
		orientation = ship1.getOrientation();
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
		orientation.reset(0f,1f,0f,
				 		  1f,0f,0f, 
				 		  0f,1f,0f,
				 		  0f,0f,1f);
	}


	public void testExplosionEffect() {
		new Explosion(new Vector(0f,0.2f,0f),orientation);
	}
}
