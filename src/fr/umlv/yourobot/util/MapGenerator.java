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

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.walls.BarWall;
import fr.umlv.yourobot.elements.walls.BorderWall;
import fr.umlv.yourobot.elements.walls.IceWall;
import fr.umlv.yourobot.elements.walls.StoneWall;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.elements.walls.WoodWall;
import fr.umlv.yourobot.graphics.GameDrawAPI;


/**
 * @code {@link MapGenerator}
 * Generator of random maps
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 */
public class MapGenerator {

	private static int value;
	private static Color color;
	private static RobotGame world;
	private static BufferedImage img;
	private static ArrayList<BorderWall> arena;
	public static ArrayList<Vec2> allStaticElement = new ArrayList<>();
	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	public static String nameWallPicture;


	/**
	 * Method that adds an arena all around the map
	 * Depends on value to choose the style applied to the walls
	 * @param g
	 * @param world
	 * @param nameWallPicture
	 * @throws IOException
	 */
	public static void createArena(Graphics2D g, RobotGame world, String nameWallPicture) throws IOException{
		arena = new ArrayList<>();
		if (value == 0)	color = new Color(0, 0, 100); //BLUE
		else if (value == 1)	color = new Color(100, 0, 0); //RED
		else if (value == 2)	color = new Color(0, 100, 0); //GREEN
		else if (value == 3)	color = new Color(255, 91, 0); //ORANGE
		for (int i = 0; i < WIDTH/Wall.WALL_SIZE; i++){
			if(i<HEIGHT){
				// GAUCHE
				Element element = (Element) world.addStaticElement(new BorderWall(0, i*Wall.WALL_SIZE, nameWallPicture));
				arena.add((BorderWall) element);

				// DROITE
				element = (Element) world.addStaticElement(new BorderWall(WIDTH-(Wall.WALL_SIZE-10), i*Wall.WALL_SIZE, nameWallPicture));
				arena.add((BorderWall) element);
			}
			// HAUT
			Element element = (Element) world.addStaticElement(new BorderWall((i*Wall.WALL_SIZE)+Wall.WALL_SIZE, 0, nameWallPicture));
			arena.add((BorderWall) element);
			// BAS
			element = (Element) world.addStaticElement(new BorderWall((i*Wall.WALL_SIZE)+Wall.WALL_SIZE, HEIGHT-(Wall.WALL_SIZE-10), nameWallPicture));
			arena.add((BorderWall) element);

		}

	}

	/**
	 * Static method that generates a new map depending on the current level of the game
	 * Initializes style, background, arena and walls
	 * @param level
	 * @param w
	 * @param g
	 * @throws IOException
	 */
	public static void mapRandom (int level, RobotGame w, Graphics2D g) throws IOException{
		new MapStyle();
		value =  (int) MathUtils.randomFloat(0, 4);
		world = w;
		String nameBackgroundPicture = MapStyle.background.get(value);
		nameWallPicture = MapStyle.wall.get(value);
		GameDrawAPI.setBackground(nameBackgroundPicture);
		createArena(g, world, nameWallPicture);
		drawArena(g);
		GameDrawAPI.drawInterface(g);
		setWalls(g, RobotGame.getCurrentLevel()+20);
	}

	/**
	 * Static method used to draw the arena
	 * @param g
	 */
	public static void drawArena(Graphics2D g){
		for(int i=0;i<arena.size();i++)
			try {
				g.setColor(color);
				arena.get(i).draw(g, world.getApi());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Static method to draw the background on the screen
	 * @param g
	 * @param nameBackground
	 * @throws IOException
	 */
	public static void drawBackground (Graphics2D g, String nameBackground) throws IOException{
		if(img == null)
			img = ImageIO.read(new File("images/" + nameBackground));	
		g.drawImage(img, null, Wall.WALL_SIZE, Wall.WALL_SIZE);
	}

	/**
	 * Methods adding a certain number of walls to the world depending on the game level
	 * @param g
	 * @param level
	 * @throws IOException
	 */
	public static void setWalls(Graphics2D g, int level) throws IOException{
		int wallNumber = level + 10;

		for (int i=50 ; i<150 ; i++){
			for (int j=HEIGHT-190 ; j<HEIGHT-50 ; j++){
				allStaticElement.add(new Vec2(i, j));
			}
		}

		for (int i=WIDTH-150 ; i<WIDTH-50 ; i++){
			for (int j=0 ; j<150 ; j++){
				allStaticElement.add(new Vec2(i, j));
			}
		}

		for (int i=0 ; i<wallNumber ; i++){
			//give a position in the map between the border
			//the map is represented like a matrix
			int posX = Wall.WALL_SIZE + Wall.WALL_SIZE * (int) MathUtils.randomFloat(0, ((WIDTH)/Wall.WALL_SIZE)-2);
			int posY = Wall.WALL_SIZE + Wall.WALL_SIZE * (int) MathUtils.randomFloat(0, ((HEIGHT)/Wall.WALL_SIZE)-2);
			Vec2 vec2 = new Vec2(posX, posY);

			if(!allStaticElement.contains(vec2)){
				allStaticElement.add(vec2);
				int randomNumber = (int) MathUtils.randomFloat(0, 4);
				switch (randomNumber){
				case (0) : 	world.addStaticElement(new WoodWall(posX, posY));
				break;
				case (1) : 	world.addStaticElement(new StoneWall(posX, posY));
				break;
				case (2) : 	world.addStaticElement(new IceWall(posX, posY));		
				break;
				case (3) : 	world.addStaticElement(new BarWall(posX, posY));		
				break;
				}
			}
			else
				i--;
		}
	}

	public static void addVecPos (Vec2 vec){
		allStaticElement.add(vec);
	}

	public static ArrayList<Vec2> getAllStaticElement(){
		return allStaticElement;
	}


	/**
	 * @code {@link MapStyle}
	 * Defines the styles of maps being used during the game.
	 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
	 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
	 */
	private static class MapStyle {
		public static ArrayList<String> background = new ArrayList<>();
		public static ArrayList<String> wall = new ArrayList<>();

		public MapStyle() {
			background.add("background_1.png");
			background.add("background_2.png");
			background.add("background_3.png");
			background.add("background_4.png");

			wall.add("wall_1.png");
			wall.add("wall_2.png");
			wall.add("wall_3.png");
			wall.add("wall_4.png");
		}
	}
}

