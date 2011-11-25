package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.graphics.DrawAPI;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.KeyController;
import fr.umlv.zen.KeyboardEvent;

public class HumanRobot extends Robot {
	private volatile double life;
	private Bonus currentBonus = null;
	protected KeyController controller;
	
	public HumanRobot(RobotWorld world, String name, String[] k, float x, float y) {
		super(x, y);
		controller = new KeyController(world, this, k);
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
		return currentBonus.run(world, this);
	}


	public void control(Graphics2D g, KeyboardEvent event){
		controller.control(event);
	}

	
	public void controlMenu(Graphics2D g, KeyboardEvent event, RobotWorld world) throws IOException{
		controller.controlMenuPlayer(event, g, world);			
	}
	
	
	public double getLife() {
		return life;
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
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		super.draw(this, "robot_game.png", g, api);
		return this;
	}


	public float getDirection() {
		return direction;
	}
}
