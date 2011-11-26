package fr.umlv.yourobot.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.elements.Element;

public abstract class DrawAPI {
	
	abstract public void drawWall(Vec2 pos, BufferedImage img, Color c, Graphics2D g);
	public abstract void drawCircle(Vec2 pos, float direction, Color c, BufferedImage img, Graphics2D g) throws IOException;
	abstract public void drawEffect(Vec2 pos, Paint p, Color c, BufferedImage img, Graphics2D g) throws IOException;
	abstract public void drawEffect(CircleShape circle, Paint p, Color c, BufferedImage img,
			Graphics2D g) throws IOException;
	
	public void drawAll(ArrayList<Element> all, Graphics2D g){
		for (Element e : all){
			try {
				e.draw(g, this);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
