package fr.umlv.yourobot.elements;

import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.io.IOException;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotWorld;
<<<<<<< HEAD
import fr.umlv.yourobot.graphics.DrawAPI;
import fr.umlv.yourobot.util.ElementType;
=======
import fr.umlv.yourobot.graphics.GameDrawAPI;
>>>>>>> 802290debd1d84e6cb628fed522789f61efc2a0b

public class Circle extends Element {
	private Float circle;
	protected CircleShape shapeElem;
	private RadialGradientPaint paint;
	public Circle(RobotWorld world, float x, float y) {
		super(x, y);
		bodyElem.setType(BodyType.DYNAMIC);	
	}

	public Circle(RadialGradientPaint paint, float size, float x, float y, ElementType type) {
		super(x, y);
		fixtureDef = new FixtureDef();
		shapeElem = new CircleShape();
		fixtureDef.shape = shapeElem;
		this.circle = new Ellipse2D.Float(x - size/2, y - size/2, size, size);
		this.paint = paint;
		this.type = type;
	}

	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		g.setPaint(paint);
		g.fill(circle);
		return this;
	}

}
