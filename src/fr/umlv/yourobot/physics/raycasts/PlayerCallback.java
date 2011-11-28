package fr.umlv.yourobot.physics.raycasts;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

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

	@Override
	public void raycast(Element elem) {
		world.removeElement(elem);
		robot.setBonus((Bonus) elem);
	}
}
