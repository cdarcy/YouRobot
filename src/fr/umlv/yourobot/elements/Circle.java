package fr.umlv.yourobot.elements;

import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.io.IOException;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.graphics.GameDrawAPI;

/**
 * @code {@link Circle.java}
 * Circle is a type of Element use to like the start point.
 * @author BAUDRAND Sebastien <sbaudran@univ-mlv.fr>
 * @author Camille <cdarcy@univ-mlv.fr>
 *
 */
public class Circle extends Element {
	private int size;
	
	protected CircleShape shapeElem;
	private RadialGradientPaint paint;
	
	/**
	 * Creat an element type circle 
	 * @param x float
	 * @param y float
	 */
	public Circle(float x, float y) {
		super(x, y);
	}

	/**
	 * Constructor of Circle class
	 * @param paint
	 * @param size int size of the circle
	 * @param x float position x
	 * @param y float position y
	 * @param type ElementType to know what kind of element the circle is
	 */
	public Circle(RadialGradientPaint paint, int size, float x, float y, ElementType type) {
		super(x, y);
		fixtureDef = new FixtureDef();
		shapeElem = new CircleShape();
		fixtureDef.shape = shapeElem;
		shapeElem.m_radius = 0;
		this.size = size;
		this.paint = paint;
		this.type = type;
	}

	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		g.setPaint(paint);
		api.drawCircle(bodyElem.getWorldCenter(), size, paint, g);
		return this;
	}

}
