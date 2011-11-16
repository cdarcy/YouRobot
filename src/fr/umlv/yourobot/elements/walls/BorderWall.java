package fr.umlv.yourobot.elements.walls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class BorderWall extends Wall{
	private BufferedImage img;
	String fileName;
	
	public BorderWall(RobotWorld world, float x, float y, String name) throws IOException {
		super(world, x, y);
		fileName = name;
		bodyElem.setUserData(new ElementData(100, ElementType.BORDERWALL, this));
		bodyElem.setType(BodyType.STATIC);
	}


	@Override
	public void draw(Graphics2D g) throws IOException {
		g.fillRect((int)getX(), (int)getY(), WALL_SIZE, WALL_SIZE);
		if(img == null)
			img = ImageIO.read(new File("images/" + fileName));
		g.drawImage(img, null, getX(), getY());	
	}
}
