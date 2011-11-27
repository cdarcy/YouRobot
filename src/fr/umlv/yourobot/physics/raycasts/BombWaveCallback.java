package fr.umlv.yourobot.physics.raycasts;

import java.util.ArrayList;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.util.ElementType;

public class BombWaveCallback implements QueryCallback {
	private HumanRobot robot;
	private ArrayList<Element> raycasted;
	private ElementType maxEffectType;


	public BombWaveCallback(RobotWorld world, HumanRobot robot, ElementType typeEffectMax){
		this.robot = robot;
		this.raycasted = new ArrayList<>();
		this.maxEffectType = typeEffectMax;
	}

	@Override
	public boolean reportFixture(Fixture fixture) {
		Element p = (Element) fixture.getBody().getUserData();

		System.out.println(p);
		if(p == null || raycasted.contains(p))
			return false;
		p.getBody().setType(BodyType.DYNAMIC);	
		Vec2 pos = new Vec2(robot.getPosition());
		Vec2 force = pos.sub(p.getPosition()).negate();
		int factor = 30000;
		switch (p.typeElem() ) {
		case WOODWALL:
			if(maxEffectType == ElementType.WOODWALL)
				factor = 100000;
			break;
		case ICEWALL:
			if(maxEffectType == ElementType.ICEWALL)
				factor = 100000;
			break;
		case STONEWALL:
			if(maxEffectType == ElementType.STONEWALL)
				factor = 100000;		
			break;
		case BORDERWALL:
			return false;
		default:
			break;
		}
		float distance = MathUtils.distance(robot.getPosition(), p.getPosition());
		p.getBody().applyForce(new Vec2((force.x*distance)*factor,(force.y*distance)*factor), p.getBody().getWorldCenter());
		p.getBody().setAwake(true);
		if(!raycasted.contains(p))
			raycasted.add(p);
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
