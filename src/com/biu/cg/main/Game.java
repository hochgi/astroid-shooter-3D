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
import com.biu.cg.object3d.planets.Earth;
import com.biu.cg.objects3d.Cube;
import com.biu.cg.objects3d.Object3D;
import com.biu.cg.objects3d.Tetrahedron;
import com.biu.cg.objects3d.asteroids.Asteroid;
import com.biu.cg.objects3d.asteroids.Asteroids;
import com.biu.cg.objects3d.particles.sprites.Photon;
import com.biu.cg.objects3d.particles.sprites.Shot;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.biu.cg.objects3d.particles.sprites.SpriteEmitter;
import com.biu.cg.objects3d.physics.Collidables;
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
	//private float nTheta = ((float)Math.PI * 2f) - pTheta;
	//private float step = 4f;
	//expansionFactor defines the size of the room
	private float expansionFactor = 0f;
	//all the cubes i'll be using
	private Cube cube1,cube2,cube3,cube4;
	private Ship1 ship1;
	private Ship2 ship2;
	private MotherShip motherShip;
	
	private Earth earth;
	//private Asteroid astroid;
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
			shootPhoton();
			break;
		case KeyEvent.VK_R:
			shootRocket();
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
		if( !isKeyPressed(MultiKeysAdapter.LOOK_UP)   &&
			!isKeyPressed(MultiKeysAdapter.LOOK_DOWN) &&
			!isKeyPressed(MultiKeysAdapter.LOOK_LEFT) &&
			!isKeyPressed(MultiKeysAdapter.LOOK_RIGHT)){
			ship1.enableUpdate();
		}
	}

	/**
	 * execute the action that were set in the pressedKeys array
	 */
	@Override
	public synchronized void executeKeysAction() {
		if(isKeyPressed(MultiKeysAdapter.LOOK_UP)) {
			ship1.disableUpdate();
			ship1.pitchUp();
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_DOWN)) {
			ship1.disableUpdate();
			ship1.pitchDown();
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_RIGHT)) {
			ship1.disableUpdate();
			ship1.turnRight();
			
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_LEFT)) {
			ship1.disableUpdate();
			ship1.turnLeft();
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_ROLL_CW)) {
			ship1.rollRight();
			
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_ROLL_CCW)) {
			ship1.rollLeft();
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_FORWARD)) {
			ship1.moveForward();
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_BACKWARD)) {
			ship1.moveBackward();
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_LEFT)) {
			ship1.moveLeft();
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_RIGHT)) {
			ship1.moveRight();
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_UP)) {
			ship1.moveUp();
		}
		if(isKeyPressed(MultiKeysAdapter.MOVE_DOWN)) {
			ship1.moveDown();
		}
		if(isKeyPressed(MultiKeysAdapter.FIRE)){
			shootPhoton();
		}
	}

	private void shootPhoton() { 
 		if(Photon.canShoot()){ 
 			Photon.createNewPhoton(ship1.getWingPosition(),orientation,orientation.getAxis('z'),12f); 
 		} 
 	} 
	
	private void shootRocket() {
		SpriteEmitter.registerObject(new Shot(ship1.getWingPosition(), orientation, new Vector(orientation.getAxis('z')), 6f));
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
	public synchronized void display(GLAutoDrawable gLDrawable) {
		final GL gl = gLDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		ship1.lookAtCamera1(gLDrawable);
		
		//the following piece of code is useless!!!
		//it's only here because we have to render something that has no blending.
		//if this code is to be omitted, for some strange reason particle rendering
		//will cause the whole frame to render weirdly...
		gl.glBegin(GL.GL_TRIANGLES);
		gl.glColor3f(1f, 1f, 1f);
		gl.glVertex3f(0,0,0);
		gl.glColor3f(1f, 1f, 1f);
		gl.glVertex3f(0,0,0);
		gl.glColor3f(1f, 1f, 1f);
		gl.glVertex3f(0,0,0);
		gl.glEnd();

		ship1.draw(gLDrawable);
		ship2.draw(gLDrawable);
		motherShip.draw(gLDrawable);
		//astroid.draw(gLDrawable);
		earth.draw(gLDrawable);
		
		
		for(Asteroid a : Asteroids.getAsteroids())
			a.draw(gLDrawable);
		
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
		Photon.init();
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
		ship1 = new Ship1(new Vector(0, 10 , 0));
		Object3D.registerObject(ship1);
		ship1.setActive(true);
		
		Collidables.registerObject(ship1);
		
		ship2 = new Ship2(new Vector(50, 0 , 80));
		Collidables.registerObject(ship2);
		motherShip = new MotherShip(new Vector(0, -180 , 40));
		motherShip.setScale(5);
		//Collidables.registerObject(motherShip);
		
		earth = new Earth();
		Collidables.registerObject(earth);
		earth.setScale(1000);
		
		Asteroids.setCamera(ship1.getOrientation());
		Asteroids.setEarth(earth);
		
		//astroid = new Asteroid(earth , ship1.getOrientation());
		//Object3D.registerObject(astroid);
		
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
		new Explosion(new Vector(7.4f,100f,-90f),orientation, false);
	}
}
