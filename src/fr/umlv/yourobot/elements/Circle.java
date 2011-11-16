package fr.umlv.yourobot.elements;

import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.io.IOException;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class Circle extends Element {
	private Float circle;
	protected FixtureDef fixtureDef;
	private RadialGradientPaint paint;
	public Circle(RobotWorld world, float x, float y) {
		super(world, x, y);
		bodyElem.setType(BodyType.DYNAMIC);
		bodyElem.setUserData(new ElementData(100, ElementType.EFFECT, this));
	
	}

	public Circle(RobotWorld world, RadialGradientPaint paint, float size,float x, float y) {
		super(world, x, y);
		shapeElem.setAsBox(size/2, size/2, bodyElem.getLocalCenter(), bodyElem.getAngle());
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shapeElem;
		fixture = bodyElem.createFixture(fixtureDef);
		this.circle = new Ellipse2D.Float(x - size/2, y - size/2, size, size);
		
		this.paint = paint;
		
	}

	@Override
	public void draw(Graphics2D g) throws IOException {
		g.setPaint(paint);
		g.draw(circle);
	}

}
