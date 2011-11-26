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
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementClass;
import fr.umlv.yourobot.util.ElementType;

abstract public class Element {

	protected final int ELEM_SIZE = 20;
	final ArrayList<Shape> jbox2DShapes = new ArrayList<>();
	protected BufferedImage img;
	protected Body bodyElem;
	protected BodyDef bodyDef;
	protected Fixture fixture;
	protected FixtureDef fixtureDef;
	protected Filter filter;
	protected ElementType type;
	public Color color = Color.RED;
	int size;
	


	/*
	 * Creates body and shape. Sets element position 
	 */
	public Element(float x, float y){
		bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.linearDamping = .5f;
	}
	
	public Body getBody() {
		return bodyElem;
	}
	public FixtureDef getFixtureDef() {
		return fixtureDef;
	}

	
	public Vec2 getPosition() {
		return bodyElem.getPosition();
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

	public Fixture getFixture() {
		return fixture;
	}
	
	public ElementType typeElem() {
		return type;
	}
	
	public ElementClass classElem() {
		return type.getEClass();
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		return null;
	}

}
