package fr.umlv.yourobot.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

public class TexturedDrawAPI implements DrawAPI {

	@Override
	public void drawCircle(Vec2 pos, float direction, Color c, BufferedImage img, Graphics2D g) throws IOException {
		g.setColor(c);    
		drawRotateImage(pos,direction,img,g);
	}

	@Override
	public void drawWall(Vec2 pos, BufferedImage img, Color c, Graphics2D g) {
		g.setColor(c);
		g.drawImage(img, null, (int)pos.x-5, (int)pos.y-5);
		
	}
	
	public void drawRotateImage(Vec2 pos, float direction, BufferedImage img, Graphics2D g){
		AffineTransform at = new AffineTransform();
	    at.rotate(direction * Math.PI / 180.0, img.getWidth() / 2.0, img.getHeight() / 2.0);
	    BufferedImageOp bio;
	    bio = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(img, bio, (int)pos.x, (int)pos.y);
	}

	@Override
	public void drawEffect(Vec2 pos, Paint p, Color c, BufferedImage img,
			Graphics2D g) throws IOException {
		
	}

	@Override
	public void drawEffect(CircleShape circle, Paint p, Color c,
			BufferedImage img, Graphics2D g) throws IOException {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void drawEffect(Shape circle, Paint paint, Color c,
			BufferedImage img, Graphics2D g) throws IOException {
		g.setPaint(paint);
		g.draw(circle);
		return this;
	}
	}*/
}
