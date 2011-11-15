package fr.umlv.yourobot.elements.walls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;

public class WoodWall extends Wall {

	public WoodWall(RobotWorld world, float x, float y) {
		super(world, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics2D g){
		Vec2 pos = this.bodyElem.getPosition();
		g.setColor(Color.BLACK);
		g.fillRect((int)pos.x, (int)pos.y, WALL_SIZE, WALL_SIZE);
		if(img == null)
			try {
				img = ImageIO.read(new File("images/block.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		g.drawImage(img, null, getX(), getY());
	}

}
