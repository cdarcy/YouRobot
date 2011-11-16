package fr.umlv.yourobot.elements.walls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;

public class BorderWall extends Wall{
	private BufferedImage img;
	String fileName;
	
	public BorderWall(RobotWorld world, float x, float y, String name) throws IOException {
		super(world, x, y);
		img = ImageIO.read(new File("images/" + name));
		System.out.println("name "+name);
	}


	@Override
	public void draw(Graphics2D g) throws IOException {
		g.fillRect((int)getX(), (int)getY(), WALL_SIZE, WALL_SIZE);
		g.drawImage(img, null, getX(), getY());	
	}
}
