package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Circle;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class Bomb extends Bonus {

	public Bomb(RobotWorld world, float x, float y) {
		super(world, x, y);
		bodyElem.setUserData(new ElementData(100, ElementType.BOMB, this));
	}

	public void draw(Graphics2D g) throws IOException{
		Vec2 pos = this.bodyElem.getPosition();
		if(img == null)
			img = ImageIO.read(new File("images/bombicon.png"));	
		g.drawImage(img, null, (int)pos.x, (int)pos.y);
	}

	public void drawIcon(int x, int y, Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/bombicon.png"));	
		g.drawImage(img, null, (int)x, (int)y);
	}

	@Override
	public void run(final HumanRobot robot) {
		new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i=0;i<100;i++)
				{

					float x = robot.getBody().getWorldCenter().x+(BONUS_SIZE/2);
					float y = robot.getBody().getWorldCenter().y+(BONUS_SIZE/2);
					RadialGradientPaint paint1 = new RadialGradientPaint(x, y, BONUS_SIZE+i, new float[]{0f, 1f}, new Color[]{Color.ORANGE, Color.ORANGE});
					RadialGradientPaint paint2 = new RadialGradientPaint(x, y, BONUS_SIZE+(i*2), new float[]{0f, 1f}, new Color[]{Color.YELLOW, Color.YELLOW});
					final Circle c1 = new Circle(world, paint1, BONUS_SIZE+i, x, y);
					final Circle c2 = new Circle(world, paint2, BONUS_SIZE+(i*2),x, y);
					world.drawEffect(c1);
					world.drawEffect(c2);
/*					ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
					lock.readLock().lock();
					world.getJBoxWorld().raycast(new BombWaveCallback(world, robot), c2.getPosition(), wall.getBody().getWorldCenter());
					lock.readLock().unlock();
					lock.readLock().lock();
					world.removeBonus(c1.getPosition());
					world.removeBonus(c2.getPosition());
					lock.readLock().unlock();*/
				}
				
			}
		}).run();
	}
}
