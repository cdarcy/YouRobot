package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
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
	public void run(HumanRobot robot) {
		float x = robot.getBody().getWorldCenter().x+(BONUS_SIZE/2);
		float y = robot.getBody().getWorldCenter().y+(BONUS_SIZE/2);
		RadialGradientPaint paint = new RadialGradientPaint(x, y, 30, new float[]{0f, 1f}, new Color[]{color, Color.RED});
		final Circle c = new Circle(world, paint, x, y);
		new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i=0;i<100;i++)
				{
					world.drawEffect(c);
				}
			}
		}).run();
	}
}
