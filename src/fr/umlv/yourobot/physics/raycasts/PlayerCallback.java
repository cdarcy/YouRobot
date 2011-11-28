package fr.umlv.yourobot.physics.raycasts;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.HumanRobot;

public class PlayerCallback implements GameDetectionCallback {

	private final RobotGame world;
	private final HumanRobot robot;

	public PlayerCallback(RobotGame world, HumanRobot robot) {
		this.world = world;
		this.robot = robot;
	}
	/**
	 * Override the raycast methode
	 * @param elem Element
	 */
	@Override
	public void raycast(Element elem) {
		world.removeElement(elem);
		robot.setBonus((Bonus) elem);
	}
}
