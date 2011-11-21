package fr.umlv.yourobot.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

public interface DrawAPI {
	
	public void drawWall(Vec2 pos, BufferedImage img, Color c, Graphics2D g);
	void drawCircle(Vec2 pos, float direction, Color c, BufferedImage img, Graphics2D g) throws IOException;
	void drawEffect(Vec2 pos, Paint p, Color c, BufferedImage img, Graphics2D g) throws IOException;
	void drawEffect(CircleShape circle, Paint p, Color c, BufferedImage img,
			Graphics2D g) throws IOException;
	
}
