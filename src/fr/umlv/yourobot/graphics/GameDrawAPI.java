package fr.umlv.yourobot.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.RobotGame.RobotGameMod;
import fr.umlv.yourobot.RobotGame.RobotTextureMod;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.util.ElementType.ElementClass;
import fr.umlv.yourobot.util.KeyControllers;
import fr.umlv.yourobot.util.MapGenerator;

/**
 * @code {@link MenuGameAPI.java}
 * This class includes all methode to draw all elements.
 * @author BAUDRAND Sebastien <sbaudran@univ-mlv.fr>
 * @author Camille <cdarcy@univ-mlv.fr>
 *
 */
public class GameDrawAPI{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private static RobotGame world;
	private static boolean repaint=false;
	private static BufferedImage imgBackground;
	private static BufferedImage imgEnd;
	private static BufferedImage imgGo;

	public GameDrawAPI(RobotGame currentWorld){
		world = currentWorld;
	}
	/**
	 * Draw a simple circle
	 * @param pos Vec2 : position x and y
	 * @param direction
	 * @param c Color of the circle
	 * @param img BufferedImage with the OutPutFile 
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public void drawCircle(Vec2 pos, float direction, Color c, BufferedImage img, Graphics2D g) throws IOException {
		g.setColor(c);    
		drawRotateImage(pos,direction,img,g);
	}

	/**
	 * Draw a gradient paint circle
	 * @param pos Vec2 : position x and y
	 * @param size int : radius of the circle
	 * @param paint RadialGradientPaint : choose the position, the color and the type of gradient
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public void drawCircle(Vec2 pos, int size, RadialGradientPaint paint,  Graphics2D g) throws IOException {
		g.setPaint(paint);
		g.fillOval((int)pos.x, (int)pos.y, size, size);
	}

	/**
	 * Draw all the wall inside the map
	 * @param pos : position x and y
	 * @param img : BufferedImage with the OutPutFile
	 * @param c Color of the circle
	 * @param g Graphics2D to use the library
	 */
	public void drawWall(Vec2 pos, BufferedImage img, Color c, Graphics2D g) {
		g.setColor(c);
		g.drawImage(img, null, (int)pos.x-5, (int)pos.y-5);

	}

	/**
	 * Draw an image with a rotate
	 * @param pos : position x and y
	 * @param direction
	 * @param img : BufferedImage with the OutPutFile
	 * @param g Graphics2D to use the library
	 */
	public void drawRotateImage(Vec2 pos, float direction, BufferedImage img, Graphics2D g){
		AffineTransform at = new AffineTransform();
		at.rotate(direction * Math.PI / 180.0, img.getWidth() / 2.0, img.getHeight() / 2.0);
		BufferedImageOp bio;
		bio = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(img, bio, (int)pos.x, (int)pos.y);
	}


	/**
	 * 
	 * @param circle
	 * @param p Paint
	 * @param c Color of the circle
	 * @param img : BufferedImage with the OutPutFile
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public void drawEffect(CircleShape circle, Paint p, Color c,
			BufferedImage img, Graphics2D g) throws IOException {
	}

	/**
	 * Draw the game over menu
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public static void drawGameOver(Graphics2D g) throws IOException{
		if(imgGo == null) 
			imgGo = ImageIO.read(new File("images/gameOver.png"));
		g.drawImage(imgGo, null, 300, 200);
	}

	/**
	 * Drow the image of the end of game
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public static void drawEndOfGame(Graphics2D g) throws IOException{
		if(imgEnd == null) 
			imgEnd = ImageIO.read(new File("images/endOfGame.png"));
		g.drawImage(imgEnd, null, 0, 0);
	}

	/**
	 * Draw the background
	 * @param g Graphics2D to use the library
	 */
	public static void drawBackground(Graphics2D g) {
		g.drawImage(imgBackground, null, Wall.WALL_SIZE-8, Wall.WALL_SIZE-8);
		if (KeyControllers.getModeGraphic() == RobotTextureMod.TEXTURE)
			g.drawImage(imgBackground, null, Wall.WALL_SIZE-8, Wall.WALL_SIZE-8);
		if (KeyControllers.getModeGraphic() == RobotTextureMod.GRAPHIC){
			g.drawRect(0, 0, 800, 600);
		}
	}

	/**
	 * General draw method to draw all the elements of the game.
	 * @param g Graphics2D to draw graphics
	 * @throws IOException if exist a problem with the draw method
	 */
	public static void drawElements(RobotGame currentWorld, Graphics2D g) throws IOException {
		if(repaint){
			drawInterface(g);
			repaint = false;
		}
		drawBackground(g);
		drawElementList(g, world.getBonuses());
		drawElementList(g, world.getCircles());
		drawElementList(g, world.getWalls());
		drawElementList(g, world.getRobots());
		drawElementList(g, world.getPlayers());
		drawElementList(g, world.getLureRobots());
		drawElementList(g, world.getToDraw());
	}


	/**
	 * Static method that draws a list of elements on a Graphics2D object
	 * @param g
	 * @param list
	 * @throws IOException
	 */
	private static void drawElementList(Graphics2D g, ArrayList<Element> list) throws IOException{
		if(list.size()>0)
			for(Element e : list){
				if(e != null)	
					e.draw(g, world.getApi());
			}
	}

	/**
	 * Draw the interface with the player name and the current level number
	 * @param g Graphics2D to use the library
	 * @throws IOException if exist a problem with the draw method
	 */
	public static void drawInterface(Graphics2D g) throws IOException {
		MapGenerator.drawArena(g);
		HumanRobot p1 = (HumanRobot) world.getPlayers().get(0);	
		int p1Col = 10;
		g.setColor(Color.ORANGE);

		Font fonte = new Font("Liberation Mono Bold",Font.BOLD,20);
		g.setFont(fonte);
		g.drawString("player 1 - " + Math.round(p1.getLife())+"%", p1Col, 20);
		g.drawString("Level - " + RobotGame.getCurrentLevel()+1, 350, 20);
		if (world.getMode() == RobotGameMod.TWOPLAYER){
			HumanRobot p2 = (HumanRobot) world.getPlayers().get(1);
			int p2Col = 640;
			g.setColor(Color.CYAN);
			Font fonte2 = new Font(Font.SERIF,Font.BOLD, 20);
			g.setFont(fonte2);
			g.drawString("player 2 - " + Math.round(p2.getLife()) +"%", p2Col, 20);
		}
	}

	/**
	 * Sets the repaint boolean to repaint interface at next update loop
	 * Does nothing else
	 */
	public static void drawInterface(){
		repaint = true;
	}

	/**
	 * Modify the backround picture
	 * @param nameBackgroundPicture String : name of the new background picture
	 * @throws IOException if exist a problem with the draw method
	 */
	public static void setBackground(String nameBackgroundPicture) throws IOException {
		imgBackground = ImageIO.read(new File("images/" + nameBackgroundPicture));
	}

}
