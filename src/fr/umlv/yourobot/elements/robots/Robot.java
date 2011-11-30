package fr.umlv.yourobot.elements.robots;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;

/**
 * @code {@link Robot.java}
 * This is the super class of robot and defined all methode using by all robot type
 * @author BAUDRAND Sebastien <sbaudran@univ-mlv.fr>
 * @author Camille <cdarcy@univ-mlv.fr>
 *
 */
abstract public class Robot extends Element {
	protected int ROBOT_SIZE = 35;
	protected float SPEED = 10000;
	protected float RADIUS = ROBOT_SIZE / 2;
	protected float direction = 1.f;
	protected CircleShape shapeElem;


	/**
	 * Public contructor for human players
	 * @param x float
	 * @param y float
	 */
	public Robot(float x, float y) {
		super(x, y);
		shapeElem = new CircleShape();
		shapeElem.m_radius = RADIUS;
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shapeElem;
		fixtureDef.density = 1.f;
		fixtureDef.friction = .3f;
		fixtureDef.restitution = .5f;
		// fixtureDef.filter = this.filter;

	}

	/**
	 * This method can moved randomly
	 * @param world RobotGame
	 */
	public void impulse(RobotGame world) {
		
		final Vec2 imp = new Vec2();		
		final Vec2 dir = new Vec2();

		//random coordinate value
		imp.x = (float) Math.cos(Math.toRadians(direction)) * SPEED;
		imp.y = (float) Math.sin(Math.toRadians(direction)) * SPEED;
		//linear impulse to move the robot
		bodyElem.applyLinearImpulse(imp, bodyElem.getLocalCenter());

		/*	
		float x = bodyElem.getLinearVelocity().x;
		float y = bodyElem.getLinearVelocity().y;
		double hypo = Math.sqrt(x*x + y*y);
		double direction = Math.toDegrees(Math.acos(y/hypo));
	
		dir.x =  bodyElem.getPosition().x+(float)Math.cos(Math.toRadians(direction));
		dir.y =  bodyElem.getPosition().y+(float)Math.sin(Math.toRadians(direction));
		
=======
		//calcul of the effect place
		dir.x = (float) (bodyElem.getPosition().x+Math.cos(Math.toRadians((direction+135)%360)+30));
		dir.y = (float) (bodyElem.getPosition().y+Math.sin(Math.toRadians((direction+135)%360)+30));

>>>>>>> 73ed45941279178649f253088c28e6de70d6f766
		RadialGradientPaint paint = new RadialGradientPaint(dir.x, dir.y, 10, new float[]{.5f, 1f}, new Color[]{Color.YELLOW, Color.ORANGE});
		Circle circle = new Circle (paint, 10, dir.x, dir.y, ElementType.EFFECT);
		world.addDynamicElement(circle);
		*/
	}

	/**
	 * Creat a movement with a defined speed
	 * @param speed float
	 */
	public void impulse(float speed){
		final Vec2 imp = new Vec2();
		imp.x = (float) Math.cos(Math.toRadians(direction)) * speed;
		imp.y = (float) Math.sin(Math.toRadians(direction)) * speed;
		bodyElem.applyLinearImpulse(imp, bodyElem.getLocalCenter());

	}
	
	/**
	 * Can rotate the robot whan it change his direction
	 * @param inc float
	 */
	public void rotate(float inc) {
		direction = (direction + inc) % 360;
		bodyElem.setType(BodyType.DYNAMIC);
		
	}

	/**
	 * Override the setBody to set the good value
	 */
	@Override
	public void setBody(Body bodyElem) {
		super.setBody(bodyElem);
	}

	/**
	 * Draw a robot on the map.
	 * @param robot Element
	 * @param fileName String : name of the image
	 * @param g Graphics2D
	 * @param api GameDrawAPI
	 * @return
	 * @throws IOException
	 */
	public Robot draw(Element robot, String fileName, Graphics2D g, GameDrawAPI api) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/" + fileName));
		api.drawCircle(robot.getBody().getPosition(), direction, Color.lightGray, img, g);
		return this;
	}

	abstract public void run(final RobotGame world);
}
