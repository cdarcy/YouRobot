package fr.umlv.yourobot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
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
	private ArrayList<Element> elements;
	private ArrayList<Robot> robotMap;
	private ArrayList<Element> tmpelements;
	private ArrayList<AICallback> callbacks;
	private ArrayList<HumanRobot> players;
	
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
		elements = new ArrayList<>();
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
		elements.add(element);
		callbacks.add(new AICallback(this, element));
	}	
	


	public void addPlayer(HumanRobot p) {
		players.add(p);
		elements.add(p);
	}	

	public WoodWall addWall(int x, int y) {
		WoodWall element = new WoodWall(this, x, y);
		elements.add(element);
		return element;
	}	

	public Bonus putBonus() {
		float x = MathUtils.randomFloat(100, WIDTH-100);
		float y = MathUtils.randomFloat(100, HEIGHT-100);
		System.out.println(x + "," +y);
		final Bomb element = new Bomb(this, x, y);
		elements.add(element);
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
				ComputerRobot robot = (ComputerRobot) getRobotFromPosition(a.getOrigin());
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
		for(Element e : elements){
			if(e != null){
				e.draw(g);
			}
		}
		elements.removeAll(tmpelements);
		tmpelements.clear();
	}

	public void drawInterface(Graphics2D g) throws IOException {
		g.setColor(Color.WHITE);
		Robot p1 = players.get(0);
		int p1Col = 10;
		g.drawString("Player 1 : " + p1.getpName() + " - " + p1.getLife()+"%", p1Col, 15);
		
	}

	public void setMode(RobotGameMod mode) {
		this.mode = mode;
	}


	public synchronized void removeElement(Vec2 pos) {
		if(getElement(pos) != null){
			jboxWorld.destroyBody(getElement(pos).getBody());
			elements.remove(pos);	
		}
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
	
	
	public ComputerRobot getRobotFromPosition(Vec2 pos) {
		for(Element e : elements)
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
