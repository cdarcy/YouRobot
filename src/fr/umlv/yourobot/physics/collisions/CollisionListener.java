package fr.umlv.yourobot.physics.collisions;

import javax.lang.model.element.TypeElement;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
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
				elem.setDetect(false);
				HumanRobot h = (HumanRobot) elemB;
				double ecart = contact.getFixtureA().getBody().m_linearVelocity.normalize() * 0.003;
				h.setLife((ecart+0.3));
			}

			break;
		case PLAYER_ROBOT:
			if(elemB.typeElem() == ElementType.COMPUTER_ROBOT){
				final Vec2 force = elemB.getPosition().sub(elemA.getPosition());	
				Robot elem = (Robot)elemB;
				elem.rotate(MathUtils.randomFloat(15, 180));
				elem.move(new Vec2(force.x * 10000, force.y * 10000));
			}
			else if(elemB.typeElem() == ElementType.END_CIRCLE){
				world.setGameFinished();
				System.out.println("finished");
			}
			break;


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
		Element elemB = (Element) contact.getFixtureB().getBody().getUserData();

		if(elemB == null)
			return;
		if(elemB.classElem() == ElementClass.BONUS)
			contact.setEnabled(false);
		
	}
}
