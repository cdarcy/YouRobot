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
	private final float quarter_diagonal = (float) (Math.sqrt((RobotGame.WIDTH*RobotGame.WIDTH)+(RobotGame.HEIGHT*RobotGame.HEIGHT))/4);
	
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
	
	private static class SnapElement {
		Element element;
		BodyType bodyType;
		Joint joint;
	}
	private final ArrayList<SnapElement> snapElements = new ArrayList<>();
	private RobotGame world;
	
	@Override
	public void run(RobotGame world, HumanRobot robot){
		this.world = world;
		start = System.nanoTime();
		for(final Element wall : world.getWalls()){
			float distance = MathUtils.distance(robot.getPosition(), wall.getPosition());
			if(distance < quarter_diagonal/2){
				SnapElement snapElement = new SnapElement();
				snapElement.element = wall;
				snapElement.bodyType = wall.getBody().getType();
				wall.getBody().setType(BodyType.DYNAMIC);
				RevoluteJointDef rjd = new RevoluteJointDef();
				rjd.initialize(robot.getBody(),	wall.getBody(), new Vec2(1,1));
				snapElement.joint = world.addJoint(rjd);
				snapElements.add(snapElement);
			}
		}
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
		if(start + ((long)length*1000000000) > System.nanoTime()){
			timeleft = (int) ((length*1000)-(System.nanoTime()-start)/1000000);
			return true;
		}
		for(SnapElement snapElement : snapElements) {
			snapElement.element.getBody().setType(snapElement.bodyType);
			this.world.destroyJoint(snapElement.joint);
		}
		snapElements.clear();
		return false;
	}
}
