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
import fr.umlv.yourobot.util.ElementClass;
import fr.umlv.yourobot.util.ElementType;

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
		
		if(elemA.typeElem() != ElementType.PLAYER_ROBOT){
			((Robot) elemA).rotate(MathUtils.randomFloat(0, 360));
			return;
		}

		// A player is bodyA
		if(elemA.typeElem()  == ElementType.PLAYER_ROBOT){
			if(elemB.classElem() == ElementClass.BONUS){
				((HumanRobot) elemA).setBonus((Bonus) elemB);
				world.removeBonus(elemB.getPosition());
			}
		}
		// A computer robot is bodyA
		else if(elemA.typeElem() == ElementType.COMPUTER_ROBOT){
			
		}
	
		if(elemB.classElem() == ElementClass.WALL){
			world.drawElement(elemB);
		}
		
		if(elemB.classElem() == ElementClass.BLOCK){
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
