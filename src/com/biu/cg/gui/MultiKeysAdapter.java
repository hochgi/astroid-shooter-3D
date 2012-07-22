package com.biu.cg.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;


public abstract class MultiKeysAdapter implements KeyListener {

	//////////////	
	//ROTATIONS://
	//////////////
	
	public static final int LOOK_UP = 0;		// confront to: KeyEvent.VK_I
	public static final int LOOK_DOWN = 1;		// confront to: KeyEvent.VK_K
	public static final int LOOK_RIGHT = 2;		// confront to: KeyEvent.VK_L
	public static final int LOOK_LEFT = 3;		// confront to: KeyEvent.VK_J
	public static final int LOOK_ROLL_CW = 4;	// confront to: KeyEvent.VK_O
	public static final int LOOK_ROLL_CCW = 5;	// confront to: KeyEvent.VK_U

	/////////////////	
	//TRANSLATIONS://
	/////////////////
	
	public static final int TURBO_FORWARD = 6;	// confront to: KeyEvent.VK_W (WAS MOVE_FORWARD)
	public static final int MOVE_BACKWARD = 7;	// confront to: KeyEvent.VK_S
	public static final int MOVE_LEFT = 8;		// confront to: KeyEvent.VK_A
	public static final int MOVE_RIGHT = 9;		// confront to: KeyEvent.VK_D
	public static final int MOVE_UP = 10;		// confront to: KeyEvent.VK_E
	public static final int MOVE_DOWN = 11;		// confront to: KeyEvent.VK_Q

	////////////	
	//SPECIAL://
	////////////
	
	public static final int FIRE = 12;			// confront to: KeyEvent.VK_SPACE
	public static final int HELP = 13;			// confront to: KeyEvent.VK_F1
	
	/////////////////
	//END OF STATIC//
	/////////////////
	
	private boolean[] pressedKeys = new boolean[14];
	private Timer timer;
	private TimerTask task;
	private int ms;
	
	public MultiKeysAdapter(int ms) {
		this.ms = ms;
		task = new TimerTask() {
			
			@Override
			public void run() {
				executeKeysAction();
			}
		};
		timer = new Timer("Timer-MultiKeysAdapter");
		for (int i = 0; i < pressedKeys.length; i++) {
			pressedKeys[i] = false;
		}
	}
	
	public void start(){
		timer.scheduleAtFixedRate(task, 0, ms);
	}

	/**
	 * whenever a key was pressed,
	 * register it on the suitable location in the array,
	 * would cause executeKeysAction to consider it pressed.
	 * @param code
	 */
	public void registerKeysAction(int code, boolean value) {
		switch(code){
		//pitch ++
		case KeyEvent.VK_I:
			pressedKeys[LOOK_UP] = value;
			break;
		//pitch --
		case KeyEvent.VK_K:
			pressedKeys[LOOK_DOWN] = value;
			break;
		//heading ++
		case KeyEvent.VK_L:
			pressedKeys[LOOK_RIGHT] = value;
			break;
		//heading --
		case KeyEvent.VK_J:
			pressedKeys[LOOK_LEFT] = value;
			break;
		//roll ++
		case KeyEvent.VK_O:
			pressedKeys[LOOK_ROLL_CW] = value;
			break;
		//roll --
		case KeyEvent.VK_U:
			pressedKeys[LOOK_ROLL_CCW] = value;
			break;
		//forwards
		case KeyEvent.VK_W:
			pressedKeys[TURBO_FORWARD] = value;
			break;
		//backwards
		case KeyEvent.VK_S:
			pressedKeys[MOVE_BACKWARD] = value;
			break;
		//left
		case KeyEvent.VK_A:
			pressedKeys[MOVE_LEFT] = value;
			break;
		//right
		case KeyEvent.VK_D:
			pressedKeys[MOVE_RIGHT] = value;
			break;
		//upwards
		case KeyEvent.VK_E:
			pressedKeys[MOVE_UP] = value;
			break;
		//downwards
		case KeyEvent.VK_Q:
			pressedKeys[MOVE_DOWN] = value;
			break;
		//fire
		case KeyEvent.VK_SPACE:
			pressedKeys[FIRE] = value;
			break;
			/*
		case KeyEvent.VK_F1:
			pressedKeys[HELP] = value;
			break;
			*/
		default:
			//nothing
		}
	}

	/**
	 * implementation of key listener interface
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		//add key code to the actions to perform
		registerKeysAction(e.getKeyCode(), true);
	}
	
	/**
	 * unregister keys that was released from the actions to perform.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		registerKeysAction(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {/*NOT USED!*/}

	/**
	 * execute the action that were set in the pressedKeys array
	 */
	abstract public void executeKeysAction();

	public boolean isKeyPressed(int keyCode) {
		return pressedKeys[keyCode];
	}
}
