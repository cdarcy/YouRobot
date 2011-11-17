package fr.umlv.yourobot.elements.walls;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;
import fr.umlv.yourobot.util.MapGenerator;

public class BorderWall extends Wall{
	private BufferedImage img;
	String fileName;
	protected FixtureDef fixtureDef;
	public BorderWall(RobotWorld world, float x, float y, String name) throws IOException {
		super(world, x, y);
		fileName = name;
		shapeElem.setAsBox(WALL_SIZE/2, WALL_SIZE/2, bodyElem.getLocalCenter(), bodyElem.getAngle());
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shapeElem;
		fixture = bodyElem.createFixture(fixtureDef);
		bodyElem.setUserData(new ElementData(100, ElementType.BORDERWALL, this));
		bodyElem.setType(BodyType.STATIC);
	}


	@Override
	public void draw(Graphics2D g) throws IOException {
		g.setColor(MapGenerator.color);
		g.fillRect((int)getX(), (int)getY(), WALL_SIZE, WALL_SIZE);
		if(img == null)
			img = ImageIO.read(new File("images/" + fileName));
		g.drawImage(img, null, getX(), getY());	
	}
}
