package fr.umlv.yourobot.util;

import java.util.ArrayList;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.physics.raycasts.PlayerCallback;
import fr.umlv.zen.KeyboardEvent;

public class KeyController {
	private HumanRobot e;
	private RobotWorld world;
	private final String keyUp;
	private String keyDown;
	private String keyLeft;
	private String keyRight;
	private String keyFire;
	private String keySpace;

	public KeyController(RobotWorld world, Robot e, String[] keys){
		this.e = (HumanRobot) e;
		this.keyUp = keys[0];
		this.keyLeft = keys[1];
		this.keyRight = keys[2];
		this.keyFire = keys[3];
		this.world = world;
	}

	public void control(KeyboardEvent event){
		if(keyUp.equals(event.getKey().name()))
			e.impulse(world);
		if(keyLeft.equals(event.getKey().name()))
			e.rotate(-10);
		if(keyRight.equals(event.getKey().name()))
			e.rotate(10);
		if(keyFire.equals(event.getKey().name())){
			if(e.getBonus() == null){
				PlayerCallback c = new PlayerCallback(world, e);
				@SuppressWarnings("unchecked")
				ArrayList<Bonus> bonus = (ArrayList<Bonus>) world.getBonusList().clone();
				for(Bonus b : bonus)
					world.getJBoxWorld().raycast(c, e.getBody().getPosition(), b.getBody().getPosition());
			}
			else{
				world.drawElements(e.runBonus(world));
			}
		}
	}

}

