package fr.umlv.yourobot.elements.walls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.graphics.GameDrawAPI;

abstract public class Wall extends Element{
	public static  int WALL_SIZE = 50;
	public static  int RADIUS = WALL_SIZE/2;
	
	protected PolygonShape shapeElem;
	
	public Wall(float x, float y) {
		super(x, y);
		shapeElem = new PolygonShape();
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shapeElem;
		fixtureDef.density = 1.f;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = .3f;
		shapeElem.setAsBox(RADIUS, RADIUS);
	}
	
	public Wall draw(Element wall, String fileName, Graphics2D g, GameDrawAPI api) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/" + fileName));
		api.drawWall(wall.getBody().getWorldCenter(), img, Color.lightGray, g);
		return this;
	}
}
