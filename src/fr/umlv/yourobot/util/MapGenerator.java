package fr.umlv.yourobot.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Collection;

import java.awt.geom.Ellipse2D.Float;


import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.walls.BarWall;
import fr.umlv.yourobot.elements.walls.BorderWall;
import fr.umlv.yourobot.elements.walls.IceWall;
import fr.umlv.yourobot.elements.walls.StoneWall;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.elements.walls.WoodWall;
import fr.umlv.zen.KeyboardEvent;

public class MapGenerator {

	private static int value;
	private static Color color;
	private static RobotWorld world;
	private static BufferedImage img;
	private static ArrayList<BorderWall> arena;
	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	private static final Point2D Point2D = null;
	public static ArrayList<Element> allWall;


	public static void drawArena(Graphics2D g, RobotWorld world, String nameWallPicture) throws IOException{
		arena = new ArrayList<>();
		if (value == 0)	color = new Color(0, 0, 100); //BLUE
		else if (value == 1)	color = new Color(100, 0, 0); //RED
		else if (value == 2)	color = new Color(0, 100, 0); //GREEN
		else if (value == 3)	color = new Color(255, 91, 0); //ORANGE
		int idx = 0;
		for (int i = 0; i < WIDTH/Wall.WALL_SIZE; i++){
			if(i<HEIGHT){
				// GAUCHE
				g.setColor(color);
				Element element = world.addBorder(0, i*Wall.WALL_SIZE, nameWallPicture);
				arena.add((BorderWall) element.draw(g, world.getApi()));
				
				// DROITE
				//g.rotate(Math.PI, WIDTH-(Wall.WALL_SIZE/2), i*(Wall.WALL_SIZE/2));
				g.setColor(color);
			    element =  world.addBorder(WIDTH-(Wall.WALL_SIZE-10), i*Wall.WALL_SIZE, nameWallPicture);
				arena.add((BorderWall) element.draw(g, world.getApi()));
			}
			// HAUT
			g.setColor(color);
			Element element = world.addBorder((i*Wall.WALL_SIZE)+Wall.WALL_SIZE, 0, nameWallPicture);
			arena.add((BorderWall) element.draw(g, world.getApi()));
			// BAS
			g.setColor(color);
			element = world.addBorder((i*Wall.WALL_SIZE)+Wall.WALL_SIZE, HEIGHT-(Wall.WALL_SIZE-10), nameWallPicture);
			arena.add((BorderWall) element.draw(g, world.getApi()));
			
		}
		
	}

	public static void mapRandom (RobotWorld w, Graphics2D g) throws IOException{
		new MapStyle();
		value =  (int) MathUtils.randomFloat(0, 4);
		world = w;
		String nameBackgroundPicture = MapStyle.background.get(value);
		String nameWallPicture = MapStyle.wall.get(value);
		world.setBackground(nameBackgroundPicture);
		drawArena(g, world, nameWallPicture);
		setWalls(g, 4);
	}

	public static void drawArena(Graphics2D g){
		for(int i=0;i<arena.size();i++)
			try {
				arena.get(i).draw(g, world.getApi());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public static void drawBackground (Graphics2D g, String nameBackground) throws IOException{
		if(img == null)
			img = ImageIO.read(new File("images/" + nameBackground));	
		g.drawImage(img, null, Wall.WALL_SIZE-10, Wall.WALL_SIZE-10);
		BufferedImage img = ImageIO.read(new File("images/" + nameBackground));
		g.drawImage(img, null, Wall.WALL_SIZE, Wall.WALL_SIZE);
	}

	public static void setWalls(Graphics2D g, int level) throws IOException{
		int wallNumber = level + 10;
		allWall = new ArrayList<>();
		ArrayList<Vec2> allPositions = new ArrayList<>();
		
		RadialGradientPaint paint1 = new RadialGradientPaint(75, 75, 40, new float[]{0f, 1f}, new Color[]{Color.BLUE, Color.BLUE});
		Circle circle1 = new Circle (world, paint1, 40, 80, 80);
		allPositions.add(new Vec2(80, 80));
		RadialGradientPaint paint2 = new RadialGradientPaint(75, 515, 40, new float[]{0f, 1f}, new Color[]{Color.BLUE, Color.BLUE});
		Circle circle2 = new Circle (world, paint2, 40, 80, 520);
		allPositions.add(new Vec2(80, 520));
		RadialGradientPaint paint3 = new RadialGradientPaint(710, 300, 40, new float[]{0f, 1f}, new Color[]{Color.BLUE, Color.BLUE});
		Circle circle3 = new Circle (world, paint3, 40, 720, 300);
		allPositions.add(new Vec2(720, 300));
		
		world.drawIOMap(circle1);
		world.drawIOMap(circle2);
		world.drawIOMap(circle3);
		
		for (int i=0 ; i<wallNumber ; i++){
			//give a position in the map between the border
			//the map is represented like a matrix
			int posX = Wall.WALL_SIZE + Wall.WALL_SIZE * (int) MathUtils.randomFloat(0, ((WIDTH-100)/Wall.WALL_SIZE)-2);
			int posY = Wall.WALL_SIZE + Wall.WALL_SIZE * (int) MathUtils.randomFloat(0, ((HEIGHT-100)/Wall.WALL_SIZE)-2);
			Vec2 vec2 = new Vec2(posX, posY);

			if(!allPositions.contains(vec2)){
				allPositions.add(vec2);
				int randomNumber = (int) MathUtils.randomFloat(0, 4);
				switch (randomNumber){
					case (0) : 	world.addWall(new WoodWall(posX, posY));
								break;
					case (1) : 	world.addWall(new StoneWall(posX, posY));
								break;
					case (2) : 	world.addWall(new IceWall(posX, posY));		
								break;
					case (3) : 	world.addWall(new BarWall(posX, posY));		
								break;
				}
			}
			else
				i--;
		}
	}

	public static BufferedImage getBackground() {
		return img;
	}

	public static ArrayList<BorderWall> getArena() {
		return arena;
	}

}

