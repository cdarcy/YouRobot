package fr.umlv.yourobot.physics.raycasts;

import java.util.ArrayList;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.util.ElementClass;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.MapGenerator;

public class BombWaveCallback implements QueryCallback {
	private RobotWorld world;
	private HumanRobot robot;
	private ArrayList<Element> raycasted;
	private Vec2 distance;
	private float quarter_diagonal = (float) (Math.sqrt((RobotWorld.WIDTH*RobotWorld.WIDTH)+(RobotWorld.HEIGHT*RobotWorld.HEIGHT))/4);


	public BombWaveCallback(RobotWorld world, HumanRobot robot){
		this.world = world;
		this.robot = robot;
		this.raycasted = new ArrayList<>();
	}

	@Override
	public boolean reportFixture(Fixture fixture) {
		Element p = (Element) fixture.getBody().getUserData();
		if(p == null)
			return false;

		if(p.typeElem() == ElementType.BORDERWALL){
			if(!raycasted.contains(p))
				raycasted.add(p);
			return false;
		}

		if(p.classElem() != ElementClass.WALL)
			return false;


		System.out.println("bomb wave");
		p.getBody().setType(BodyType.DYNAMIC);	
		Vec2 pos = new Vec2(robot.getPosition());
		Vec2 force = pos.sub(p.getBody().getPosition()).negate();
		p.getBody().applyForce(new Vec2(force.x*10000,force.y*10000), p.getPosition());
		p.getBody().setAwake(true);
		//world.removeBonus(p.getPosition());
		//robot.clearBonus();
		System.out.println(p);

		return true;

	}

	public ArrayList<Element> getRaycastedBorder(){
		return raycasted;
	}
	/*
	 * return -1: ignore this fixture and continue 
	 * return 0: terminate the ray cast return fraction: clip the ray to this point 
	 * return 1: don't clip the ray and continue 
	 * 
	 */

	/*@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {

		Vec2 pos = fixture.getBody().getPosition();
		Element p = world.getElementFromPosition(pos);
		if(p == null)
			return -1;
		ElementData data = (ElementData) p.getBody().getUserData();

		if(data.type() == ElementType.BORDERWALL){
			world.drawElement(p);
			System.out.println(p);
		}
		float x = (robot.getX()-p.getX())*(robot.getX()-p.getX());
		float y = (robot.getY()-p.getY())*(robot.getY()-p.getY());
		float distance = (float) Math.sqrt(x+y);
		float quarter_diagonal = (float) (Math.sqrt((RobotWorld.WIDTH*RobotWorld.WIDTH)+(RobotWorld.HEIGHT*RobotWorld.HEIGHT))/4);

		if(((ElementData) p.getBody().getUserData()).type() == ElementType.PLAYER_ROBOT)
			return -1;
		if((!raycasted.contains(p)) && distance <= quarter_diagonal){
			raycasted.add(p);
			Vec2 force = pos.sub(point);
			p.getBody().applyForce(new Vec2(force.x*1000,force.y*1000), point);
			return fraction;
		}
		return -1;
	}*/


}
