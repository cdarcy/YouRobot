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
	private AICallback c;
	private boolean detect;
	public ComputerRobot(float x, float y) {
		super(x, y);
		type = ElementType.COMPUTER_ROBOT;
		detect = false;
	}

	public Element draw(Graphics2D g, DrawAPI api) throws IOException{
		super.draw(this, "robot_IA.png", g, api);
		return this;
	}

	public void run(final RobotWorld world){
		c = new AICallback(world, this);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				float quarter_diagonal = (float) (Math.sqrt((RobotWorld.WIDTH*RobotWorld.WIDTH)+(RobotWorld.HEIGHT*RobotWorld.HEIGHT))/4);

					for (HumanRobot p : world.getPlayers()){
						if(MathUtils.distance(getPosition(), p.getPosition()) < quarter_diagonal){
							c.raycast(p);
							
						}
					}
				

			}
		});
		t.start();
	}
	

	public void setDetect(boolean b) {
		detect = b;
	}
}
