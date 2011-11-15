package fr.umlv.yourobot.elements.walls;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

abstract public class Wall extends Element{
	public static  int WALL_SIZE = 56;
	
	protected FixtureDef fixtureDef;
	
	
	public Wall(RobotWorld world, float x, float y) {
		super(world, x, y );
		shapeElem.setAsBox(WALL_SIZE/2, WALL_SIZE/2, bodyElem.getLocalCenter(), bodyElem.getAngle());
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shapeElem;
		fixtureDef.density = 1000000.f;
		fixtureDef.friction = 1.f;
		fixtureDef.restitution = 1.f;
		bodyElem.createFixture(fixtureDef);
		bodyElem.setType(BodyType.STATIC);
		bodyElem.setUserData(new ElementData(100, ElementType.WALL, this));
	}

}
