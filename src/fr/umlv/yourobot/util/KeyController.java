package fr.umlv.yourobot.util;

import java.awt.Graphics2D;
import java.io.IOException;
import java.security.spec.KeySpec;

import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.welcome.LoadingGame;
import fr.umlv.zen.KeyboardEvent;

public class KeyController {
	private HumanRobot e;
	private final String keyUp;
	private String keyDown;
	private String keyLeft;
	private String keyRight;
	private String keyFire;
	private int gameMode = 1;

	public KeyController(Robot e, String[] keys){
		this.e = (HumanRobot) e;
		this.keyUp = keys[0];
		this.keyDown = keys[1];
		this.keyLeft = keys[2];
		this.keyRight = keys[3];
		this.keyFire = keys[4];
	}

	public void control(KeyboardEvent event){
		if(keyUp.equals(event.getKey().name()))
			e.move(new Vec2(0, -100000));
		if(keyDown.equals(event.getKey().name()))
			e.move(new Vec2(0, 100000));
		if(keyLeft.equals(event.getKey().name()))
			e.move(new Vec2(-100000, 0));
		if(keyRight.equals(event.getKey().name()))
			e.move(new Vec2(100000, 0));
		if(keyFire.equals(event.getKey().name())){
			e.runBonus();
		}
	}
	
	public void control2(KeyboardEvent event){
		if(keyLeft.equals(event.getKey().name()))
			e.rotateLeft();
		if(keyRight.equals(event.getKey().name()))
			e.rotateRight();
		
	}
	
	public void controlMenu(KeyboardEvent event, Graphics2D g, RobotWorld world) throws IOException{
		if(keyUp.equals(event.getKey().name())){
			LoadingGame.drawSelectMenuSP(g);
			gameMode = 1;
		}
		if(keyDown.equals(event.getKey().name())){
			LoadingGame.drawSelectMenuMP(g);
			gameMode = 2;
		}
		if(keyFire.equals(event.getKey().name()) && gameMode == 1)
			world.setMode(RobotGameMod.ONEPLAYER);
		if(keyFire.equals(event.getKey().name()) && gameMode == 2)
			world.setMode(RobotGameMod.TWOPLAYER);
	}
}

