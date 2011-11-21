package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import javax.imageio.ImageIO;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.physics.raycasts.PlayerCallback;
import fr.umlv.yourobot.physics.raycasts.SnapCallback;
import fr.umlv.yourobot.util.ElementType;

public class Snap  extends Bonus  {
	public int length;
	public Snap(RobotWorld world, float x, float y) {
		super(x, y);
		length = (int) Math.round(Math.random()*10);
		type = ElementType.SNAP;
	}

	public void drawIcon(int x, int y, Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/snap.png"));	
		g.drawImage(img, null, (int)x, (int)y);
	}

	@Override
	public ArrayList<Element> run(final RobotWorld world, final HumanRobot robot){
		System.out.println("Raycasting area");
		SnapCallback callback = new SnapCallback(world, robot);
		Vec2 center = robot.getBody().getWorldCenter();
		Vec2 d = new Vec2(100,100);
		AABB aabb = new AABB(center.sub(d), center.add(d));
		long start = System.nanoTime();
		while((System.nanoTime()-start)/10000<(length*1000)){
			
			world.getJBoxWorld().queryAABB(callback, aabb);
			System.out.println((System.nanoTime()-start)/10000);
		}
		callback.clearJoints();
		return null;
	}

	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		super.draw("snap.png", g, api);

		Graphics gr = g.create();
		gr.setColor(Color.WHITE);
		gr.setFont(new Font("Sans", Font.BOLD, 15));
		gr.drawString(length+"s", (int) bodyElem.getPosition().x, (int)bodyElem.getPosition().y);
		return this;
	}

}
