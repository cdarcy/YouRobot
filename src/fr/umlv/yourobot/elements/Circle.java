package fr.umlv.yourobot.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.io.IOException;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.graphics.GameDrawAPI;

public class Circle extends Element {
	private int size;
	
	protected CircleShape shapeElem;
	private RadialGradientPaint paint;
	public Circle(float x, float y) {
		super(x, y);
		bodyElem.setType(BodyType.DYNAMIC);	
	}

	public Circle(RadialGradientPaint paint, int size, float x, float y, ElementType type) {
		super(x, y);
		fixtureDef = new FixtureDef();
		shapeElem = new CircleShape();
		fixtureDef.shape = shapeElem;
		shapeElem.m_radius = size/2;
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
