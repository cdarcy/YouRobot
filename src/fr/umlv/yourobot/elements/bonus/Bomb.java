package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.physics.raycasts.BombWaveCallback;
import fr.umlv.yourobot.util.ElementType;

public abstract class Bomb extends Bonus {
	protected ElementType typeEffectMax;

	public Bomb(float x, float y) {
		super(x, y);
	}

	public abstract ElementType getTypeBomb();
	public void drawIcon(int x, int y, Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/bombicon.png"));	
		g.drawImage(img, null, (int)x, (int)y);
	}

	public ArrayList<Element> run(final RobotGame world, final HumanRobot robot) {
		final BombWaveCallback b = new BombWaveCallback(world, robot, getTypeBomb());
		new Thread(new Runnable(){

			@Override
			public void run() {
				Circle c1 = null;
				float x = 0;
				float y = 0;
				float size = 200;

				x = robot.getBody().getWorldCenter().x;
				y = robot.getBody().getWorldCenter().y;
				
				Vec2 lower = new Vec2(x - size/2, y - size/2);
				Vec2 upper = new Vec2(x + size/2, y + size/2);

				world.queryAABB(b, new AABB(lower, upper));
			
				RadialGradientPaint paint1 = new RadialGradientPaint(x, y, BONUS_SIZE*7, new float[]{0f, 1f}, new Color[]{Color.ORANGE, Color.YELLOW});

				c1 = new Circle(paint1, BONUS_SIZE*7, x, y, ElementType.EFFECT);
				world.addElement(c1, BodyType.DYNAMIC, true);
				world.removeEffects();


			}
		}).run();
		return b.getRaycastedBorder();
	}

	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		return super.draw("bombicon.png", g, api);
	}

	public Element draw(String image, Graphics2D g, GameDrawAPI api) throws IOException {
		return super.draw(image, g, api);
	}
}
