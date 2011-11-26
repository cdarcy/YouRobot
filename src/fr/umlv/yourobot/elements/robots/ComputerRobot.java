package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.graphics.DrawAPI;
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

	public void run(final RobotWorld world) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				float quarter_diagonal = (float) (Math.sqrt((RobotWorld.WIDTH*RobotWorld.WIDTH)+(RobotWorld.HEIGHT*RobotWorld.HEIGHT))/4);
				ArrayList<Element> list = new ArrayList<>();
				list.addAll(world.getListByType(ElementType.PLAYER_ROBOT));
				list.addAll(world.getListByType(ElementType.LURE_ROBOT));
				
				while(true) {
					for (final Element p : list){
						float distance = MathUtils.distance(bodyElem.getPosition(), p.getPosition());
						Random rand = new Random();
						
						if(distance < quarter_diagonal && p.typeElem() == ElementType.PLAYER_ROBOT && world.getListByType(ElementType.LURE_ROBOT).size() == 0)
						{
							distance = MathUtils.distance(bodyElem.getPosition(), p.getPosition());
							final Vec2 force = p.getPosition().sub(bodyElem.getPosition());		
							move(new Vec2(force.x * 10000, force.y * 10000));

							try {
								Thread.sleep(rand.nextInt(2000));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
						else  {
							int rotation = rand.nextInt(45);

							if(rand.nextBoolean()) {
								rotate(-rotation);
							}
							else {
								rotate(rotation);
							}
							final Vec2 imp = new Vec2();

							imp.x = (float) Math.cos(Math.toRadians(direction)) * SPEED;
							imp.y = (float) Math.sin(Math.toRadians(direction)) * SPEED;

							bodyElem.applyLinearImpulse(imp, getPosition());
						}
						try {
							Thread.sleep(rand.nextInt(5000));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}
		}).start();
	}

	public void setDetect(boolean b) {
		detect = b;
	}
}
