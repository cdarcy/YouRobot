package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.common.MathUtils;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.bonus.IceBomb;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.KeyControllers.KeyController;
import fr.umlv.zen.KeyboardEvent;

public class HumanRobot extends Robot {
	private volatile double life;
	//TODO
	private Bonus currentBonus = new IceBomb(0,0);
	private Bonus runningBonus = null;
	protected KeyController controller;
	
	
	public HumanRobot(RobotGame world, String name, float x, float y) {
		super(x, y);
		type = ElementType.PLAYER_ROBOT;
		this.life = 100;
	}
	
	public void setBonus(Bonus b) {
		if(currentBonus == b)
			return;
		currentBonus = b;
	}
	
	public void runBonus(RobotGame world) {
		if(currentBonus == null)
			return;
		currentBonus.run(world, this);
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


	public void detectBonus(RobotGame world) {
		if(currentBonus == null){
			ArrayList<Element> bonus = world.getBonuses();
			for(Element b : bonus){
				if (MathUtils.distance(bodyElem.getPosition(), b.getPosition()) < 40){
					world.removeElement(b);
					currentBonus = (Bonus) b;
					return;
				}
			}
		}
		else{
			runningBonus = currentBonus;
			runningBonus.run(world, this);
			currentBonus = null;
		}
	}


	public void setController(KeyController keyController) {
		this.controller = keyController;
	}


	@Override
	public void run(RobotGame world) {
		if(runningBonus != null){
			if(!runningBonus.update()){
				runningBonus = null;				
			}
		}
	}
}
