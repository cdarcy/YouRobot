package fr.umlv.yourobot.util;

import java.awt.Graphics2D;

import fr.umlv.zen.KeyboardEvent;

public abstract class KeyController {
	private String keyUp;
	private String keyDown;
	private String keyLeft;
	private String keyRight;
	private String keyFire;
	protected boolean finished;
	
	public KeyController(String keys[]){
		keyUp = keys[0];
		keyDown = keys[1];
		keyLeft = keys[2];
		keyRight = keys[3];
		keyFire = keys[4];
		finished = false;
	}
	
	public void control(KeyboardEvent event) {
		if(keyUp.equals(event.getKey().name()))
			pressKeyUp();
		else if(keyDown.equals(event.getKey().name()))
			pressKeyDown();
		else if(keyLeft.equals(event.getKey().name()))
			pressKeyLeft();
		else if(keyRight.equals(event.getKey().name()))
			pressKeyRight();
		else if(keyFire.equals(event.getKey().name())){
			pressKeyFire();
		}
	}
	abstract public void pressKeyUp();
	abstract public void pressKeyDown();
	abstract public void pressKeyLeft();
	abstract public void pressKeyRight();
	abstract public void pressKeyFire();
	abstract public void drawMenu(Graphics2D g);
	public boolean isFinished() {
		return finished;
	}

	public enum GameMenu{
		REPLAY,
		EXIT;
	}
}

