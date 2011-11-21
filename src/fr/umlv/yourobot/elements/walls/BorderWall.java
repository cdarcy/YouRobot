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
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.util.ElementType;

public class BorderWall extends Wall{
	private BufferedImage img;
	String fileName;

	public BorderWall(float x, float y, String name) throws IOException {
		super(x, y);
		fileName = name;
		type = ElementType.BORDERWALL;
		
	}


	public BorderWall(BorderWall borderWall) throws IOException {
		this(borderWall.getBody().getPosition().x, borderWall.getBody().getPosition().y, "");
	}


	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		super.draw(this, fileName, g, api);
		return this;
	}


}
