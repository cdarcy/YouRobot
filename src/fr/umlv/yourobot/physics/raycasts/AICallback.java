package fr.umlv.yourobot.physics.raycasts;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.util.ElementData;

public class AICallback implements RayCastCallback{
	private RobotWorld world;
	private Robot robot;
	public AICallback(RobotWorld world, Robot robot){
		this.world = world;
		this.robot = robot;
	}
	
	/*
	 * return -1: ignore this fixture and continue 
	 * return 0: terminate the ray cast return fraction: clip the ray to this point 
	 * return 1: don't clip the ray and continue 
	 * 
	 */
	
	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
		Vec2 pos = fixture.getBody().getPosition();
		Element player= world.getPlayer(pos);
		if(player != null)
		{
			float quarter_diagonal = (float) (Math.sqrt((world.WIDTH*world.WIDTH)+(world.HEIGHT*world.HEIGHT))/4);
			float x = (robot.getX()-player.getX())*(robot.getX()-player.getX());
			float y = (robot.getY()-player.getY())*(robot.getY()-player.getY());
			float distance = (float) Math.sqrt(x+y);
			
			if(distance<=quarter_diagonal){
				System.out.println("Detected");
				Vec2 force = pos.sub(point);
				robot.move(new Vec2(force.x*100000,force.y*100000));
			}
		}

		return 0;
	}

	public Vec2 getOrigin() {
		return robot.getBody().getPosition();
	}


}
