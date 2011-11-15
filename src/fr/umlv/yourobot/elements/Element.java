package fr.umlv.yourobot.elements;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class Element {

	protected final int ELEM_SIZE = 20;
	final ArrayList<Shape> jbox2DShapes = new ArrayList<>();
	protected BufferedImage img;
	protected Body bodyElem;
	protected PolygonShape shapeElem;
	protected Vec2 positionElem;
	protected BodyDef bodyDef;
	protected Fixture fixture;
	public Color color = Color.RED;
	int size;

	/*
	 * Creates body and shape. Sets element position 
	 */
	public Element(RobotWorld world, float x, float y){
		shapeElem = new PolygonShape();
		bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyElem = world.getJBoxWorld().createBody(bodyDef);
	}
	
	public Body getBody() {
		return bodyElem;
	}

	public PolygonShape getShape() {
		return shapeElem;
	}
	
	public Vec2 getPosition() {
		return fixture.getBody().getPosition();
	}


	public int getX() {
		return (int) bodyElem.getPosition().x;
	}	
	
	public int getY() {
		return (int) bodyElem.getPosition().y;
	}	


	public BodyDef getBodyDef() {
		return bodyDef;
	}

	public void setBody(Body bodyElem) {
		this.bodyElem = bodyElem;
	}
	
	public void move(Vec2 force){
		this.bodyElem.applyForce(force, bodyElem.getLocalCenter());
	}
	
	public void draw(Graphics2D graphics) throws IOException {
		Vec2 pos = this.bodyElem.getPosition();
		graphics.fillRect((int)pos.x, (int)pos.y, ELEM_SIZE, ELEM_SIZE);
	}

	public ElementType getType() {
		return ((ElementData) getBody().getUserData()).type();
	}


}
