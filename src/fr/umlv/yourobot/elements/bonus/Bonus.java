package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.graphics.GameDrawAPI;

/**
 * @code {@link Bonus}
 * Defines methods for bonuses objects
 * @see {@link Element} 
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 */
abstract public class Bonus extends Element{
	protected RobotGame world;
	protected HumanRobot robot;
	protected int length = (int) Math.round(Math.random()*13+2);
	protected int timeleft = length;
	protected long start = 0;
	protected static int BONUS_SIZE = 30;
	protected BufferedImage img;
	protected CircleShape shapeElem;
	
	/**
	 * Main constructor 
	 * @param x
	 * @param y
	 */
	public Bonus(float x, float y) {
		super(x, y);
		fixtureDef = new FixtureDef();
		shapeElem = new CircleShape();
		fixtureDef.shape = shapeElem;
		fixtureDef.density = 0.f;
		fixtureDef.friction = 0.f;
		fixtureDef.restitution = 0.f;
		color = Color.BLACK;
	}
	
	/**
	 * Default method called by subclasses of Bonus to draw the element
	 * @param name
	 * @param g
	 * @param api
	 * @return the element drawn
	 * @throws IOException
	 */
	public Element draw(String name, Graphics2D g, GameDrawAPI api) throws IOException{
		if(img==null)
			img=ImageIO.read(new File("images/"+name));
		api.drawCircle(bodyElem.getPosition(), 0, Color.GREEN, img, g);
		return this;
	}

	/**
	 * Abstract method defining the behavior of bonus implemented in each sub-class
	 * @param world
	 * @param robot the robot running the bonus
	 */
	
	abstract public void run(final RobotGame world, final HumanRobot robot);
	abstract public boolean update();


	
}
