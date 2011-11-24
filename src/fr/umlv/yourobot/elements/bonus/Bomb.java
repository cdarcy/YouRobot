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

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
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

	public ArrayList<Element> run(final RobotWorld world, final HumanRobot robot) {
		final BombWaveCallback b = new BombWaveCallback(world, robot, getTypeBomb());
		new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i=0;i<120;i++)
				{

					float x = robot.getBody().getWorldCenter().x+(BONUS_SIZE/2);
					float y = robot.getBody().getWorldCenter().y+(BONUS_SIZE/2);
					RadialGradientPaint paint1 = new RadialGradientPaint(x, y, BONUS_SIZE+i, new float[]{0f, 1f}, new Color[]{Color.ORANGE, Color.ORANGE});
					RadialGradientPaint paint2 = new RadialGradientPaint(x, y, BONUS_SIZE+(i*2), new float[]{0f, 1f}, new Color[]{Color.YELLOW, Color.YELLOW});
					final Circle c1 = new Circle(world, paint1, BONUS_SIZE+i, x, y);
					new Circle(world, paint2, BONUS_SIZE+(i*2),x, y);
					Vec2 pos = new Vec2(x, y);
					Vec2 d = new Vec2(BONUS_SIZE+(i*2), BONUS_SIZE+(i*2));
					AABB aabb = new AABB(pos.sub(d), pos.add(d));
					world.getJBoxWorld().queryAABB(b, aabb);
					world.drawEffect(c1);
					
				}
				
			}
		}).run();
		return b.getRaycastedBorder();
	}

	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		return super.draw("bombicon.png", g, api);
	}
	
	public Element draw(String image, Graphics2D g, DrawAPI api) throws IOException {
		return super.draw(image, g, api);
	}
}
