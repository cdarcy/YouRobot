package fr.umlv.yourobot.elements.bonus;

import java.awt.Graphics2D;
import java.io.IOException;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.physics.raycasts.BombWaveCallback;
import fr.umlv.yourobot.util.ElementType;

/**
 * @code {@link Bomb}
 * Sub bonus simulating magnetic bomb
 * @see {@link Element} 
 * @author Darcy Camille <cdarcy@etudiant.univ-mlv.fr>
 * @author Baudrand Sebastien <sbaudran@etudiant.univ-mlv.fr>
 *
 */
public abstract class Bomb extends Bonus {
	// type of wall on which the bomb works the most
	protected ElementType typeEffectMax;
	private BombWaveCallback b;
	public Bomb(float x, float y) {
		super(x, y);
		length = 3;
	}

	public ElementType getTypeBomb(){
		return typeEffectMax;
	}
	
	/**
	 * Runs bomb effect
	 * Uses a callback to detect walls to impulse 
	 */
	@Override
	public void run(final RobotGame world, final HumanRobot robot) {
		b = new BombWaveCallback(world, robot, getTypeBomb());
		float size = 200;
		float x = robot.getBody().getPosition().x;
		float y = robot.getBody().getPosition().y;
		
		/*RadialGradientPaint paint1 = new RadialGradientPaint(x-size/2, y-size/2, 200, new float[]{0f, 1f}, new Color[]{Color.ORANGE, Color.YELLOW});
		Circle c1 = new Circle(paint1, 200, x-size/2, y-size/2, ElementType.EFFECT);
		world.addElement(c1, BodyType.DYNAMIC, true);*/
		
		Vec2 lower = new Vec2(x - size/2, y - size/2);
		Vec2 upper = new Vec2(x + size/2, y + size/2);

		
		world.queryAABB(b, new AABB(lower, upper));
		//world.removeElement(c1);
		start = System.nanoTime();
	}

	public Element draw(String image, Graphics2D g, GameDrawAPI api) throws IOException {
		return super.draw(image, g, api);
	}
	
	@Override
	public boolean update(){
		if(start + ((long)length*1000000000) > System.nanoTime()){
			System.out.println("update bomb");
			return true;
		}
		for(Element e : b.getRaycasted())
			e.getBody().setType(BodyType.STATIC);
		return false;
	}
}
