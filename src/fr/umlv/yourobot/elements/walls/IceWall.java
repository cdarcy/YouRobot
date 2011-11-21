package fr.umlv.yourobot.elements.walls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.util.ElementType;

public class IceWall extends Wall {

	public IceWall(float x, float y) {
		super(x, y);
		type = ElementType.ICEWALL;
	}

	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		final Vec2 pos = bodyElem.getPosition();
		if (img == null) {
			img = ImageIO.read(new File("images/iceWall.png"));
		}
		api.drawWall(pos, img, Color.BLUE, g);
		shapeElem.setAsBox(WALL_SIZE, WALL_SIZE);
		return this;
	}

}
