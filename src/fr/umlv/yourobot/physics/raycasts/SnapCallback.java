package fr.umlv.yourobot.physics.raycasts;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.LineJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.util.ElementClass;
import fr.umlv.yourobot.util.ElementType;

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
		final Element detected = (Element) fixture.getBody().getUserData();
		System.out.println(detected);
		if(detected == null || detected.typeElem() == ElementType.PLAYER_ROBOT)
			return true;
		detected.getBody().setAwake(true);
		detected.getBody().setType(BodyType.DYNAMIC);
		final Vec2 force = robot.getBody().getPosition().sub(detected.getBody().getPosition());
		detected.move(new Vec2(force.x * 100000, force.y * 100000));
		
		if(detected.classElem() == ElementClass.WALL && detected.typeElem() != ElementType.BORDERWALL){
			new Thread(new Runnable() {
				
				@Override
				public void run() {

					wjd.initialize(robot.getBody(), detected.getBody(), robot.getPosition());
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					world.getJBoxWorld().createJoint(wjd);
					System.out.println(world.getJBoxWorld().getJointCount());
					//detected.getBody().setActive(false);
				}
			}).start();
		}
		return true;
	}

}
