package fr.umlv.yourobot.elements.walls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class BorderWall extends Wall{
	private BufferedImage img;
	String fileName;

	public BorderWall(float x, float y, String name) throws IOException {
		super(x, y);
		fileName = name;
		type = ElementType.BORDERWALL;
		
	}


	@Override
	public BorderWall draw(Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/" + fileName));
		g.drawImage(img, null, getX()-8, getY()-8);	
		shapeElem.setAsBox(WALL_SIZE, WALL_SIZE);
		return this;
	}
}
