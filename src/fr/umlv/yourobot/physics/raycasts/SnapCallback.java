package fr.umlv.yourobot.physics.raycasts;

import java.util.ArrayList;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.util.ElementClass;
import fr.umlv.yourobot.util.ElementType;

public class SnapCallback implements RayCastCallback{
	// Joint 
	private RobotWorld world;
	private HumanRobot robot;
	private ArrayList<Element> list = new ArrayList<>();
	private ArrayList<Joint> joints = new ArrayList<>();
	DistanceJointDef wjd;

	public SnapCallback(RobotWorld world, HumanRobot robot){
		this.world = world;
		this.robot = robot;
	}
	public boolean reportFixture(Fixture fixture) {
		final Element detected = (Element) fixture.getBody().getUserData();
		if(detected == null)
			return false;

		if(detected.classElem() == ElementClass.WALL && detected.typeElem() != ElementType.BORDERWALL && !list.contains(detected)){
			final Vec2 force = detected.getPosition().sub(robot.getPosition());	
			detected.move(new Vec2(force.x * MathUtils.randomFloat(6000, 10000), force.y * MathUtils.randomFloat(6000, 10000)));
			
			/*System.out.println("SNAP");
			DistanceJointDef djd = new DistanceJointDef();
			BodyType oldType = detected.getBody().getType();
			detected.getBody().setType(BodyType.DYNAMIC);
			djd.initialize(robot.getBody(), detected.getBody(), new Vec2(100,100), new Vec2(100,100));
			Joint j = world.getJBoxWorld().createJoint(djd);
			world.getJBoxWorld().destroyJoint(j);
			detected.getBody().setType(oldType);*/
			return false;
		}
		System.out.println(joints);
		return false;

	}

	public void clearJoints(){
System.out.println(list);
		for(Joint j : joints)
			world.getJBoxWorld().destroyJoint(j);
		for(Element d : list)
			d.getBody().setType(BodyType.STATIC);
	}
	
	@Override
	public float reportFixture(Fixture fixture, Vec2 position, Vec2 arg2, float arg3) {
		final Element detected = (Element) fixture.getBody().getUserData();
		if(detected == null)
			return 0;

		if(detected.classElem() == ElementClass.WALL && detected.typeElem() != ElementType.BORDERWALL && !list.contains(detected)){
			System.out.println("added : "+detected);
			detected.getBody().setType(BodyType.DYNAMIC);
			DistanceJointDef djd = new DistanceJointDef();
			djd.initialize(robot.getBody(), detected.getBody(), new Vec2(100,100), new Vec2(100,100));
			Joint j = world.getJBoxWorld().createJoint(djd);
			final Vec2 force = robot.getPosition().sub(position);
			detected.move(new Vec2(force.x * 100000, force.y * 100000));
			list.add(detected);
			joints.add(j);
				return 0;
		}
		return -1;
	}


}
