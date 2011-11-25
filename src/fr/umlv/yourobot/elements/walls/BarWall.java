package fr.umlv.yourobot.elements.walls;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.graphics.DrawAPI;
import fr.umlv.yourobot.util.ElementType;


public class BarWall extends Wall {

	public BarWall(float x, float y) {
		super(x, y);
		type = ElementType.BARWALL;
	}



	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/barrierWall.png"));	
		g.drawImage(img, null, getX(), getY());	
		shapeElem.setAsBox(WALL_SIZE, WALL_SIZE);
		return this;
	}

}
