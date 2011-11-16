package fr.umlv.yourobot.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.walls.Wall;

public class MapGenerator {
	public static int value;
	public static Color color;
	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	
	public static void drawArena(Graphics2D g, RobotWorld world, String nameWallPicture) throws IOException{
		if (value == 0)	color = new Color(0, 0, 100); //BLUE
		else if (value == 1)	color = new Color(100, 0, 0); //RED
		else if (value == 2)	color = new Color(0, 200, 0); //GREEN
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
	
	public static void mapRandom (RobotWorld world, Graphics2D g) throws IOException{
		new MapStyle();
		value =  (int) MathUtils.randomFloat(0, 3);
		String nameBackgroundPicture = MapStyle.background.get(value);
		String nameWallPicture = MapStyle.wall.get(value);
		world.setBackground(nameBackgroundPicture);
		drawArena(g, world, nameWallPicture);
	}
	
	public static void drawBackground (Graphics2D g, String nameBackground) throws IOException{
		
		BufferedImage img = ImageIO.read(new File("images/" + nameBackground));	
		g.drawImage(img, null, Wall.WALL_SIZE, Wall.WALL_SIZE);
		
	}
	
	public static void drawWalls(int level){
		//add wall dif level
	}

}
