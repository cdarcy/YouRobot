package fr.umlv.yourobot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bomb;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.elements.walls.WoodWall;
import fr.umlv.yourobot.physics.collisions.CollisionListener;
import fr.umlv.yourobot.physics.raycasts.AICallback;
import fr.umlv.zen.KeyboardEvent;

public class RobotWorld  {
	
	private World jboxWorld;
	private HashMap<Vec2, Element> elements;
	private ArrayList<Robot> robotMap;
	private ArrayList<Element> tmpelements;
	private ArrayList<AICallback> callbacks;
	private ArrayList<HumanRobot> players;
	private BufferedImage img;
	public int WIDTH = 800;
	public int HEIGHT = 600;

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
		elements = new HashMap<>();
		tmpelements = new ArrayList<>();
		callbacks = new ArrayList<>();
		players = new ArrayList<>();
	}


	/**
	 * Add a body to the world
	 * 
	 * @param body The body to be added to the world
	 */

	public void addRobot(Robot element) {
		robotMap.add(element);
		elements.put(element.getBody().getPosition(), element);
		callbacks.add(new AICallback(this, element));
	}	
	


	public void addPlayer(HumanRobot p) {
		players.add(p);
		elements.put(p.getBody().getPosition(), p);
	}	

	public WoodWall addWall(int x, int y) {
		WoodWall element = new WoodWall(this, x, y);
		elements.put(element.getBody().getPosition(), element);
		return element;
	}	

	public Bonus putBonus() {
		float x = MathUtils.randomFloat(Wall.WALL_SIZE, WIDTH-Wall.WALL_SIZE);
		float y = MathUtils.randomFloat(Wall.WALL_SIZE, HEIGHT-Wall.WALL_SIZE);
		final Bonus element = new Bomb(this, x, y);
		elements.put(element.getBody().getPosition(), element);
		return element;
	}	


	public void addArena(Graphics2D g) throws IOException{
		for (int i = 0; i < WIDTH/Wall.WALL_SIZE; i++){
			if(i<HEIGHT){
				// GAUCHE
				addWall(0, i*Wall.WALL_SIZE).draw(g);
				// DROITE
				addWall(WIDTH-Wall.WALL_SIZE, i*Wall.WALL_SIZE).draw(g);
			}
			// HAUT
			addWall((i*Wall.WALL_SIZE)+Wall.WALL_SIZE, 0).draw(g);
			// BAS
			addWall((i*Wall.WALL_SIZE)+Wall.WALL_SIZE, HEIGHT-Wall.WALL_SIZE).draw(g);

		}
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
		jboxWorld.clearForces();
		// Draw background
		drowBackgroung(g);
		// Draw elements of the game
		draw(g);
		// Draw Interface
		drawInterface(g);
		
	}
	/**
	 * @param args
	 */


	public World getJBoxWorld() {
		return jboxWorld;
	}


	public void addCallback(Robot robot){
		callbacks.add(new AICallback(this, robot));
	}	
	
	public void updateRaycasts(){
		for (HumanRobot p : players){
			for (AICallback a : callbacks){
				ComputerRobot robot = (ComputerRobot) getRobotFromCurrentPosition(a.getOrigin());
				float quarter_diagonal = (float) (Math.sqrt((WIDTH*WIDTH)+(HEIGHT*HEIGHT))/4);
				float x = (robot.getX()-p.getX())*(robot.getX()-p.getX());
				float y = (robot.getY()-p.getY())*(robot.getY()-p.getY());
				float distance = (float) Math.sqrt(x+y);

				if(distance<=quarter_diagonal)
					jboxWorld.raycast(a, a.getOrigin(), p.getBody().getPosition());
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
	public void draw(Graphics2D g) throws IOException {
		for(Element e : elements.values()){
			if(e != null){
				e.draw(g);
			}
		}
	}

	public void drowBackgroung (Graphics2D g) throws IOException{
		if(img == null)
			img = ImageIO.read(new File("images/background_1.png"));	
		g.drawImage(img, null, 0, 0);
	}
	
	public void drawInterface(Graphics2D g) throws IOException {
		g.setColor(Color.WHITE);
		Robot p1 = players.get(0);
		int p1Col = 10;
		g.drawString("Player 1 : " + p1.getpName() + " - " + p1.getLife()+"%", p1Col, 15);
		p1.drawBonusList(g, p1Col, 45);
		
	}

	public void setMode(RobotGameMod mode) {
		this.mode = mode;
	}


	public void removeElement(Vec2 vec2) {
		if(elements.get(vec2) != null){
			jboxWorld.destroyBody(elements.get(vec2).getBody());
			elements.remove(vec2);	
		}
	}
	
	public synchronized Element getElement(Vec2 pos){
		return elements.get(pos);
	}

	public void drawElement(Element element) {
		elements.put(element.getBody().getPosition(), element);
		tmpelements.add(element);
	}

	public ComputerRobot getRobotFromCurrentPosition(Vec2 pos) {
		for(Element e : elements.values())
			if(e.getBody().getPosition().equals(pos))
				return (ComputerRobot) e;
		return null;
	}

	public HumanRobot getPlayerFromCurrentPosition(Vec2 pos) {
		for(Element e : players)
			if(e.getBody().getPosition().equals(pos))
				return (HumanRobot) e;
		return null;
	}



}
