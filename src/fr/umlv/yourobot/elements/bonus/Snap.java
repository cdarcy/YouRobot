package fr.umlv.yourobot.elements.bonus;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.physics.raycasts.SnapCallback;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class Snap  extends Bonus  {
	private HumanRobot robot;
	public Snap(RobotWorld world, float x, float y) {
		super(world, x, y);
		bodyElem.setUserData(new ElementData(100, ElementType.BOMB, this));
	}
	
	public void draw(Graphics2D g) throws IOException{
		Vec2 pos = this.bodyElem.getPosition();
		if(img == null)
			img = ImageIO.read(new File("images/snap.png"));	
		g.drawImage(img, null, (int)pos.x, (int)pos.y);
	}
	
	public void drawIcon(int x, int y, Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/snap.png"));	
		g.drawImage(img, null, (int)x, (int)y);
	}

	public void run(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean stop = false;
				/*AABB aabb = new AABB();
				aabb.lowerBound = robot.getBody().getWorldCenter().add(new Vec2());*/
				while(!stop){
					//world.getJBoxWorld().queryAABB(new SnapCallback(world, robot), aabb);
				}
			}
		}).start();
	}
}
