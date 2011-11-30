package fr.umlv.yourobot.physics.raycasts;

import java.util.ArrayList;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.ElementType.ElementClass;

/**
 * @code {@link BombWaveCallback}
 * Bomb callback definition. Implements Jbox2D QueryCallback interface.
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 */
public class BombWaveCallback implements QueryCallback {
	private HumanRobot robot;
	private ArrayList<Element> raycasted;
	private ElementType maxEffectType;


	public BombWaveCallback(RobotGame world, HumanRobot robot, ElementType typeEffectMax){
		this.robot = robot;
		this.raycasted = new ArrayList<>();
		this.maxEffectType = typeEffectMax;
	}

	/**
	 * Callback when an element is detected in the ray of the bomb
	 * @param fixture the detected fixture
	 * @return boolean to specify if the raycast have to stop now
	 */
	@Override
	public boolean reportFixture(Fixture fixture) {
		Element p = (Element) fixture.getBody().getUserData();
		
		if(p == null || raycasted.contains(p) || p.classElem() != ElementClass.WALL)
			return true;
		
		Vec2 pos = new Vec2(robot.getPosition());
		Vec2 force = pos.sub(p.getPosition()).negate();
		int factor = 30000;
		switch (p.typeElem() ) {
		case WOODWALL:
			if(maxEffectType == ElementType.WOODWALL)
				factor = 100000;
			break;
		case ICEWALL:
			if(maxEffectType == ElementType.ICEWALL)
				factor = 100000;
			break;
		case STONEWALL:
			if(maxEffectType == ElementType.STONEWALL)
				factor = 100000;		
			break;
		case BARWALL:
			return true;
		}

		p.getBody().setType(BodyType.DYNAMIC);	
		float distance = MathUtils.distance(robot.getPosition(), p.getPosition());
		p.getBody().applyForce(new Vec2((force.x*distance)*factor,(force.y*distance)*factor), p.getBody().getWorldCenter());
		p.getBody().setAwake(true);
		raycasted.add(p);
		return true;

	}
	
	public ArrayList<Element> getRaycasted(){
		return raycasted;
	}
}
