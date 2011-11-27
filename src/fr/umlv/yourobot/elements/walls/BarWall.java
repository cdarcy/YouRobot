package fr.umlv.yourobot.elements.walls;

import java.awt.Graphics2D;
import java.io.IOException;

import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;


public class BarWall extends Wall {

	public BarWall(float x, float y) {
		super(x, y);
		type = ElementType.BARWALL;
	}



	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		this.draw(this, "barrierWall.png", g, api);
		return this;
	}

}
