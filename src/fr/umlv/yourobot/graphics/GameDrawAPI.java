package fr.umlv.yourobot.graphics;

import java.awt.Color;
import java.awt.Font;
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

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.RobotWorld.RobotGameMod;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.util.ElementType.ElementClass;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.MapGenerator;

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
	
	public static void drawGameOver(Graphics2D g) throws IOException{
		BufferedImage img = ImageIO.read(new File("images/gameOver.png"));
		g.drawImage(img, null, 300, 200);
	}

	public static void drawBackground(Graphics2D g) {
		g.drawImage(img, null, Wall.WALL_SIZE-8, Wall.WALL_SIZE-8);
	}
	
	/* 
	 *TODO : switch case in RobotWorld to manage lists to store bonuses, circles, robots, players and walls
	 */
	public static void draw(Graphics2D g) throws IOException {
		for(Element e : world.getListByClass(ElementClass.BONUS)){
			if(e != null)	
				e.draw(g, world.getApi());
		}
		for(Element e : world.getListByClass(ElementClass.CIRCLE)){
			if(e != null)	
				e.draw(g, world.getApi());
		}
		for(Element e : world.getAll()){
			if(e != null && e.classElem() != ElementClass.BONUS && e.typeElem() != ElementType.START_CIRCLE)	
				e.draw(g, world.getApi());
		}

		world.getAll().remove(ElementType.EFFECT);
	}


	public static void drawInterface(Graphics2D g) throws IOException {
		HumanRobot p1 = (HumanRobot) world.getPlayers().get(0);
		BufferedImage img = ImageIO.read(new File("images/" + MapGenerator.getNameWallPicture()));
		for (int i = 0; i < 800/Wall.WALL_SIZE; i++){
			g.drawImage(img , null, (i*Wall.WALL_SIZE)+Wall.WALL_SIZE, 0);
		}
		
		int p1Col = 10;
		g.setColor(Color.CYAN);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 20);
		g.setFont(fonte);
		g.drawString("player 1 - " + Math.round(p1.getLife())+"%", p1Col, 20);
		g.drawString("Level - " + RobotWorld.getCurrentLevel()+1, 350, 20);
		if (world.getMode() == RobotGameMod.TWOPLAYER){
			HumanRobot p2 = (HumanRobot) world.getPlayers().get(1);
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
