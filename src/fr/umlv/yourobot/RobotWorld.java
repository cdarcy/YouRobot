package fr.umlv.yourobot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bomb;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.elements.walls.BorderWall;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.physics.collisions.CollisionListener;
import fr.umlv.yourobot.physics.raycasts.AICallback;
import fr.umlv.yourobot.util.MapGenerator;
import fr.umlv.yourobot.welcome.LoadingGame;
import fr.umlv.zen.KeyboardEvent;

public class RobotWorld  {

	private BufferedImage img;
	private World jboxWorld;
	private ArrayList<Element> elements;
	private ArrayList<Element> effects;
	private ArrayList<Robot> robotMap;
	private ArrayList<Bonus> bonuses;
	private ArrayList<Element> tmpelements;
	private ArrayList<RayCastCallback> callbacks;
	private ArrayList<HumanRobot> players;
	private ArrayList<Wall> walls ;
	private ArrayList<Circle> IOMap ;
	
	public static int WIDTH = 800;
	public static int HEIGHT = 600;

	public enum RobotGameMod{
		ONEPLAYER,
		TWOPLAYER
	}

	RobotGameMod mode;
	/**
	 * Create a new world simulation
	 * @param gravity gravity applied to the world
	 * @param doSleep boolean to set sleeping
	 */
	public RobotWorld() {
		jboxWorld = new World(new Vec2(0, 0), true);
		jboxWorld.createBody(new BodyDef());		
		jboxWorld.setContactListener(new CollisionListener(this));
		robotMap = new ArrayList<>();
		elements = new ArrayList<>();
		effects = new ArrayList<>();
		bonuses = new ArrayList<>();
		tmpelements = new ArrayList<>();
		callbacks = new ArrayList<>();
		players = new ArrayList<>();
		walls = new ArrayList<>();
		IOMap = new ArrayList<>();
	}


	/**
	 * Add a body to the world
	 * 
	 * @param body The body to be added to the world
	 */

	public void addRobot(Robot element) {
		robotMap.add(element);
		elements.add(element);
		callbacks.add(new AICallback(this, element));
		
	}	
 


	public void addPlayer(HumanRobot p) {
		players.add(p);
		elements.add(p);
	}	


	public void addWall(Wall wall) {
		elements.add(wall);
		walls.add(wall);
	}
	
	
	public BorderWall addBorder(int x, int y, String fileName) throws IOException {
		BorderWall element = new BorderWall(this, x, y, fileName);
		elements.add(element);
		return element;
	}	

	public Bonus putBonus() {
		float x = MathUtils.randomFloat(100, WIDTH-100);
		float y = MathUtils.randomFloat(100, HEIGHT-100);
		final Bomb element = new Bomb(this, x, y);
		bonuses.add(element);
		elements.add(element);
		return element;
	}	



	/**
	 * Get the number of bodies in the world
	 * 
	 * @return The number of bodies in the world
	 */
	public int getBodyCount() {
		return 0;//bodies.size();
	}

	/**
	 * Update the world
	 * 
	 * @param timeStep The amount of time to simulate
	 * @throws IOException 
	 */
	public void updateGame(Graphics2D g) throws IOException {
		// Steps jbox2d physics world
		jboxWorld.step(1/10f, 15, 8);
		//MapGenerator background
		drawBackground(g);
		//g.fillRect(Wall.WALL_SIZE, Wall.WALL_SIZE, WIDTH-(Wall.WALL_SIZE*2), HEIGHT-(Wall.WALL_SIZE*2));
		// Draw bonuses before drawing other elements (robots, walls)
		drawBonuses(g);
		//Draw wall
		// Draw elements of the game
		draw(g);
		// Draw Interface
		drawInterface(g);
	}
	private void drawBackground(Graphics2D g) {
		g.drawImage(img, null, Wall.WALL_SIZE, Wall.WALL_SIZE);
	}


	/**
	 * @param args
	 */


	public World getJBoxWorld() {
		return jboxWorld;
	}


	public void addCallback(RayCastCallback callback){
		callbacks.add(callback);
	}	

	public  void updateRaycasts() throws InterruptedException{
		for (HumanRobot p : players){
			for (RayCastCallback a : callbacks){
				ComputerRobot robot = (ComputerRobot) getElementFromPosition(((AICallback) a).getOrigin());
				float quarter_diagonal = (float) (Math.sqrt((WIDTH*WIDTH)+(HEIGHT*HEIGHT))/4);
				float x = (robot.getX()-p.getX())*(robot.getX()-p.getX());
				float y = (robot.getY()-p.getY())*(robot.getY()-p.getY());
				float distance = (float) Math.sqrt(x+y);
				if(distance<=quarter_diagonal)
					jboxWorld.raycast(a, ((AICallback) a).getOrigin(), p.getBody().getPosition());
				Thread.sleep(1000);
			}
		}
	}

	public void doControl(Graphics2D g, KeyboardEvent event){
		if (event == null)
			return;
		for(HumanRobot r : players){
			if(r != null){
				r.control(g, event);
			}
		}
	}
	
	public void doControlMenu(Graphics2D g, KeyboardEvent event, RobotWorld world) throws IOException{
		if (event == null)
			return;
		HumanRobot r;
		players.get(0).controlMenu(g, event, world);
	}

	public void drawBonuses(Graphics2D g) throws IOException {
		for(Bonus b : bonuses){
			if(b != null){
				b.draw(g);
			}
		}

	}
	public void draw(Graphics2D g) throws IOException {
		for(Element e : players){
			if(e != null){
				e.draw(g);
			}
		}
		for(Element e : tmpelements){
			if(e != null){
				e.draw(g);
			}
		}
		for(Element e : effects){
			if(e != null){
				e.draw(g);
			}
		}
		for(Element e : walls){
			if(e != null){
				e.draw(g);
			}
		}
		for (Element e : IOMap){
			if(e!=null){
				e.draw(g);
			}
		}
		for(Element e : robotMap){
			if(e != null){
				e.draw(g);
			}
		}
		effects.clear();
		tmpelements.clear();
	}

	public void drawInterface(Graphics2D g) throws IOException {
		g.setColor(Color.BLACK);
		Font fonte = new Font(Font.SERIF,Font.BOLD, 15);
		g.setFont(fonte);
		Robot p1 = players.get(0);
		int p1Col = 10;
		g.drawString("Player 1 : " + p1.getpName() + " - " + p1.getLife()+"%", p1Col, 15);
	}

	public void setMode(RobotGameMod mode) {
		this.mode = mode;
	}
	
	public RobotGameMod getMode(){
		return this.mode;
	}


	public void removeBonus(Vec2 pos) {
		Element elem = getBonus(pos);
		if(elem != null){
			bonuses.remove(elem);
			elements.remove(elem);
			jboxWorld.destroyBody(elem.getBody());
		}
	}

	private Element getBonus(Vec2 pos) {
		for (Bonus b : bonuses)
			if(b.getBody().getPosition().equals(pos))
				return b;
		return null;
	}



	public synchronized Element getElement(Vec2 pos){
		for(Element e : elements)
			if(e.getBody().getPosition().equals(pos))
				return e;
		return null;
	}

	public void drawElement(Element element) {
		tmpelements.add(element);
	}
	
	public void drawIOMap(Circle circle) {
		IOMap.add(circle);
	}

	public void drawEffect(Element element){
		effects.add(element);
	}

	public HumanRobot getPlayerFromCurrentPosition(Vec2 pos) {
		for(Element e : players)
			if(e.getBody().getPosition().equals(pos))
				return (HumanRobot) e;
		return null;
	}



	public Element getElementFromPosition(Vec2 pos) {
		for(Element e : elements){
			if(e.getBody().getPosition().equals(pos))
				return e;
		}
		return null;
	}
	public void setBackground(String nameBackgroundPicture) throws IOException {
		img = ImageIO.read(new File("images/" + nameBackgroundPicture));	
	}
}
