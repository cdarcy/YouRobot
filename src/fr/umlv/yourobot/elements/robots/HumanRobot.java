package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.YouRobotCode;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.physics.raycasts.PlayerCallback;
import fr.umlv.yourobot.util.ElementType.ElementClass;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.KeyController;
import fr.umlv.yourobot.util.KeyControllers;
import fr.umlv.zen.KeyboardEvent;

public class HumanRobot extends Robot {
	private volatile double life;
	private Bonus currentBonus = null;
	protected KeyController controller;
	
	public HumanRobot(RobotWorld world, String name, float x, float y) {
		super(x, y);
		type = ElementType.PLAYER_ROBOT;
		this.life = 100;
	}
	

	public void setBonus(Bonus b) {
		if(currentBonus == b)
			return;
		currentBonus = b;
	}
	
	public ArrayList<Element> runBonus(RobotWorld world) {
		if(currentBonus == null)
			return null;
		System.out.println(world);
		return currentBonus.run(world, this);
	}


	public void control(Graphics2D g, KeyboardEvent event){
		controller.control(event);
	}

	
	public int getLife() {
		return (int) Math.round(life);
	}
	
	public void setLife(double d){
		if (d<=0.5)
			this.life = this.life * d;
	}

	public Bonus getBonus() {
		return currentBonus;
	}
	

	public void clearBonus() {
		currentBonus = null;
	}

	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		super.draw(this, "robot_game.png", g, api);
		return this;
	}


	public float getDirection() {
		return direction;
	}


	public void detectBonus(RobotWorld world) {
		if(getBonus() == null){
			PlayerCallback c = new PlayerCallback(world, this);
			@SuppressWarnings("unchecked")
			ArrayList<Bonus> bonus = (ArrayList<Bonus>) world.getListByClass(ElementClass.BONUS).clone();
			for(Bonus b : bonus)
				world.getJBoxWorld().raycast(c, bodyElem.getPosition(), b.getBody().getPosition());
		}
		else{
			ArrayList<Element> list = runBonus(world);
			if(list != null && list.size()>0)
				world.drawElements(list);
			setBonus(null);
		}
	}


	public void setController(KeyController gameController) {
		this.controller = gameController;
	}
}
