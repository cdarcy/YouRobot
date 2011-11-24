package fr.umlv.yourobot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.TexturedDrawAPI;
import fr.umlv.yourobot.elements.bonus.Bomb;
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
import fr.umlv.yourobot.physics.collisions.CollisionListener;
import fr.umlv.yourobot.physics.raycasts.AICallback;
import fr.umlv.yourobot.physics.raycasts.GameDetectionCallback;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.MapGenerator;
import fr.umlv.zen.KeyboardEvent;

public class RobotWorld  {

	private BufferedImage img;
	private World jboxWorld;
	private ArrayList<Element> elements;
	private ArrayList<Element> effects;
	private ArrayList<Robot> robots;
	private ArrayList<Bonus> bonuses;
	private ArrayList<Element> tmpelements;
	private ArrayList<AICallback> callbacks;
	private ArrayList<HumanRobot> players;
	private ArrayList<Wall> walls ;
	private ArrayList<Circle> IOMap ;
	private Object monitor = new Object();
	private TexturedDrawAPI api;
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	final String[] keysP1 = {"UP","LEFT","RIGHT","SPACE"};
	final String[] keysP2 = {"Z","Q","D","X"};
	final Vec2 startCoordsP1 = new Vec2(Wall.WALL_SIZE+30, HEIGHT-100);
	final Vec2 startCoordsP2 = new Vec2(Wall.WALL_SIZE+30, HEIGHT-200);
	public enum RobotGameMod{
		ONEPLAYER,
		TWOPLAYER
	}

	Body body;
	RobotGameMod mode;
	/**
	 * Create a new world simulation
	 * @param gravity gravity applied to the world
	 * @param doSleep boolean to set sleeping
	 */
	public RobotWorld() {
		jboxWorld = new World(new Vec2(0, 0), true);
		body = jboxWorld.createBody(new BodyDef());
		body.setUserData(this);
		mode = RobotGameMod.ONEPLAYER;
		jboxWorld.setContactListener(new CollisionListener(this));
		jboxWorld.setContinuousPhysics(true);
		robots = new ArrayList<>();
		elements = new ArrayList<>();
		effects = new ArrayList<>();
		bonuses = new ArrayList<>();
		tmpelements = new ArrayList<>();
		callbacks = new ArrayList<>();
		players = new ArrayList<>();
		walls = new ArrayList<>();
		IOMap = new ArrayList<>();
		if(api == null)
			api = new TexturedDrawAPI();
	}


	public void addElement(Element element, BodyType type, boolean createFixture){
		Body body = jboxWorld.createBody(element.getBodyDef());
		elements.add(element);
		element.setBody(body);
		body.setUserData(element);
		body.setType(type);
		if(createFixture)
			element.setFixture(body.createFixture(element.getFixtureDef()));
	}

	public void addRobot(Robot element) {
		callbacks.add(new AICallback(this, element));
		robots.add((ComputerRobot) element);
		addElement(element, BodyType.DYNAMIC, true);
	}	



	public void addPlayer(HumanRobot element) {
		players.add(element);
		addElement(element, BodyType.DYNAMIC, true);
	}	


	public void addWall(Wall element) {
		walls.add(element);
		addElement(element, BodyType.STATIC, true);
	}

	public void addBonus(Bonus b){
		addElement(b, BodyType.STATIC, true);
		bonuses.add(b);
	}
	
	public void addLureRobot(Robot lureRobot){
		robots.add(lureRobot);
		addElement(lureRobot, BodyType.STATIC, true);
	}
	
	public void delLureRobot(int index){
		robots.remove(index);
	}
	
	public BorderWall addBorder(int x, int y, String fileName) throws IOException {
		BorderWall element = new BorderWall(x, y, fileName);
		addElement(element, BodyType.STATIC, true);
		element.getBody().setAngularDamping(.0f);
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
					addBonus(new WoodBomb(x, y));
					break;
				case STONEBOMB:
					addBonus(new StoneBomb(x, y));
					break;
				case ICEBOMB:
					addBonus(new IceBomb(x, y));
					break;
				case SNAP:
					addBonus(new Snap(x, y));
					break;
				case LURE:
					addBonus(new Lure(x, y));
			}
			
			System.out.println(list.get(value));
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
	public void updateGame(Graphics2D g, KeyboardEvent event) throws IOException {
		synchronized (monitor){
			doControl(g, event);

			// Steps jbox2d physics world
			jboxWorld.step(1/30f, 15, 8);
			jboxWorld.clearForces();
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
	}

	private void drawBackground(Graphics2D g) {
		g.drawImage(img, null, Wall.WALL_SIZE-8, Wall.WALL_SIZE-8);
		
		RadialGradientPaint paint1 = new RadialGradientPaint(70, HEIGHT-100, 40, new float[]{.3f, 1f}, new Color[]{Color.BLUE, Color.BLUE});
		g.setPaint(paint1);
		g.fill(new Ellipse2D.Float(43, HEIGHT-100, 40, 40));
		RadialGradientPaint paint2 = new RadialGradientPaint(70, HEIGHT-150, 40, new float[]{.3f, 1f}, new Color[]{Color.BLUE, Color.BLUE});
		g.setPaint(paint2);
		g.fill(new Ellipse2D.Float(43, HEIGHT-150, 40, 40));
		RadialGradientPaint paint3 = new RadialGradientPaint(710, 70, 40, new float[]{.3f, 1f}, new Color[]{Color.GREEN, Color.GREEN});
		g.setPaint(paint3);
		g.fill(new Ellipse2D.Float(705, 43, 40, 40));
	}


	/**
	 * @param args
	 */


	public World getJBoxWorld() {
		return jboxWorld;
	}


	public void addCircle(Element circle){
		addElement(circle, BodyType.STATIC, false);
	}

	public void addCallback(AICallback callback){
		callbacks.add(callback);
	}	

	public void updateRaycasts() throws InterruptedException{
		final RobotWorld rw = (RobotWorld) this;
		for(final Robot e : robots){
				((ComputerRobot) e).run(rw);
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
		players.get(0).controlMenu(g, event, world);
	}

	public void drawBonuses(Graphics2D g) throws IOException {
		for(Bonus b : bonuses){
			if(b != null){
				b.draw(g,api);
			}
		}

	}
	public void draw(Graphics2D g) throws IOException {
		if(api==null)
			api = new TexturedDrawAPI();

		for(Element e : players){
			if(e != null){
				e.draw(g,api);
			}
		}
		for(Element e : tmpelements){
			if(e != null){
				e.draw(g,api);
			}
		}
		for(Element e : walls){
			if(e != null){
				e.draw(g,api);
			}
		}
		for(Element e : effects){
			if(e!=null){
				e.draw(g,api);
			}
		}
		for (Element e : IOMap){
			if(e!=null){
				e.draw(g,api);
			}
		}
		for(Element e : robots){
			if(e != null){
				e.draw(g,api);
			}
		}
		effects.clear();
		tmpelements.clear();
	}

	public void drawInterface(Graphics2D g) throws IOException {
		g.setColor(Color.BLACK);
		HumanRobot p1 = players.get(0);
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
		System.out.println(elem);
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


	public void init(Graphics2D g) {
		setMode(RobotGameMod.TWOPLAYER);

		ComputerRobot r1;
		ComputerRobot r2;
		ComputerRobot r3;
		HumanRobot e1;
		HumanRobot e2;

		// Defining ComputerRobots
		r1 = new ComputerRobot(500,300);
		r2 = new ComputerRobot(500,400);
		r3 = new ComputerRobot(500,500);

		// Defining HumanRobots
		e1 = new HumanRobot(this,"Camcam",keysP1,46, HEIGHT-97);
		e2 = new HumanRobot(this,"Camcam",keysP2,46, HEIGHT-147);
		addRobot(r1);
		addRobot(r2);
		addRobot(r3);

		addPlayer(e1);
		addPlayer(e2);
		// create map
		try {
			MapGenerator.mapRandom(this, g);
		} catch (IOException e) {
			e.printStackTrace();
		}
		putBonus();

		addBonus(new Snap(80, HEIGHT-150));
	}


	public ArrayList<Bonus> getBonusList() {
		return bonuses;
	}


	public void drawArena() {
		tmpelements.addAll(MapGenerator.getArena());
	}


	public void drawElements(ArrayList<Element> torepaint) {
		tmpelements.addAll(torepaint);		
	}



	public DrawAPI getApi() {
		return api;
	}


	public ArrayList<Wall> getWalls() {
		return walls;
	}


	public Object getMonitor() {
		return this.monitor;
	}


	public ArrayList<HumanRobot> getPlayers() {
		return players;
	}



	public ArrayList<Robot> getRobots() {
		return robots;
	}


}


