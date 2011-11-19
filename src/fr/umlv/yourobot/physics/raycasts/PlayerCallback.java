package fr.umlv.yourobot.physics.raycasts;

import java.lang.annotation.ElementType;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.util.ElementData.ElementClass;

public class PlayerCallback implements GameDetectionCallback, RayCastCallback{

	private RobotWorld world;
	private HumanRobot robot;

	public PlayerCallback(RobotWorld world,HumanRobot robot){
		this.world = world;
		this.robot = robot;
	}


	@Override
	public void raycast(Element elem) {
		if(MathUtils.distance(robot.getPosition(), elem.getPosition()) < 40){
			world.removeBonus(elem.getPosition());
			robot.setBonus((Bonus)elem);
		}

	}


	@Override
	public float reportFixture(Fixture arg0, Vec2 arg1, Vec2 arg2, float arg3) {
		Element elem = (Element) arg0.getBody().getUserData();
		if(MathUtils.distance(robot.getPosition(), elem.getPosition()) < 40){
			world.removeBonus(elem.getPosition());
			robot.setBonus((Bonus)elem);
		}
		return 0;
	}
}
