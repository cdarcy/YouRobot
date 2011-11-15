package fr.umlv.yourobot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bomb;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.physics.collisions.AICollisionListener;
import fr.umlv.yourobot.physics.raycasts.AICallback;
import fr.umlv.zen.KeyboardEvent;

public class RobotWorld  {
	private World jboxWorld;
	private HashMap<Vec2, Element> elements;
	private ArrayList<Robot> robotMap;
	private ArrayList<Element> tmpelements;
	private ArrayList<AICallback> callbacks;
	private ArrayList<HumanRobot> players;
	public int WIDTH = 800;
	public int HEIGHT = 600;

	public enum RobotGameMod{
		ONEPLAYER,
		TWOPLAYER,
		FOURPLAYER
		
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

	public Wall addWall(int x, int y) {
		Wall element = new Wall(this, x, y);
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


	public void addArena(Graphics2D g){
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
			Vec2 pos = p.getBody().getPosition();
			for (AICallback a : callbacks){
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


	public HumanRobot getPlayer(Vec2 pos) {
		for(Element e : players)
			if(e.getBody().getPosition().equals(pos))
				return (HumanRobot) e;
		return null;
	}



}
