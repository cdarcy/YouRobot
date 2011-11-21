package fr.umlv.yourobot.physics.raycasts;

import java.util.ArrayList;

import javax.sound.sampled.Line;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.LineJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
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
	private ArrayList<Element> list = new ArrayList<>();
	private ArrayList<Joint> joints = new ArrayList<>();
	DistanceJointDef wjd;

	public SnapCallback(RobotWorld world, HumanRobot robot){
		this.world = world;
		this.robot = robot;
	}
	@Override
	public boolean reportFixture(Fixture fixture) {
		final Element detected = (Element) fixture.getBody().getUserData();
		if(detected == null)
			return true;
		System.out.println("detected : "+detected);

		if(detected.classElem() == ElementClass.WALL && detected.typeElem() != ElementType.BORDERWALL && !list.contains(wjd)){
			System.out.println("added : "+detected);
			detected.getBody().setAwake(true);
			detected.getBody().setType(BodyType.DYNAMIC);
			detected.getBody().getFixtureList().destroy();
			Vec2 force = robot.getBody().getPosition().sub(detected.getBody().getPosition());
			wjd = new DistanceJointDef();
			wjd.collideConnected=true;
			wjd.length = 0;
			
			detected.move(new Vec2(force.x * 100000, force.y * 100000));
			list.add(detected);  
			wjd.initialize(robot.getBody(), detected.getBody(), robot.getPosition(), robot.getPosition());
			joints.add(world.getJBoxWorld().createJoint(wjd));
		}
	return true;

}
	
	public void clearJoints(){
		for(Element d : list)
			d.getBody().setType(BodyType.STATIC);
		for(Joint j : joints)
			world.getJBoxWorld().destroyJoint(j);
	}


}
