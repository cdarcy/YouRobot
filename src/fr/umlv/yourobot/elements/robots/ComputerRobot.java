package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;

/**
 * @code {@link ComputerRobot.java}
 * @see {@link Robot.java}
 * This class define all behavior of an computer robot
 * @author BAUDRAND Sebastien <sbaudran@univ-mlv.fr>
 * @author Camille <cdarcy@univ-mlv.fr>
 */
public class ComputerRobot extends Robot {
	
	/**
	 * Constructor of a computer robot
	 * @param x float
	 * @param y float
	 */
	public ComputerRobot(float x, float y) {
		super(x, y);
		type = ElementType.COMPUTER_ROBOT;
	}
	
	/**
	 * Use the method on the super class Robot
	 */
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException{
		super.draw(this, "robot_IA.png", g, api);
		return this;
	}
	
	/**
	 * Define the method run to a computer robot
	 * @param world Robot Game
	 */
	public void run(final RobotGame world) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				float quarter_diagonal = (float) (Math.sqrt((RobotGame.WIDTH*RobotGame.WIDTH)+(RobotGame.HEIGHT*RobotGame.HEIGHT))/4);
				ArrayList<Element> list = new ArrayList<>();
				list.addAll(world.getPlayers());
				list.addAll(world.getLureRobots());
				
				while(true) {
					//calcul the distance with a Human Robot
					for (final Element p : list){
						float distance = MathUtils.distance(bodyElem.getPosition(), p.getPosition());
						Random rand = new Random();
						
						if(distance < quarter_diagonal && p.typeElem() == ElementType.PLAYER_ROBOT && world.getLureRobots().size() == 0)
						{
							//if the distance is less than the diagonal quarter the computer robot go to the human robot
							distance = MathUtils.distance(bodyElem.getPosition(), p.getPosition());
							final Vec2 force = p.getPosition().sub(bodyElem.getPosition());		
							move(new Vec2(force.x * 10000, force.y * 10000));

							try {
								//next the contact the computer robot sleep since 2 seconds
								Thread.sleep(rand.nextInt(2000));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
						else  {
							//else the computer robot move in the map
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

}
