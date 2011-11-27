package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.graphics.GameDrawAPI;

abstract public class Bonus extends Element{
	protected static  int BONUS_SIZE = 30;
	protected BufferedImage img;
	protected CircleShape shapeElem;

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
	
	public Element draw(String name, Graphics2D g, GameDrawAPI api) throws IOException{
		if(img==null)
			img=ImageIO.read(new File("images/"+name));
		api.drawCircle(bodyElem.getPosition(), 0, Color.GREEN, img, g);
		return this;
	}
	abstract public void drawIcon(int x, int y, Graphics2D g) throws IOException;
	abstract public ArrayList<Element> run(final RobotGame world, final HumanRobot robot);
}
