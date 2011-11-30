package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.common.MathUtils;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.physics.raycasts.PlayerCallback;
import fr.umlv.yourobot.util.ElementType.ElementClass;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.KeyControllers.KeyController;
import fr.umlv.zen.KeyboardEvent;

/**
 * @code {@link ComputerRobot.java}
 * @see {@link Robot.java}
 * This class define all behavior of an computer robot
 * @author BAUDRAND Sebastien <sbaudran@univ-mlv.fr>
 * @author Camille <cdarcy@univ-mlv.fr>
 */
public class HumanRobot extends Robot {
	private volatile double life;
	private Bonus currentBonus = null;
	protected KeyController controller;
	
	/**
	 * Constructor
	 * @param world Robot Game
	 * @param name String
	 * @param x float
	 * @param y float
	 */
	public HumanRobot(RobotGame world, String name, float x, float y) {
		super(x, y);
		type = ElementType.PLAYER_ROBOT;
		this.life = 100;
	}
	
	/**
	 * Set the new bonus
	 * @param b Bonus
	 */
	public void setBonus(Bonus b) {
		if(currentBonus == b)
			return;
		currentBonus = b;
	}
	
	/**
	 * Run the bonus
	 * @param world RobotGame
	 * @return ArrayList<Element>
	 */
	public ArrayList<Element> runBonus(RobotGame world) {
		if(currentBonus == null)
			return null;
		return currentBonus.run(world, this);
	}

	/**
	 * Call the control methode to control the robot
	 * @param g Graphics2D
	 * @param event KeyboardEvent
	 */
	public void control(Graphics2D g, KeyboardEvent event){
		controller.control(event);
	}

	/**
	 * Get the life point
	 * @return int
	 */
	public int getLife() {
		return (int) Math.round(life);
	}
	
	/**
	 * Set the life point 
	 * @param d double
	 */
	public void setLife(double d){
		if (d<=0.5)
			this.life = this.life * d;
	}

	/**
	 * Get the current Bonus
	 * @return Bonus
	 */
	public Bonus getBonus() {
		return currentBonus;
	}
	
	/**
	 * Delete all the bonus after their are use
	 */
	public void clearBonus() {
		currentBonus = null;
	}

	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		super.draw(this, "robot_game.png", g, api);
		return this;
	}

	/**
	 * Get the new Direction
	 * @return float
	 */
	public float getDirection() {
		return direction;
	}

	/**
	 * Detect and take a bonus
	 * @param world Robot Game
	 */
	public void detectBonus(RobotGame world) {
		if(getBonus() == null){
			//if the human robot has any bonus, he take a bonus
			PlayerCallback c = new PlayerCallback(world, this);
			@SuppressWarnings("unchecked")
			ArrayList<Bonus> bonus = (ArrayList<Bonus>) world.getListByClass(ElementClass.BONUS).clone();
			for(Bonus b : bonus){
				if (MathUtils.distance(bodyElem.getPosition(), b.getPosition()) < 40){
					c.raycast(b);
					return;
				}
			}
		}
		else{
			//else, he run the bonus
			ArrayList<Element> list = runBonus(world);
			if(list != null && list.size()>0)
				world.drawElements(list);
			setBonus(null);
		}
	}

	/**
	 * Modify the key controller use in this game
	 * @param keyController KeyController
	 */
	public void setController(KeyController keyController) {
		this.controller = keyController;
	}
}
