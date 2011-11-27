package fr.umlv.yourobot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.bonus.IceBomb;
import fr.umlv.yourobot.elements.bonus.Lure;
import fr.umlv.yourobot.elements.bonus.Snap;
import fr.umlv.yourobot.elements.bonus.StoneBomb;
import fr.umlv.yourobot.elements.bonus.WoodBomb;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.elements.walls.BorderWall;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.graphics.MenusDrawAPI;
import fr.umlv.yourobot.physics.collisions.CollisionListener;
import fr.umlv.yourobot.util.ElementType.ElementClass;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.KeyControllers;
import fr.umlv.yourobot.util.KeyControllers.RobotTextureMod;
import fr.umlv.yourobot.util.MapGenerator;
import fr.umlv.zen.KeyboardEvent;

public class RobotWorld  {

	private World jboxWorld;
	private GameDrawAPI api;
	private ArrayList<Element> players;
	private ArrayList<Element> all;
	private ArrayList<Element> toDestroy;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	final String[] keysP1 = {"UP","DOWN","LEFT","RIGHT","SPACE"};
	final String[] keysP2 = {"Z","S","Q","D","X"};
	final Vec2 startCoordsP1 = new Vec2(Wall.WALL_SIZE+30, HEIGHT-100);
	final Vec2 startCoordsP2 = new Vec2(Wall.WALL_SIZE+30, HEIGHT-200);

	private HashMap<ElementClass, ArrayList<Element>> map;

	public enum RobotGameMod{
		ONEPLAYER,
		TWOPLAYER
	}

	Body body;
	RobotGameMod mode;
	private int currentLevel;
	private boolean finished = false;
	private ArrayList<Element> walls;

	public RobotWorld(int level, RobotGameMod gameMod, RobotTextureMod graphicMod) {
		jboxWorld = new World(new Vec2(0, 0), true);
		mode = gameMod;
		api = new GameDrawAPI(this);
		body = jboxWorld.createBody(new BodyDef());
		body.setUserData(this);
		jboxWorld.setContactListener(new CollisionListener(this));
		jboxWorld.setContinuousPhysics(true);
		map = new HashMap<>();
		all = new ArrayList<>();
		players = new ArrayList<>();
		toDestroy = new ArrayList<>();
	}

	public void setGameFinished(){
		finished = true;
	}

	public void addElement(Element element, BodyType type, boolean createFixture){
		Body body = jboxWorld.createBody(element.getBodyDef());
		if(map.get(element.classElem()) == null){
			ArrayList<Element> l = new ArrayList<>();
			l.add(element);
			map.put(element.classElem(), l);
		}
		else map.get(element.classElem()).add(element);
		if(element.typeElem()==ElementType.PLAYER_ROBOT)
			players.add(element);
		all.add(element);
		element.setBody(body);
		body.setUserData(element);
		body.setType(type);
		if(createFixture)
			element.setFixture(body.createFixture(element.getFixtureDef()));
	}


	public Element addStaticElement(Element element){
		addElement(element, BodyType.STATIC, true);
		return element;
	}

	public Element addDynamicElement(Element element){
		addElement(element, BodyType.DYNAMIC, true);
		return element;
	}

	public void putBonus() {
		ArrayList<ElementType> list = new ArrayList<>();
		list.add(ElementType.SNAP);
		list.add(ElementType.WOODBOMB);
		list.add(ElementType.STONEBOMB);
		list.add(ElementType.ICEBOMB);
		list.add(ElementType.LURE);

		for (int i=0;i<5;i++){
			float x = MathUtils.randomFloat(100, WIDTH-100);
			float y = MathUtils.randomFloat(100, HEIGHT-100);			
			int value = Math.round(MathUtils.randomFloat(0,list.size()-1));
			switch (list.get(value)){
			case WOODBOMB:
				addStaticElement(new WoodBomb(x, y));
				break;
			case STONEBOMB:
				addStaticElement(new StoneBomb(x, y));
				break;
			case ICEBOMB:
				addStaticElement(new IceBomb(x, y));
				break;
			case SNAP:
				addStaticElement(new Snap(x, y));
				break;
			case LURE:
				addStaticElement(new Lure(x, y));
			}
		}
	}	

	/**
	 * Update the world
	 * 
	 * @param timeStep The amount of time to simulate
	 * @return 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public boolean updateGame(Graphics2D g, KeyboardEvent event) throws IOException, InterruptedException {
		if(event != null)
			doControl(g, event);
		// Steps jbox2d physics world
		jboxWorld.step(1/30f, 15, 8);

		if (Math.round(((HumanRobot) players.get(0)).getLife()) == 0){
			GameDrawAPI.drawGameOver(g);
			MenusDrawAPI.restartGame(g);
		}

		//MapGenerator background
		GameDrawAPI.drawBackground(g);
		// Draw bonuses before drawing other elements (robots, walls)
		// Draw elements of the ga		g.drawImage(img, null, Wall.WALL_SIZE-8, Wall.WALL_SIZE-8);

		GameDrawAPI.draw(g);

		// Destroy effects
		for (Element e : toDestroy){
			jboxWorld.destroyBody(e.getBody());
			all.remove(e);
			map.get(e.classElem()).remove(e);
		}
		// Draw Interface
		GameDrawAPI.drawInterface(g);

		return isGameFinished();
	}




	private boolean isGameFinished() {
		return finished;
	}


	/**
	 * @param args
	 */


	public World getJBoxWorld() {
		return jboxWorld;
	}

	public void updateRobots() throws InterruptedException{
		final RobotWorld rw = (RobotWorld) this;
		for(final Element e : getListByType(ElementType.COMPUTER_ROBOT)){
			((ComputerRobot) e).run(rw);
		}	
	}

	public void doControl(Graphics2D g, KeyboardEvent event){
		if (event == null)
			return;
		for(Element r : players){
			if(r != null){
				((HumanRobot) r).control(g, event);
			}
		}
	}

	public ArrayList<Element> getListByType(ElementType type) {
		ArrayList<Element> l = new ArrayList<>();
		for(Element e : map.get(type.getEClass()))
			if(e.typeElem()==type)
				l.add(e);
		return l;
	}


	public ArrayList<Element> getListByClass(ElementClass elclass) {
		return map.get(elclass);
	}


	public void removeEffects() {
		toDestroy.addAll(getListByClass(ElementClass.EFFECT));
	}



	public void setMode(RobotGameMod mode) {
		this.mode = mode;
	}

	public RobotGameMod getMode(){
		return this.mode;
	}


	public void removeElement(Element e){
		if(map.get(e.classElem()).contains(e)){
			map.get(e.classElem()).remove(e);
			all.remove(e);
			toDestroy.add(e);
		}
		if(players.contains(e))
			players.remove(e);
	}

	public synchronized Element getElement(ElementClass classelem, Vec2 pos){
		for(Element e : map.get(classelem))
			if(e.getBody().getPosition().equals(pos))
				return e;
		return null;
	}


	public HumanRobot getPlayerFromCurrentPosition(Vec2 pos) {
		for(Element e : players)
			if(e.getBody().getPosition().equals(pos))
				return (HumanRobot) e;
		return null;
	}

	public List<Element> getElementList(ElementClass group){
		return map.get(group);
	}



	public void init(Graphics2D g) {
		//setMode(RobotGameMod.TWOPLAYER);

		ComputerRobot r1;
		ComputerRobot r2;
		ComputerRobot r3;
		HumanRobot e1 = null;
		HumanRobot e2 = null;

		// Defining ComputerRobots
		r1 = new ComputerRobot(500,300);
		r2 = new ComputerRobot(500,400);
		r3 = new ComputerRobot(500,500);

		addDynamicElement(r1);
		addDynamicElement(r2);
		addDynamicElement(r3);

		RadialGradientPaint paint1 = new RadialGradientPaint(70, HEIGHT-100, 40, new float[]{.3f, 1f}, new Color[]{Color.BLUE, Color.WHITE});
		RadialGradientPaint paint2 = new RadialGradientPaint(710, 70, 40, new float[]{.3f, 1f}, new Color[]{Color.GREEN, Color.WHITE});
		Circle c1 = new Circle(paint1, 40, 70, HEIGHT-100, ElementType.START_CIRCLE);
		Circle c3 = new Circle(paint2, 40, 710, 70, ElementType.END_CIRCLE);
		// Defining HumanRobots
		e1 = new HumanRobot(this,"Camcam",46, HEIGHT-97);
		e1.setController(KeyControllers.getGameController(this, e1, keysP1));
		addDynamicElement(e1);
		addStaticElement(c1);
		addStaticElement(c3);

		if(mode == RobotGameMod.TWOPLAYER){
			e2 = new HumanRobot(this,"Loulou",46, HEIGHT-147);
			e2.setController(KeyControllers.getGameController(this, e2, keysP2));
			Circle c2 = new Circle(paint1, 40, 70, HEIGHT-150, ElementType.START_CIRCLE);
			addStaticElement(c2);
			addDynamicElement(e2);
		}

		// create map
		try {
			MapGenerator.mapRandom(currentLevel, this, g);
		} catch (IOException e) {
			e.printStackTrace();
		}
		putBonus();
		addStaticElement(new Snap(80, HEIGHT-150));

		try {
			updateRobots();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(Element e : map.get(ElementClass.BLOCK)){
			try {
				e.draw(g, api);
			} catch (IOException e3) {
				e3.printStackTrace();
			}
		}
	}


	public void drawArena() {
		map.get(ElementClass.EFFECT);
	}


	public void drawElements(ArrayList<Element> torepaint) {
		map.get(torepaint.get(0).classElem()).addAll(torepaint);		
	}



	public GameDrawAPI getApi() {
		return api;
	}

	public void setApi(GameDrawAPI api) {
		this.api = api;
	}

	public ArrayList<Element> getAll() {
		return all;
	}

	public ArrayList<Element> getPlayers() {
		return players;
	}

	public ArrayList<Element> getWalls() {
		if(walls == null)
			walls = map.get(ElementClass.WALL);
		return walls;
	}



}


