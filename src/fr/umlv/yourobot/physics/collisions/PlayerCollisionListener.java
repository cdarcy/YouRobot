package fr.umlv.yourobot.physics.collisions;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class PlayerCollisionListener implements ContactListener {
	private final RobotWorld world;
	public PlayerCollisionListener(RobotWorld world){
		this.world = world;
	}
	@Override
	public void beginContact(Contact contact) {
		Element elem = ((ElementData) contact.getFixtureA().getBody().getUserData()).getObj();
		Body bodyB = contact.getFixtureB().getBody();
	
        Vec2 posB = bodyB.getPosition();
    	
    	ElementType typeA = ((ElementData) elem.getBody().getUserData()).type();
        ElementType typeB = ((ElementData) bodyB.getUserData()).type();
        
        if(typeA != ElementType.PLAYER_ROBOT)
    	   return;
        
		if(typeB == ElementType.BOMB){
			((HumanRobot) elem).addBonus((Bonus) world.getElement(posB));
			world.removeElement(posB);
		}
		
		if(typeB == ElementType.WALL){
			world.drawElement(world.getElement(posB));
			
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}
}
