package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.IOException;

import org.jbox2d.common.MathUtils;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.physics.raycasts.AICallback;
import fr.umlv.yourobot.physics.raycasts.LureCallback;

public class LureRobot extends Robot {

	public LureRobot(float x, float y) {
		super(x, y);
	}

	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		return super.draw(this, "robot_game.png", g, api);
	}
	
	public void run(RobotWorld world){
		LureCallback c = new LureCallback(world, this);
		for (Robot p : world.getRobots()){
				c.raycast(p);
		}
	}
}
