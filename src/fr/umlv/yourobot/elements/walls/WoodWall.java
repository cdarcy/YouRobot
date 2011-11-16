package fr.umlv.yourobot.elements.walls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class WoodWall extends Wall {

	public WoodWall(RobotWorld world, float x, float y) {
		super(world, x, y);
		bodyElem.setUserData(new ElementData(100, ElementType.WOODWALL, this));
		bodyElem.setType(BodyType.STATIC);
	}

	@Override
	public void draw(Graphics2D g) throws IOException{
		Vec2 pos = this.bodyElem.getPosition();
		g.fillRect((int)pos.x, (int)pos.y, WALL_SIZE, WALL_SIZE);
		if(img == null)
				img = ImageIO.read(new File("images/woodWall.png"));
		g.drawImage(img, null, getX(), getY());
	}

}
