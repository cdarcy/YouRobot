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

	public Vec2 getOrigin() {
		return robot.getBody().getPosition();
	}

	@Override
	public void raycast(Element elem) {
		synchronized (world.getMonitor()) {
		world.getJBoxWorld().raycast(this, robot.getBody().getPosition(), elem.getBody().getPosition());
		}
	}

	/*
	 * return -1: ignore this fixture and continue return 0: terminate the ray
	 * cast return fraction: clip the ray to this point return 1: don't clip the
	 * ray and continue
	 */

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
		final Vec2 pos = fixture.getBody().getPosition();

		final Element elem = (Element) fixture.getBody().getUserData();

		if (elem.typeElem() != ElementType.PLAYER_ROBOT) {
			return 0;
		}
		
		final Vec2 force = pos.sub(point);
		
		robot.move(new Vec2(force.x * MathUtils.randomFloat(6000, 10000), force.y * MathUtils.randomFloat(6000, 10000)));
		
		return 0;
	}

}
