package fr.umlv.yourobot.physics.raycasts;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.util.ElementType;

public class AICallback implements RayCastCallback, GameDetectionCallback {
	private final RobotWorld world;
	private final Robot robot;

	public AICallback(RobotWorld world, Robot robot) {
		this.world = world;
		this.robot = robot;
	}
	@Override
	public void raycast(final Element elem) {
		world.getJBoxWorld().raycast(this, robot.getBody().getPosition(), elem.getBody().getPosition());
	}


	@Override
	public float reportFixture(final Fixture fixture,final  Vec2 point, Vec2 normal, float fraction) {

		final Vec2 pos = fixture.getBody().getPosition();
		final Element elem = (Element) fixture.getBody().getUserData();
		if(elem == null)
			return -1;
		if (elem.typeElem() != ElementType.PLAYER_ROBOT && elem.typeElem() != ElementType.LURE_ROBOT) {
			return -1;
		}

		final Vec2 force = pos.sub(point);		
		robot.move(new Vec2(force.x * 10000, force.y * 10000));
	
		return 0;
	}
}


