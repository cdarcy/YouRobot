package fr.umlv.yourobot.elements;

import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.io.IOException;

import org.jbox2d.dynamics.BodyType;

import fr.umlv.yourobot.RobotWorld;

public class Circle extends Element {
	private Float circle;
	private RadialGradientPaint paint;
	private int SIZE = 50;
	public Circle(RobotWorld world, float x, float y) {
		super(world, x, y);
		bodyElem.setType(BodyType.STATIC);
	}

	public Circle(RobotWorld world, RadialGradientPaint paint,float x, float y) {
		super(world, x, y);
		
		this.circle = new Ellipse2D.Float(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
		
		this.paint = paint;
		SIZE+=10;
	}

	@Override
	public void draw(Graphics2D g) throws IOException {
		g.setPaint(paint);
		g.draw(circle);
	}

}
