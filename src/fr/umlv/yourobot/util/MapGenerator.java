package fr.umlv.yourobot.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.walls.BarWall;
import fr.umlv.yourobot.elements.walls.IceWall;
import fr.umlv.yourobot.elements.walls.StoneWall;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.elements.walls.WoodWall;

public class MapGenerator {
	public static int value;
	public static Color color;
	public static RobotWorld world;
	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	public static ArrayList<Element> allWall;

	public static void drawArena(Graphics2D g, RobotWorld world, String nameWallPicture) throws IOException{
		if (value == 0)	color = new Color(0, 0, 100); //BLUE
		else if (value == 1)	color = new Color(100, 0, 0); //RED
		else if (value == 2)	color = new Color(0, 100, 0); //GREEN
		else if (value == 3)	color = new Color(255, 91, 0); //ORANGE

		for (int i = 0; i < WIDTH/Wall.WALL_SIZE; i++){
			if(i<HEIGHT){
				// GAUCHE
				g.setColor(color);
				world.addBorder(0, i*Wall.WALL_SIZE, nameWallPicture).draw(g);
				// DROITE
				//g.rotate(Math.PI, WIDTH-(Wall.WALL_SIZE/2), i*(Wall.WALL_SIZE/2));
				g.setColor(color);
				world.addBorder(WIDTH-Wall.WALL_SIZE, i*Wall.WALL_SIZE, nameWallPicture).draw(g);
			}
			// HAUT
			g.setColor(color);
			world.addBorder((i*Wall.WALL_SIZE)+Wall.WALL_SIZE, 0, nameWallPicture).draw(g);
			// BAS
			g.setColor(color);
			world.addBorder((i*Wall.WALL_SIZE)+Wall.WALL_SIZE, HEIGHT-Wall.WALL_SIZE, nameWallPicture).draw(g);

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

	public static void drawBackground (Graphics2D g, String nameBackground) throws IOException{

		BufferedImage img = ImageIO.read(new File("images/" + nameBackground));	
		g.drawImage(img, null, Wall.WALL_SIZE, Wall.WALL_SIZE);

	}

	public static void setWalls(Graphics2D g, int level) throws IOException{
		int wallNumber = level + 10;
		allWall = new ArrayList<>();
		ArrayList<Vec2> allPositions = new ArrayList<>();
		BarWall bW;
		WoodWall wW;
		IceWall iW;
		StoneWall sW;
		for (int i=0 ; i<wallNumber ; i++){
			//give a position in the map between the border
			//the map is represented like a matrix
			int posX = Wall.WALL_SIZE + Wall.WALL_SIZE * (int) MathUtils.randomFloat(0, ((WIDTH-100)/Wall.WALL_SIZE)-2);
			int posY = Wall.WALL_SIZE + Wall.WALL_SIZE * (int) MathUtils.randomFloat(0, ((HEIGHT-100)/Wall.WALL_SIZE)-2);
			System.out.println("i : "+i+" x "+posX+" y "+posY);
			Vec2 vec2 = new Vec2(posX, posY);

			if(!allPositions.contains(vec2)){
				allPositions.add(vec2);
				int randomNumber = (int) MathUtils.randomFloat(0, 4);
				System.out.println(randomNumber);
				switch (randomNumber){
					case (0) : 	world.addWall(new WoodWall(world, posX, posY));
								break;
					case (1) : 	world.addWall(new StoneWall(world, posX, posY));
								break;
					case (2) : 	world.addWall(new IceWall(world, posX, posY));		
								break;
					case (3) : 	world.addWall(new BarWall(world, posX, posY));		
								break;
				}
			}
			else
				i--;
		}
	}
}

