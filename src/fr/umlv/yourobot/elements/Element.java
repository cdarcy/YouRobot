package fr.umlv.yourobot.elements;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.ElementType.ElementClass;

/**
 * @code {@link Element.java}
 * Element class is the super class of the YouRobot game.
 * Defined all the methode representative of an element
 * @author BAUDRAND Sebastien <sbaudran@univ-mlv.fr>
 * @author Camille <cdarcy@univ-mlv.fr>
 *
 */
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
	
	/**
	 * Creates body and shape. Sets element position
	 * @param x float position
	 * @param y float position
	 */
	public Element(float x, float y){
		bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.linearDamping = .5f;
	}
	
	/**
	 * Get the body
	 * @return bodyElem Body
	 */
	public Body getBody() {
		return bodyElem;
	}
	
	/**
	 * Get the Fixture
	 * @return fixtureDef
	 */
	public FixtureDef getFixtureDef() {
		return fixtureDef;
	}

	/**
	 * Get the postition of an element
	 * @return Vec2 the poition 
	 */
	public Vec2 getPosition() {
		return bodyElem.getPosition();
	}

	/**
	 * Get the X coordinate value 
	 * @return int
	 */
	public int getX() {
		return (int) bodyElem.getPosition().x;
	}	
	
	/**
	 * Get the Y coordinate value
	 * @return int
	 */
	public int getY() {
		return (int) bodyElem.getPosition().y;
	}	

	/**
	 * Get the BodyDef
	 * @return BodyDef
	 */
	public BodyDef getBodyDef() {
		return bodyDef;
	}

	/**
	 * Set the Body Elem
	 * @param bodyElem  Body
	 */
	public void setBody(Body bodyElem) {
		this.bodyElem = bodyElem;
	}
	
	/**
	 * This methode allow the movment of an element
	 * @param force Vec2 
	 */
	public void move(Vec2 force){
		this.bodyElem.applyForce(force, bodyElem.getLocalCenter());
	}

	/**
	 * Get the Fixture
	 * @return Fixture
	 */
	public Fixture getFixture() {
		return fixture;
	}
	
	/**
	 * Get the type of the element.
	 * All type are defines in an enum : ElementType
	 * @return Element
	 */
	public ElementType typeElem() {
		return type;
	}
	
	/**
	 * Get the specific class of an element type
	 * @return ElementClass
	 */
	public ElementClass classElem() {
		return type.getEClass();
	}

	/**
	 * Set the fixture 
	 * @param fixture Fixture
	 */
	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	/**
	 * All methode draw are override in each class who can be draw an image.
	 * @param g Graphics2D
	 * @param api GameDrawApi
	 * @return Element
	 * @throws IOException
	 */
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		return null;
	}

	/**
	 * This methode is defined in the HumanRobot class
	 * @return the life point
	 */
	public int getLife() {
		return 0;
	}

}
