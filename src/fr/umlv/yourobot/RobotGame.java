package fr.umlv.yourobot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.io.IOException;
import java.util.ArrayList;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.IceBomb;
import fr.umlv.yourobot.elements.bonus.Lure;
import fr.umlv.yourobot.elements.bonus.Snap;
import fr.umlv.yourobot.elements.bonus.StoneBomb;
import fr.umlv.yourobot.elements.bonus.WoodBomb;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.elements.walls.Wall;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.physics.collisions.CollisionListener;
import fr.umlv.yourobot.physics.raycasts.ElementDetectionCallback;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.ElementType.ElementClass;
import fr.umlv.yourobot.util.KeyControllers;
import fr.umlv.yourobot.util.MapGenerator;
import fr.umlv.zen.KeyboardEvent;

/**
 * @code {@link RobotGame}
 * Defines all methods to manage a game
 * Include physics (jbox2d objects) and graphics management
 * A new game is instanciated for each level
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sébastien <sbaudran@etudiant.univ-mlv.fr>

 */
public class RobotGame{

	private World jboxWorld;
	private GameDrawAPI api;
	
	// Lists to store elements
	private ArrayList<Element> players;
	private ArrayList<Element> toDestroy;
	private ArrayList<Element> bonuses;
	private ArrayList<Element> walls;
	private ArrayList<Element> robots;
	private ArrayList<Element> lures;
	private ArrayList<Element> toDraw;
	private ArrayList<Element> circles;
	
	private static RobotGameMod mode;
	private static int currentLevel;
	private boolean finished = false;
	
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	final String[] keysP1 = {"UP","DOWN","LEFT","RIGHT","SPACE"};
	final String[] keysP2 = {"Z","S","Q","D","X"};
	final Vec2 startCoordsP1 = new Vec2(Wall.WALL_SIZE+30, HEIGHT-100);
	final Vec2 startCoordsP2 = new Vec2(Wall.WALL_SIZE+30, HEIGHT-200);

	/**
	 * Public enum defining game modes
	 */
	public enum RobotGameMod{
		ONEPLAYER,
		TWOPLAYER
	}

	/**
	 * Public enum defining graphic modes
	 */
	public enum RobotTextureMod{
		TEXTURE,
		GRAPHIC
	}
	
	/**
	 * Public enum defining game states
	 */
	public enum StateGame{
		INTERRUPTED,
		PLAYERDIED, 
		WINLEVEL, 
		PROCESSING;
	}
	/**
	 * Main constructor
	 * Initializes lists, world and modes
	 * @param level
	 * @param gameMod
	 * @param graphicMod
	 */
	Body body;

	public RobotGame(int level, RobotGameMod gameMod, RobotTextureMod graphicMod) {
		jboxWorld = new World(new Vec2(0, 0), true);
		mode = gameMod;
		walls = new ArrayList<>();
		circles = new ArrayList<>();
		bonuses = new ArrayList<>();
		players = new ArrayList<>();
		robots = new ArrayList<>();
		lures = new ArrayList<>();
		toDestroy = new ArrayList<>();

		toDraw = new ArrayList<>();
	}

	/**
	 * Method to set the game finished
	 */
	public void setGameFinished(){
		finished = true;
	}

	/**
	 * Returns the current level of the game
	 * @return the current level
	 */
	public static int getCurrentLevel(){
		return currentLevel;
	}

	/**
	 * Method used to add elements in the game (all types)
	 * Adds the element in the right list, creates its body and its fixture (if asked)
	 * @param element to add
	 * @param type BodyType of the element
	 * @param createFixture boolean specifying if a fixture have to be created 
	 */
	public void addElement(Element element, BodyType type, boolean createFixture){
		Body body = jboxWorld.createBody(element.getBodyDef());
		body.setLinearDamping(.5f);
		if(element.typeElem()==ElementType.PLAYER_ROBOT)
			players.add(element);
		if(element.typeElem()==ElementType.COMPUTER_ROBOT)
			robots.add(element);
		if(element.typeElem()==ElementType.LURE_ROBOT)
			lures.add(element);
		if(element.classElem()==ElementClass.WALL)
			walls.add(element);
		if(element.classElem()==ElementClass.BONUS)
			bonuses.add(element);
		if(element.classElem()==ElementClass.CIRCLE){
			circles.add(element);
		}
		element.setBody(body);
		body.setUserData(element);
		body.setType(type);
		if(createFixture)
			element.setFixture(body.createFixture(element.getFixtureDef()));
	}

	/**
	 * Adds an element with a STATIC body
	 * @param element
	 * @return the element been added
	 */
	public Element addStaticElement(Element element){
		addElement(element, BodyType.STATIC, true);
		return element;
	}


	/**
	 * Adds an element with a DYNAMIC body
	 * @param element
	 * @return the element been added
	 */
	public Element addDynamicElement(Element element){
		addElement(element, BodyType.DYNAMIC, true);
		return element;
	}

	/**
	 * Custom method to place a random number of bonuses on the map
	 * Instances bonus randomly. Detects of a body is already placed in the random position.
	 * If any, regenerates new coordinates. 
	 */
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

			ElementDetectionCallback callback = new ElementDetectionCallback();
			Vec2 lower = new Vec2(x - Wall.WALL_SIZE/2, y - Wall.WALL_SIZE/2);
			Vec2 upper = new Vec2(x + Wall.WALL_SIZE/2, y + Wall.WALL_SIZE/2);
			jboxWorld.queryAABB(callback, new AABB(lower, upper));
			if(callback.isDetected()){
				i--;
				continue;
			}

			if (!MapGenerator.getAllStaticElement().contains(new Vec2(x, y))){
				MapGenerator.addVecPos(new Vec2(x, y));
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
			else
				i--;
		}
	}	

	/**
	 * Main method called in the game loop to update physics and graphics
	 * @param g
	 * @param event
	 * @return StateGame the current state of game
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public StateGame updateGame(Graphics2D g, KeyboardEvent event) throws IOException, InterruptedException {
		if(event != null)
			doControl(g, event);

		jboxWorld.step(1/30f, 15, 8);

		GameDrawAPI.drawElements(this, g);
		destroyEffects();
		updateRobots();

		
		return currentStateGame();
	}


	/**
	 * Returns the current state game to know what to do after each game loop
	 * If the game is running, returns PROCESSpublicING
	 * If the player(s) died, returns PLAYERDIED
	 * If the level is finished, returns WINLEVEL
	 * @return StateGame the state calculated from current game
	 */
	private StateGame currentStateGame(){
		if(mode == RobotGameMod.ONEPLAYER)
			if (Math.round(((HumanRobot) players.get(0)).getLife()) == 0){
				return StateGame.PLAYERDIED;
			}
			else
				if (Math.round(((HumanRobot) players.get(0)).getLife()) == 0 &&
				Math.round(((HumanRobot) players.get(1)).getLife()) == 0){
					return StateGame.PLAYERDIED;
				}
		if(finished)
			return StateGame.WINLEVEL;
		return StateGame.PROCESSING;
	}

	/**
	 * Method which update the state of AI robots
	 * @throws InterruptedException
	 */
	public void updateRobots() throws InterruptedException{
		final RobotGame rw = (RobotGame) this;
		for(final Element e : robots){
			((Robot) e).run(rw);
		}
		for(final Element e : players){
			((Robot) e).run(rw);
		}
	}

	/**
	 * Method used to control events for each player at each game loop
	 * @param g
	 * @param event
	 */
	public void doControl(Graphics2D g, KeyboardEvent event){
		if (event == null)
			return;
		for(Element r : players){
			if(r != null){
				((HumanRobot) r).control(g, event);
			}
		}
	}

	/**
	 * Procedure that destroys elements of toDestroy array in the game
	 * Destroys fixture and body and clears toDestroy array
	 */
	public void destroyEffects(){
		for (Element e : toDestroy){
			e.getBody().destroyFixture(e.getFixture());
			jboxWorld.destroyBody(e.getBody());
		}
		toDestroy.clear();
	}

	/**
	 * Accessor for game mode attribute
	 * @return the game mode attribute
	 */
	public static void setMode(RobotGameMod mode) {
		RobotGame.mode = mode;
	}

	public RobotGameMod getMode(){
		return RobotGame.mode;
	}


	/**
	 * Method that removes an element from the game
	 * Destroy body and removes from its list
	 * @param e
	 */
	public void removeElement(Element e){
		jboxWorld.destroyBody(e.getBody());
		toDestroy.addAll(toDraw);
		toDraw.clear();
		if(bonuses.contains(e))
			bonuses.remove(e);
		if(lures.contains(e))
			lures.remove(e);
		if(players.contains(e))
			players.remove(e);
		if(robots.contains(e))
			robots.remove(e);
		if(toDraw.contains(e))
			toDraw.remove(e);
	}


	/**
	 * Init method of the game. Initializes the world at each new game
	 * Defines elements (robots, bonus, circles...) and generates a random map
	 * @param g
	 */
	public void init(Graphics2D g) {
		api = new GameDrawAPI(this);
		jboxWorld.setContactListener(new CollisionListener(this));
		
		addBots(currentLevel);
		addCircles();
		addPlayers();
	
		// create map
		try {
			MapGenerator.mapRandom(currentLevel, this, g);
		} catch (IOException e) {
			e.printStackTrace();
		}
		putBonus();
	}

	/**
	 * Method to add elements to draw temporarly
	 * @param torepaint
	 */
	public void drawElements(ArrayList<Element> torepaint) {
		toDraw.addAll(torepaint);		
	}


	/**
	 * Accessor for graphic api attribute
	 * @return the current api
	 */
	public GameDrawAPI getApi() {
		return api;
	}

	/**
	 * Mutator for api attribute
	 * @param api
	 */
	public void setApi(GameDrawAPI api) {
		this.api = api;
	}

	/**
	 * Accessors for game elements list
	 * @return the list corresponding at the name of the method 
	 */
	public ArrayList<Element> getPlayers() {
		return players;
	}

	public ArrayList<Element> getWalls() {
		return walls;
	}

	public ArrayList<Element> getRobots() {
		return robots;
	}

	public ArrayList<Element> getLureRobots() {
		return lures;
	}

	public ArrayList<Element> getBonuses() {
		return bonuses;
	}

	public ArrayList<Element> getCircles() {
		return circles ;
	}

	/**
	 * Runs a raycast query from the physic world to detect elements
	 * @param callback the callback to call after detection
	 * @param position1 position of the detector element
	 * @param position2 position of the detected element
	 */
	public void raycast(RayCastCallback callback, Vec2 position1, Vec2 position2) {
		jboxWorld.raycast(callback, position1, position2);
	}


	/**
	 * Runs an AABB raycast query from the physic world to detect elements
	 * @param callback the callback to call after detection
	 * @param aabb the aabb square in which objects will be detected
	 */
	public void queryAABB(QueryCallback callback, AABB aabb) {
		jboxWorld.queryAABB(callback, aabb);
	}

	/**
	 * Method that add a joint between objects in the physics world
	 * @param rjd
	 * @return
	 */
	public Joint addJoint(RevoluteJointDef rjd) {
		return jboxWorld.createJoint(rjd);
	}
	
	public void destroyJoint(Joint joint) {
		jboxWorld.destroyJoint(joint);
	}


	/**
	 * Called by the init() method to add start and end circles to the game
	 */
	public void addCircles(){
		RadialGradientPaint paint1 = new RadialGradientPaint(47, HEIGHT-103, 40, new float[]{.1f, 1f}, new Color[]{Color.BLUE, Color.WHITE});
		RadialGradientPaint paint2 = new RadialGradientPaint(47, HEIGHT-153, 40, new float[]{.1f, 1f}, new Color[]{Color.BLUE, Color.WHITE});
		RadialGradientPaint paint3 = new RadialGradientPaint(710, 47, 40, new float[]{.3f, 1f}, new Color[]{Color.GREEN, Color.WHITE});
		Circle c1 = new Circle(paint1, 40, 47, HEIGHT-103, ElementType.START_CIRCLE);
		if(mode == RobotGameMod.TWOPLAYER){
			Circle c2 = new Circle(paint3, 40, 47, HEIGHT-153, ElementType.START_CIRCLE);
			addStaticElement(c2);
		}
		Circle c3 = new Circle(paint2, 40, 710, 47, ElementType.END_CIRCLE);
		addStaticElement(c1);
		addStaticElement(c3);
	}

	/**
	 * Called in the init() method to add players
	 * Tests the mode constant to instanciate a second player
	 */
	public void addPlayers(){
		HumanRobot e1 = new HumanRobot(this,"Camcam",50, HEIGHT-100);
		e1.setController(KeyControllers.getGameController(this, e1, keysP1));
		addDynamicElement(e1);
		if(mode == RobotGameMod.TWOPLAYER){
			HumanRobot e2 = new HumanRobot(this,"Loulou",50, HEIGHT-150);
			e2.setController(KeyControllers.getGameController(this, e2, keysP2));
			addDynamicElement(e2);
		}
	}

	/**
	 * Generate a number of enemy robots
	 * Number depends on game level
	 * @param level the number of enemies to create
	 */
	public void addBots(int level){
		/*for (int i=0;i<level+1;i++){
			
			ComputerRobot r1;
			float x = MathUtils.randomFloat(200, 600);
			float y = MathUtils.randomFloat(200, 400);
			Vec2 pos = new Vec2(x,y);
			r1 = new ComputerRobot(x,y);
			MapGenerator.addVecPos(pos);
			addDynamicElement(r1);
		}*/
	}

	/**
	 * Returns the list to draw 
	 * @return toDraw the list to draw
	 */
	public ArrayList<Element> getToDraw() {
		return toDraw;
	}
}


