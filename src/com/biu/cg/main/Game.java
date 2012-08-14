package com.biu.cg.main;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import com.biu.cg.gui.MultiKeysAdapter;
import com.biu.cg.math3d.Orientation;
import com.biu.cg.math3d.Vector;
import com.biu.cg.object3d.planets.Earth;
import com.biu.cg.objects3d.Object3D;
import com.biu.cg.objects3d.asteroids.Asteroid;
import com.biu.cg.objects3d.asteroids.Asteroids;
import com.biu.cg.objects3d.particles.Particle;
import com.biu.cg.objects3d.particles.sprites.Photon;
import com.biu.cg.objects3d.particles.sprites.Shot;
import com.biu.cg.objects3d.particles.sprites.Sprite;
import com.biu.cg.objects3d.physics.Collidables;
import com.biu.cg.objects3d.ships.MotherShip;
import com.biu.cg.objects3d.ships.Ship1;
import com.biu.cg.objects3d.ships.Ship2;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

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

    private Orientation orientation;

	private Ship1 ship1;
	private Ship2 ship2;
	private MotherShip motherShip;
	private Earth earth;
	public static Texture particleTex;
	
	
	public final static float defaultAmbient[] = {1f,1f,1f,1f};
	public final static float defaultDiffuse0[] = {1f,1f,1f,1.0f};
	public final static float defaultDiffuse1[] = {1f,1f,1f,1.0f};
	public final static float defaultColor[] = {1f,1f,0.0f,0.5f};
	
	//private Asteroid astroid;

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
		case KeyEvent.VK_0:
			speedUp();
			break;
		case KeyEvent.VK_9:
			speedDown();
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
		Exercise4.showInfo();
		
	}


	@Override
	public void keyReleased(KeyEvent e){
		super.keyReleased(e);
		if( !isKeyPressed(MultiKeysAdapter.LOOK_UP)   &&
			!isKeyPressed(MultiKeysAdapter.LOOK_DOWN) &&
			!isKeyPressed(MultiKeysAdapter.LOOK_LEFT) &&
			!isKeyPressed(MultiKeysAdapter.LOOK_RIGHT)){
			ship1.enableRotateUpdate();
		}
		if(!isKeyPressed(MultiKeysAdapter.TURBO_FORWARD)){
			ship1.enableMoveUpdate();
		}
	}

	/**
	 * execute the action that were set in the pressedKeys array
	 */
	@Override
	public synchronized void executeKeysAction() {
		ship1.moveForward();
		if(isKeyPressed(MultiKeysAdapter.LOOK_UP)) {
			ship1.disableRotateUpdate();
			ship1.pitchUp();
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_DOWN)) {
			ship1.disableRotateUpdate();
			ship1.pitchDown();
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_RIGHT)) {
			ship1.disableRotateUpdate();
			ship1.turnRight();
			
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_LEFT)) {
			ship1.disableRotateUpdate();
			ship1.turnLeft();
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_ROLL_CW)) {
			ship1.rollRight();
			
		}
		if(isKeyPressed(MultiKeysAdapter.LOOK_ROLL_CCW)) {
			ship1.rollLeft();
		}
		if(isKeyPressed(MultiKeysAdapter.TURBO_FORWARD)) {
			ship1.disableMoveUpdate();
			ship1.turboForward();
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
		if(isKeyPressed(MultiKeysAdapter.FIRE) && Photon.canShoot()){
			shootPhoton();
		}
		else if(!Photon.canShoot()){
			Photon.incrementCoolOff();
		}
	}

	private void shootPhoton() { 
 		Sprite.registerObject(new Photon(ship1.getWingPosition(),orientation,new Vector(orientation.getAxis('z')),36f));
 	} 
	
	private void shootRocket() {
		if(ship1.getRocketCounter()>0){
			Particle.registerObject(new Shot(ship1.getWingPosition(), orientation, new Vector(orientation.getAxis('z')), 15f));
			Exercise4.rocketPanel.removeRocket();
			Exercise4.rocketPanel.repaint();
			ship1.removeRocket();
			
		}
	}
	
	private void speedUp(){
		ship1.speedUp();
	}
	
	private void speedDown(){
		ship1.speedDown();
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
		float	material[] = {0.4f,0.4f,0.4f,1.0f};
    	float	position0[] = {1000f,0f,0f,1.0f};		// red light on the right side (light 0)
    	float	position1[] = {1000f,0f,0f,1.0f};	// blue light on the left side (light 1)
		
		
		final GL gl = gLDrawable.getGL();
		
		// Light
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position0, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, position1, 0);
        
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
        
        
        //float m_color[] = {0.0f, 0.85f, 0.0f, 1.0f};
        float s_color[] = {1f, 1f, 1f, 1.0f};
        float shininess[] = {120f};

        //gl.glNormal3f(0,0,-1);
//        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE, material, 0);
//        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, s_color,0); 
//        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, shininess,0);
        
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, defaultAmbient,0); 
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, defaultDiffuse1,0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, defaultColor,0); 
        gl.glMateriali(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, 127);
		
		
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
		
		ship1.drawSpace(gLDrawable);
		
		//ship2.draw(gLDrawable);
		motherShip.draw(gLDrawable);
		earth.draw(gLDrawable);
		ship1.draw(gLDrawable);
		Asteroids.drawAsteroids(gLDrawable);
		
		Sprite.renderSprites(gLDrawable);
		
		if(Exercise4.earthHealth<=0)
			Exercise4.exitWithMessage();
		
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
		
		try {
			particleTex = TextureIO.newTexture(new File( "textures/round_particle_16X16.png" ),false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ship1 = new Ship1(new Vector(0, 40-1000 , 0));
		Object3D.registerObject(ship1);
		ship1.setActive(true);
		
		Collidables.registerObject(ship1);
		
		ship2 = new Ship2(new Vector(-50, 0 , 80));
		//Collidables.registerObject(ship2);
		motherShip = new MotherShip(new Vector(0, -180-1000 , 40) , 5);
		Collidables.registerObject(motherShip);
		
		earth = new Earth(getCamera());
		
		Collidables.registerObject(earth);
		Object3D.registerObject(earth);
		//earth.setScale(1000);
		
		Asteroids.setCamera(ship1.getOrientation());
		Asteroids.setEarth(earth);
		
		//astroid = new Asteroid(earth , ship1.getOrientation());
		//Object3D.registerObject(astroid);
		
		orientation = ship1.getOrientation();
		GL gl = gLDrawable.getGL();
		
		
        gl.glShadeModel(GL.GL_SMOOTH);
        // Set the background / clear color.
        gl.glClearColor(0.05f, 0.05f, 0.05f, 1f);
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
		
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glEnable(GL.GL_TEXTURE_2D);
		
		//TODO:
		//UNCOMMENT THE FOLLOWING LINE OF CODE
		//FOR REALISTIC COLORS. NOTE THAT ICC
		//BUTTON WON'T CHANGE THE COLORS ANYMORE.

		//gl.glEnable(GL.GL_LIGHTING);

		
		
		// Light
    	
		gl.glShadeModel(GL.GL_SMOOTH); 
		float SHINE_ALL_DIRECTIONS = 1;
		float[] lightPos = {-30, 0, 0, SHINE_ALL_DIRECTIONS};
		
		gl.glDisable(GL.GL_LIGHT0);
		
//		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos, 0);
//        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, ambient, 0);
//        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, diffuse0, 0);
//        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, color, 0);
//        gl.glEnable(GL.GL_LIGHT1);
        
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_AMBIENT, defaultAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_DIFFUSE, defaultDiffuse1, 0);
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_SPECULAR, defaultColor, 0);
        gl.glEnable(GL.GL_LIGHT2);
        
        
        
        //gl.glMaterialfv(gl.GL_FRONT, gl.GL_SHININESS, 127); 
        //gl.glEnable(GL.GL_COLOR_MATERIAL);
        //float specReflection[] = { 0.1f, 0.1f, 0.1f, 1.0f };
       
        
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_NORMALIZE);

		start();
		help();
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

	public void reverseCubesRotation() {
		//TODO: change method
	}

	public void inverseCubesColors() {
		//TODO: change method
	}

	public void changeTetrahedronSpeed() {
		//TODO: change method
	}

	/**
	 * hmmm.... i think this one here is for
	 * retrieving the camera position...
	 * @return
	 */
	public Vector getCamPos() {
		if(orientation == null){
			return Vector.defaultInstance();
		}
		return orientation.getPosition();
	}

	/**
	 * well, here's the reset button implementation.
	 * in case we got into a gimble-lock because of
	 * precision error for the vectors,
	 * this button might save your arse...
	 */
	public void resetOrientation() {
		ship1.reset(0f,10f,0f);
	}


	public void testExplosionEffect() {
		new Explosion(ship1.getPosition().add(ship1.getOrientation().getAxis('z'), 200),orientation, 0.25f, null);
	}


	public LinkedList<Vector> getAsteroidsInSquareRange(int range) {
		LinkedList<Vector> rv = new LinkedList<Vector>();
		for (Asteroid ast : Asteroids.getAsteroids()) {
			if(ast.getPosition().sqrDistanceTo(ship1.getPosition()) < range){
				rv.add(ast.getPosition().sub(ship1.getPosition(), 1));
			}
		}
		return rv;
	}


	public void convertAbsoluteVectorToShipRelative(LinkedList<Vector> asteroids) {
		for (Vector v : asteroids) {
			ship1.getOrientation().convertVector(v);
		}
	}


	public Orientation getCamera() {
		return ship1.getOrientation();
	}
}
