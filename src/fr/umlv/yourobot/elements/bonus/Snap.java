package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;


/**
 * @code {@link Snap}
 * Sub bonus simulating snap bonus
 * @see {@link Element} 
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 *
 */
public class Snap  extends Bonus  {
	private float quarter_diagonal;
	
	/**
	 * Main constructor
	 * Initializes a snap with a random length
	 * @param x
	 * @param y
	 */
	public Snap(float x, float y) {
		super(x, y);
		length = (int) Math.round((Math.random()*13)+2);
		type = ElementType.SNAP;
	}

	@Override
	public void run(RobotGame world, HumanRobot robot){
		start = (int) System.nanoTime();		
	}
	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		super.draw("snap.png", g, api);
		Graphics gr = g.create();
		gr.setColor(Color.WHITE);
		gr.setFont(new Font("Sans", Font.BOLD, 15));
		drawTime(gr);
		return this;
	}

	/**
	 * Specific method used to draw time left of the snap
	 * @param gr
	 */
	public void drawTime(Graphics gr){
		gr.drawString(length+"s", (int) bodyElem.getPosition().x, (int)bodyElem.getPosition().y);
	}

	@Override
	public boolean update() {
		if(((System.nanoTime()-start)/1000000)<(length*1000)){
			timeleft = (int) ((length*1000)-(System.nanoTime()-start)/1000000);
			for(final Element wall : world.getWalls()){
				float distance = MathUtils.distance(robot.getPosition(), wall.getPosition());
				if(distance < quarter_diagonal){
					wall.getBody().setType(BodyType.DYNAMIC);
					Vec2 pos = new Vec2(robot.getPosition());
					Vec2 force = pos.sub(wall.getPosition());
					wall.getBody().applyForce(new Vec2((force.x*distance),(force.y*distance)), wall.getBody().getWorldCenter());
				}
			}
			return true;
		}
		return false;
	}
}
