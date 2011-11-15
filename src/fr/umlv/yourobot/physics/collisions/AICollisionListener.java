package fr.umlv.yourobot.physics.collisions;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

/*
 * AICollisionListener manages life to remove from the player contacted
 */
public class AICollisionListener implements ContactListener{
	private final RobotWorld world;
	public AICollisionListener(RobotWorld world){
		this.world = world;
	}
	@Override
	public void beginContact(Contact contact) {

		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
		Vec2 posB = bodyB.getPosition();
		
		Element elem = ((ElementData) contact.getFixtureA().getBody().getUserData()).getObj();
		
    	ElementType typeA = ((ElementData) bodyA.getUserData()).type();
        ElementType typeB = ((ElementData) bodyB.getUserData()).type();
        
        if(typeB == ElementType.BOMB){
			if(bodyA.shouldCollide(bodyB)){
				Fixture f = elem.getBody().getFixtureList().getNext();
				elem.getBody().destroyFixture(f);
				System.out.println(elem.getBody().m_fixtureCount);
			}
			
		}
        
		if(typeB == ElementType.WALL){
			world.drawElement(world.getElement(posB));
			return;
		}

		

		Robot r = (Robot) world.getPlayer(bodyB.getPosition());
		
		// Removing life (TODO : damages depending on robot speed)
		((ElementData) r.getBody().getUserData()).setLife(-10);

	}
	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub

	}
	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub

	}
}
