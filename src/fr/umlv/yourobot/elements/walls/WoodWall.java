package fr.umlv.yourobot.elements.walls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class WoodWall extends Wall {

	public WoodWall(float x, float y) {
		super(x, y);
		type = ElementType.WOODWALL;
	}

	@Override
	public Element draw(Graphics2D g) throws IOException {
		Vec2 pos = bodyElem.getPosition();
		if(img == null)
			img = ImageIO.read(new File("images/woodWall.png"));	
		g.drawImage(img, null, (int)pos.x-8, (int)pos.y-8);
		g.setColor(Color.BLACK);
		shapeElem.setAsBox(WALL_SIZE, WALL_SIZE);
		return this;
	}
	

}
