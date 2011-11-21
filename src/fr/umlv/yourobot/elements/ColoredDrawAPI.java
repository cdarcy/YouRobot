package fr.umlv.yourobot.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

public class ColoredDrawAPI implements DrawAPI {


	@Override
	public void drawWall(Vec2 pos,  BufferedImage img, Color c, Graphics2D g) {
		g.setColor(c);
		g.drawImage(img, null, (int)pos.x-8, (int)pos.y-8);
	}
	

	@Override
	public void drawCircle(Vec2 pos,float direction,  Color c,  BufferedImage img, Graphics2D g) throws IOException {
		g.setColor(c);
		g.drawImage(img, null, (int)pos.x-8, (int)pos.y-8);
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

	
