package fr.umlv.yourobot.elements.walls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;

abstract public class Wall extends Element{
	public static  int WALL_SIZE = 50;
	public static  int RADIUS = WALL_SIZE/2;
	
	protected PolygonShape shapeElem;
	
	public Wall(float x, float y) {
		super(x, y);

		shapeElem = new PolygonShape();
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shapeElem;
		fixtureDef.density = 0.f;
		fixtureDef.friction = 0.f;
		fixtureDef.restitution = 0.f;
		shapeElem.setAsBox(RADIUS, RADIUS);
	}
	
	public Wall draw(Element wall, String fileName, Graphics2D g, DrawAPI api) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/" + fileName));
		api.drawWall(wall.getBody().getPosition(), img, Color.lightGray, g);
		shapeElem.setAsBox(RADIUS, RADIUS);
		return this;
	}
}
