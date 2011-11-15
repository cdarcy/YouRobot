package fr.umlv.yourobot.elements.robots;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.KeyController;
import fr.umlv.zen.KeyboardEvent;

abstract public class Robot extends Element{
	private String pName;
	protected int ROBOT_SIZE = 46;
	private final static int SPEED = 1000000;
	protected FixtureDef fixtureDef;
	protected Fixture fixture;
	protected KeyController controller;
	private double direction = 0.;
	

	/*
	 * Public contructor for human players
	 * 
	 */
	public Robot(String pName, RobotWorld world, float x, float y) {
		super(world, x, y);
		this.pName = pName;
		fixtureDef = new FixtureDef();
		//shapeElem.setAsBox(ROBOT_SIZE/2, ROBOT_SIZE/2);
		shapeElem.setAsBox(ROBOT_SIZE/2, ROBOT_SIZE/2, bodyElem.getLocalCenter(), bodyElem.getAngle());
		fixtureDef.shape = shapeElem;
		fixtureDef.density = 1.f;
		fixtureDef.friction = .7f;
		fixtureDef.restitution = .3f;
		bodyElem.createFixture(fixtureDef);
		bodyElem.resetMassData();
		bodyElem.setType(BodyType.DYNAMIC);
		
	}
	public void setBody(Body bodyElem){
		super.setBody(bodyElem);
	}

	abstract public void draw(Graphics2D g) throws IOException;

	public void fireBomb(final Graphics2D g){
		final float x = bodyElem.getWorldCenter().x;
		final float y = bodyElem.getWorldCenter().y;
		RadialGradientPaint paint = new RadialGradientPaint(x, y, ROBOT_SIZE, new float[]{.5f, 1f}, new Color[]{Color.RED, Color.WHITE});
		g.setPaint(paint);
		new Runnable() {

			@Override
			public void run() {
				for (int i = 0 ;i < 100; i++){
					g.fill(new Ellipse2D.Float(x - bodyElem.getWorldCenter().x, bodyElem.getWorldCenter().y, ROBOT_SIZE+i, ROBOT_SIZE+i));
				}
			}
		}.run();
	}

	public void rotateLeft() {
		//TODO get direction from this.body.getVelocity() ???
		direction = (direction - 10.)%360;
		if(direction<0) direction = 360;
		Vec2 vec = new Vec2();
		vec.x = (float)Math.cos(Math.toRadians(direction))*SPEED;
		vec.y = (float)Math.sin(Math.toRadians(direction))*SPEED;
		bodyElem.setLinearVelocity(vec);
		//TODO need to check dimension of the PolygonShape because I think something is wrong collision are a little bit weird sometimes
		shapeElem.setAsBox(ROBOT_SIZE/2, ROBOT_SIZE/2, new Vec2(vec.x-ROBOT_SIZE/2, vec.y-ROBOT_SIZE/2), (int)direction);
		}
	
	public void rotateRight() {
		//TODO get direction from this.body.getVelocity() ???
		direction = (direction + 10.)%360;
		Vec2 vec = new Vec2();
		vec.x = (float)Math.cos(Math.toRadians(direction))*SPEED;
		vec.y = (float)Math.sin(Math.toRadians(direction))*SPEED;
		bodyElem.setLinearVelocity(vec);
		//TODO need to check dimension of the PolygonShape, I think something is wrong collision are a little bit weird sometimes
		shapeElem.setAsBox(ROBOT_SIZE/2, ROBOT_SIZE/2, new Vec2(vec.x-ROBOT_SIZE/2, vec.y-ROBOT_SIZE/2), (int)direction);
		}
	
	
	public void control(Graphics2D g, KeyboardEvent event){
		controller.control(event);
	}

	public String getpName() {
		return pName;
	}
	public float getLife() {
		return ((ElementData) bodyElem.getUserData()).life();
	}



}
