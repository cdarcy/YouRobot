package fr.umlv.yourobot.elements.robots;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.walls.Wall;

abstract public class Robot extends Element {
	private String pName;
	protected int ROBOT_SIZE = 35;
	protected float SPEED = 10000;
	protected float RADIUS = ROBOT_SIZE / 2;
	protected float direction = 1.f;
	protected CircleShape shapeElem;

	/*
	 * Public contructor for human players
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

	public void fireBomb(final Graphics2D g) {
		final float x = bodyElem.getWorldCenter().x;
		final float y = bodyElem.getWorldCenter().y;
		final RadialGradientPaint paint = new RadialGradientPaint(x, y,
				ROBOT_SIZE, new float[] { .5f, 1f }, new Color[] { Color.RED,
						Color.WHITE });
		g.setPaint(paint);
		new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					g.fill(new Ellipse2D.Float(x - bodyElem.getWorldCenter().x,
							bodyElem.getWorldCenter().y, ROBOT_SIZE + i,
							ROBOT_SIZE + i));
				}
			}
		}.run();
	}

	public String getpName() {
		return pName;
	}

	public void impulse(RobotWorld world) {
		final Vec2 imp = new Vec2();
		Vec2 pos = new Vec2();
		
		pos.x = (float) Math.cos(Math.toRadians(direction));
		pos.x = (float) Math.sin(Math.toRadians(direction));
		
		imp.x = (float) Math.cos(Math.toRadians(direction)) * SPEED;
		imp.y = (float) Math.sin(Math.toRadians(direction))* SPEED;
		
		bodyElem.applyLinearImpulse(imp, bodyElem.getLocalCenter());
		
		final RadialGradientPaint paint = new RadialGradientPaint(pos.x, pos.y,
				10, new float[] { .9f, 1f }, new Color[] { Color.ORANGE,
						Color.YELLOW });
		final Circle c = new Circle(world, paint, 10, pos.x, pos.y);

		System.out.println("impulse");
		world.drawEffect(c);
	}

	public void rotate(float inc) {
		direction = (direction + inc) % 360;
		bodyElem.setType(BodyType.DYNAMIC);
		bodyElem.setLinearDamping(.05f);
	}

	@Override
	public void setBody(Body bodyElem) {
		super.setBody(bodyElem);
	}

	public Robot draw(Element robot, String fileName, Graphics2D g, DrawAPI api) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/" + fileName));
		api.drawCircle(robot.getBody().getPosition(), direction, Color.lightGray, img, g);
		return this;
	}
}
