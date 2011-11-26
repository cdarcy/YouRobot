package fr.umlv.yourobot.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.elements.walls.Wall;


public class ColoredDrawAPI extends DrawAPI {


	@Override
	public void drawWall(Vec2 pos,  BufferedImage img, Color c, Graphics2D g) {
		g.setColor(c);
		g.fillRect((int)pos.x-8, (int)pos.y-8, Wall.WALL_SIZE, Wall.WALL_SIZE);
	}


	@Override
	public void drawCircle(Vec2 pos,float direction,  Color c,  BufferedImage img, Graphics2D g) throws IOException {
		g.setColor(c);
		g.fillOval((int)pos.x-8, (int)pos.y-8, 30, 30);
	}


	@Override
	public void drawEffect(CircleShape circle, Paint p, Color c, BufferedImage img,
			Graphics2D g) throws IOException {
		g.setPaint(p);
		g.draw((Shape) circle);
		return ;
	}


	@Override
	public void drawEffect(Vec2 pos, Paint p, Color c, BufferedImage img,
			Graphics2D g) throws IOException {
		// TODO Auto-generated method stub

	}
}


