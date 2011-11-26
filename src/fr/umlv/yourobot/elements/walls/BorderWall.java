package fr.umlv.yourobot.elements.walls;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;

public class BorderWall extends Wall{
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
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		super.draw(this, fileName, g, api);
		return this;
	}


}
