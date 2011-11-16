package fr.umlv.yourobot.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;
import org.jbox2d.dynamics.World;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.walls.BorderWall;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.elements.walls.WoodWall;

public class MapGenerator {
	public static int value;
	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	
	public static void drawArena(Graphics2D g, RobotWorld world, String nameWallPicture) throws IOException{
		for (int i = 0; i < WIDTH/Wall.WALL_SIZE; i++){
			if(i<HEIGHT){
				// GAUCHE
				world.addBorder(0, i*Wall.WALL_SIZE, nameWallPicture).draw(g);
				// DROITE
				//g.rotate(Math.PI, WIDTH-(Wall.WALL_SIZE/2), i*(Wall.WALL_SIZE/2));
				world.addBorder(WIDTH-Wall.WALL_SIZE, i*Wall.WALL_SIZE, nameWallPicture).draw(g);
			}
			// HAUT
			world.addBorder((i*Wall.WALL_SIZE)+Wall.WALL_SIZE, 0, nameWallPicture).draw(g);
			// BAS
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
