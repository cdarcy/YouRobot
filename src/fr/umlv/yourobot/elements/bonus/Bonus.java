package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

abstract public class Bonus extends Element{
	protected static  int BONUS_SIZE = 20;
	protected final RobotWorld world;
	protected BufferedImage img;
	protected FixtureDef fixtureDef;
	
	public enum BonusType{
		SNAP,
		BOMB,
		LURE
	}

	public Bonus(RobotWorld world, float x, float y) {
		super(world, x, y);
		this.world = world;
		shapeElem.setAsBox(BONUS_SIZE/2, BONUS_SIZE/2, bodyElem.getLocalCenter(), bodyElem.getAngle());
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shapeElem;
		fixtureDef.density = 0;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixture = bodyElem.createFixture(fixtureDef);
		bodyElem.setType(BodyType.DYNAMIC);
		bodyElem.setUserData(new ElementData(100, ElementType.BONUS, this));
		color = Color.BLACK;
	}
	
	abstract public void draw(Graphics2D g) throws IOException;
	abstract public void drawIcon(int x, int y, Graphics2D g) throws IOException;
}
