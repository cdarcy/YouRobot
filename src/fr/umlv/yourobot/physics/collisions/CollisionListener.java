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

		ElementType typeElementA = ((ElementData) elemA.getBody().getUserData()).type();
		ElementType typeElementB = ((ElementData) elemB.getBody().getUserData()).type();


		// A player is bodyA
		if(typeElementA == ElementType.PLAYER_ROBOT){
			
			if(typeElementB == ElementType.BOMB){
				((HumanRobot) elemA).addBonus((Bonus) world.getElement(elemB.getPosition()));
				world.removeBonus(elemB.getPosition());
			}


		}
		// A computer robot is bodyA
		else if(typeElementA == ElementType.COMPUTER_ROBOT){
			if(typeElementB == ElementType.BOMB){
				elemB.getBody().setActive(false);
			}
			// TODO : set life diminution depending on linear velocity
			
			//elemB.getBody().getUserData().setLife();
		}

		if(typeElementB == ElementType.WALL){
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
