package fr.umlv.yourobot.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.util.ElementClass;
import fr.umlv.yourobot.util.ElementType;


public class GameDrawAPI{
	

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	static RobotWorld world;
	private static BufferedImage img;

	public GameDrawAPI(RobotWorld world){
		GameDrawAPI.world = world;
	}
	


	public void drawCircle(Vec2 pos, float direction, Color c, BufferedImage img, Graphics2D g) throws IOException {
		g.setColor(c);    
		drawRotateImage(pos,direction,img,g);
	}

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

	public void drawEffect(Vec2 pos, Paint p, Color c, BufferedImage img,
			Graphics2D g) throws IOException {

	}

	public void drawEffect(CircleShape circle, Paint p, Color c,
			BufferedImage img, Graphics2D g) throws IOException {
	}

	public static void drawBackground(Graphics2D g) {
		g.drawImage(img, null, Wall.WALL_SIZE-8, Wall.WALL_SIZE-8);
		RadialGradientPaint paint1 = new RadialGradientPaint(70, HEIGHT-100, 40, new float[]{.3f, 1f}, new Color[]{Color.BLUE, Color.WHITE});
		Circle c1 = new Circle(paint1, 40, 43, HEIGHT-100, ElementType.START_CIRCLE);

		if (world.getMode() == RobotGameMod.TWOPLAYER){
			RadialGradientPaint paint2 = new RadialGradientPaint(70, HEIGHT-150, 40, new float[]{.3f, 1f}, new Color[]{Color.BLUE, Color.WHITE});
			g.setPaint(paint2);
			 g.fill(new Ellipse2D.Float(43, HEIGHT-150, 40, 40));
		}
		RadialGradientPaint paint3 = new RadialGradientPaint(710, 70, 40, new float[]{.3f, 1f}, new Color[]{Color.GREEN, Color.WHITE});
		g.setPaint(paint3);
		g.fill(new Ellipse2D.Float(705, 43, 40, 40));
	}
	
	public static void draw(Graphics2D g) throws IOException {
		for(Element e : world.getListByClass(ElementClass.BONUS)){
			if(e != null)	
				e.draw(g, world.getApi());
		}
		for(Element e : world.getAll()){
			if(e != null && e.classElem() != ElementClass.BONUS)	
				e.draw(g, world.getApi());
		}

		world.getAll().remove(ElementType.EFFECT);
	}


	public static void drawInterface(Graphics2D g) throws IOException {
		HumanRobot p1 = (HumanRobot) world.getListByType(ElementType.PLAYER_ROBOT).get(0);
		
		int p1Col = 10;
		g.setColor(Color.CYAN);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 20);
		g.setFont(fonte);
		g.drawString("player 1 - " + Math.round(p1.getLife())+"%", p1Col, 20);
		if (world.getMode() == RobotGameMod.TWOPLAYER){
			HumanRobot p2 = (HumanRobot) world.getListByType(ElementType.PLAYER_ROBOT).get(1);
			int p2Col = 640;
			g.setColor(Color.CYAN);
			Font fonte2 = new Font(Font.SERIF,Font.BOLD, 20);
			g.setFont(fonte2);
			g.drawString("player 2 - " + Math.round(p2.getLife()) +"%", p2Col, 20);
		}
	}
	
	public static void setBackground(String nameBackgroundPicture) throws IOException {
		img = ImageIO.read(new File("images/" + nameBackgroundPicture));
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
