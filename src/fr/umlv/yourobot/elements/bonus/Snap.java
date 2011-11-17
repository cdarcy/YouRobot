package fr.umlv.yourobot.elements.bonus;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.physics.raycasts.SnapCallback;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class Snap  extends Bonus  {

	public Snap(RobotWorld world, float x, float y) {
		super(world, x, y);
		bodyElem.setUserData(new ElementData(100, ElementType.SNAP, this));
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

	@Override
	public void run(final HumanRobot robot){
		System.out.println("Raycasting area");
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean stop = false;
				AABB aabb = new AABB();
				Vec2 center = robot.getBody().getWorldCenter();
				aabb.lowerBound.x = center.x-100;
				aabb.lowerBound.y = center.y-100;
				aabb.upperBound.x = center.x+100;
				aabb.upperBound.y = center.y+100;
				world.getJBoxWorld().queryAABB(new SnapCallback(world, robot), aabb);
				
				//while(!stop){
					//world.getJBoxWorld().queryAABB(new SnapCallback(world, robot), aabb);
					
				//}
			}
		}).start();
	}

}
