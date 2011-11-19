package fr.umlv.yourobot.physics.collisions;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.MathUtils;
import org.jbox2d.dynamics.contacts.Contact;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementClass;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class CollisionListener implements ContactListener {
	private final RobotWorld world;
	public CollisionListener(RobotWorld world){
		this.world = world;
	}
	@Override
	public void beginContact(Contact contact) {
	
		Element elemA = (Element) contact.getFixtureA().getUserData();
		Element elemB = (Element) contact.getFixtureB().getUserData();
		if(elemA == null || elemB == null)
			return;
		ElementData dataA = (ElementData) elemA.getBody().getUserData();
		ElementData dataB = (ElementData) elemB.getBody().getUserData();
		
		if(elemA.typeElem() != ElementType.PLAYER_ROBOT){
			((Robot) elemA).rotate(MathUtils.randomFloat(0, 360));
			return;
		}

		// A player is bodyA
		if(dataA.type() == ElementType.PLAYER_ROBOT){
			if(dataB.getElemClass() == ElementClass.BONUS){
				((HumanRobot) elemA).setBonus((Bonus) elemB);
				world.removeBonus(elemB.getPosition());
			}
		}
		// A computer robot is bodyA
		else if(dataA.type() == ElementType.COMPUTER_ROBOT){
			
		}
	
		if(dataB.getElemClass() == ElementClass.WALL){
			world.drawElement(elemB);
		}
		
		if(dataB.getElemClass() == ElementClass.BLOCK){
			world.drawElement(elemB);
		}
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}
}
