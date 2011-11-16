package fr.umlv.yourobot.physics.collisions;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.HumanRobot;
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
		Element elemA = ((ElementData) contact.getFixtureA().getBody().getUserData()).getObj();
		Element elemB = ((ElementData) contact.getFixtureB().getBody().getUserData()).getObj();

		ElementData dataA = (ElementData) elemA.getBody().getUserData();
		ElementData dataB = (ElementData) elemB.getBody().getUserData();


		// A player is bodyA
		if(dataA.type() == ElementType.PLAYER_ROBOT){
			
			if(dataB.getElemClass() == ElementClass.BONUS){
				((HumanRobot) elemA).addBonus((Bonus) elemB);
				world.removeBonus(elemB.getPosition());
			}


		}
		// A computer robot is bodyA
		else if(dataA.type() == ElementType.COMPUTER_ROBOT){
			if(dataB.getElemClass() == ElementClass.BONUS){
				elemB.getBody().setActive(false);
			}
			
			//elemB.getBody().getUserData().setLife();
		}

		if(dataB.getElemClass() == ElementClass.WALL){
			world.drawElement(elemB);
		}
	}

	@Override
	public void endContact(Contact contact) {
		Element elemB = ((ElementData) contact.getFixtureB().getBody().getUserData()).getObj();

		elemB.getBody().setActive(true);
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}
}
