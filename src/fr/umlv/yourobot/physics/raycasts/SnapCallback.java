package fr.umlv.yourobot.physics.raycasts;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.joints.WeldJointDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;

public class SnapCallback implements QueryCallback{
	// Joint 
	private RobotWorld world;
	private HumanRobot robot;
	WeldJointDef wjd = new WeldJointDef();

	public SnapCallback(RobotWorld world, HumanRobot robot){
		this.world = world;
		this.robot = robot;
	}
	@Override
	public boolean reportFixture(Fixture fixture) {
		Vec2 pos = fixture.getBody().getPosition();
		Element detected = world.getElementFromPosition(pos);
		System.out.println(detected);
		System.out.println("snap fixture detected");
		if(detected == null)
			return false;
		wjd.initialize(robot.getBody(), detected.getBody(), robot.getBody().getWorldCenter());	 
		world.getJBoxWorld().createJoint(wjd);
		return true;
	}
	
}
