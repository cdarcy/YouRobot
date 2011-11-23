package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.IOException;

import org.jbox2d.common.MathUtils;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.physics.raycasts.AICallback;
import fr.umlv.yourobot.util.ElementType;

public class ComputerRobot extends Robot {
	public ComputerRobot(RobotWorld world, float x, float y) {
		super(x, y);
		type = ElementType.COMPUTER_ROBOT;
	}
	public Element draw(Graphics2D g, DrawAPI api) throws IOException{
		super.draw(this, "robot_IA.png", g, api);
		return this;
	}
	public void run(RobotWorld world){
		AICallback c = new AICallback(world, this);
		float quarter_diagonal = (float) (Math.sqrt((RobotWorld.WIDTH*RobotWorld.WIDTH)+(RobotWorld.HEIGHT*RobotWorld.HEIGHT))/4);
		for (HumanRobot p : world.getPlayers()){
			if(MathUtils.distance(getPosition(), p.getPosition()) < quarter_diagonal)
				c.raycast(p);
		}
			
	}
}
