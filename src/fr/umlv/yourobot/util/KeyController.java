package fr.umlv.yourobot.util;

import java.util.ArrayList;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.physics.raycasts.PlayerCallback;

import java.awt.Graphics2D;
import java.io.IOException;
import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.welcome.LoadingGame;

import fr.umlv.zen.KeyboardEvent;

public class KeyController {
	private HumanRobot e;
	private RobotWorld world;
	private final String keyUp;
	private String keyDown;
	private String keyLeft;
	private String keyRight;
	private String keyFire;
	
	public enum RobotTextureMod{
		TEXTURE,
		GRAPHIC
	}
	RobotTextureMod texture;
	
	public KeyController(RobotWorld world, Robot e, String[] keys){
		this.e = (HumanRobot) e;
		this.keyUp = keys[0];
		this.keyLeft = keys[1];
		this.keyRight = keys[2];
		this.keyFire = keys[3];
		this.keyDown = keys[4];
		this.world = world;
	}

	public void control(KeyboardEvent event){
		if(keyUp.equals(event.getKey().name()))
			e.impulse();
		if(keyLeft.equals(event.getKey().name()))
			e.rotate(-10);
		if(keyRight.equals(event.getKey().name()))
			e.rotate(10);
		if(keyFire.equals(event.getKey().name())){
			if(e.getBonus() == null){
				
				PlayerCallback c = new PlayerCallback(world, e);
				@SuppressWarnings("unchecked")
				ArrayList<Bonus> bonus = (ArrayList<Bonus>) world.getBonusList().clone();
				for(Bonus b : bonus)
					world.getJBoxWorld().raycast(c, e.getBody().getPosition(), b.getBody().getPosition());
			}
			else{
				ArrayList<Element> list = e.runBonus(world);
				if(list != null && list.size()>0)
					world.drawElements(list);
				e.setBonus(null);
			}
		}
	}

	
	public void control2(KeyboardEvent event){
		if(keyLeft.equals(event.getKey().name()))
			e.rotate(-10);
		if(keyRight.equals(event.getKey().name()))
			e.rotate(10);
		
	}
	
	public void controlMenuPlayer(KeyboardEvent event, Graphics2D g, RobotWorld world) throws IOException, InterruptedException{
		RobotGameMod mode = RobotGameMod.ONEPLAYER;
		if(keyUp.equals(event.getKey().name())){
			LoadingGame.drawSelectMenuSP(g);
			mode = RobotGameMod.ONEPLAYER;
		}
		if(keyDown.equals(event.getKey().name())){
			mode = RobotGameMod.TWOPLAYER;
			LoadingGame.drawSelectMenuMP(g);
			
		}
		if(keyFire.equals(event.getKey().name())){
			world.setMode(mode);
			LoadingGame.menu2(g);
		}
	}
	
	public void controlMenuGraphic(KeyboardEvent event, Graphics2D g, RobotWorld world) throws IOException{
		RobotTextureMod mode = RobotTextureMod.TEXTURE;
		
		if(keyUp.equals(event.getKey().name())){
			LoadingGame.drawSelectMenuGT(g);
			mode = RobotTextureMod.TEXTURE;
		}
		if(keyDown.equals(event.getKey().name())){
			LoadingGame.drawSelectMenuGC(g);
			mode = RobotTextureMod.GRAPHIC;
			System.out.println("control down");
		}
		if (keyFire.equals(event.getKey().name())){
			
		}
	}
}

