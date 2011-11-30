package fr.umlv.yourobot.physics.collisions;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.ElementType.ElementClass;


/**
 * @code {@link CollisionListener}
 * CollisionListener element definition. Implements Jbox2D ContactListener interface.
 * Manages start and end of contacts between each type of element
 * @see {@link ContactListener} 
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 *
 */
public class CollisionListener implements ContactListener {
	private final RobotGame world;
	
	public CollisionListener(RobotGame world){
		this.world = world;
	}
	
	@Override
	public void beginContact(Contact contact) {

		Element elemA = (Element) contact.getFixtureA().getBody().getUserData();
		Element elemB = (Element) contact.getFixtureB().getBody().getUserData();

		if(elemA == null || elemB == null)
			return;

		switch (elemA.typeElem()) {
		case COMPUTER_ROBOT:

			if(elemB.typeElem() == ElementType.PLAYER_ROBOT){
				final Vec2 force = elemA.getPosition().sub(elemB.getPosition());	
				ComputerRobot elem = (ComputerRobot)elemA;
				elem.rotate(MathUtils.randomFloat(15, 180));
				elem.move(new Vec2(force.x * 10000, force.y * 10000));
				HumanRobot h = (HumanRobot) elemB;
				double ecart = contact.getFixtureA().getBody().m_linearVelocity.normalize() * 0.003;
				h.setLife(ecart+0.3);
				GameDrawAPI.drawInterface();
			}
			else{
				Robot elem = (Robot)elemA;
				elem.rotate(MathUtils.randomFloat(-180, 180));
				elem.impulse(100000);
			}

			break;
		case PLAYER_ROBOT:
			if(elemB.typeElem() == ElementType.END_CIRCLE){
				world.setGameFinished();
			}
			break;

		case BORDERWALL:
			GameDrawAPI.drawInterface();
		default:
			break;
		}
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Element elemA = (Element) contact.getFixtureA().getBody().getUserData();
		Element elemB = (Element) contact.getFixtureB().getBody().getUserData();

		if(elemB == null && elemA == null)
			return;
		if(elemB.classElem() == ElementClass.BONUS || elemB.typeElem() == ElementType.START_CIRCLE
				|| elemA.typeElem() == ElementType.START_CIRCLE)
			contact.setEnabled(false);
		
	}
}
