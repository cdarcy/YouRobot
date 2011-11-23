package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.TexturedDrawAPI;
import fr.umlv.yourobot.elements.robots.ComputerRobot;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.LureRobot;
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.physics.raycasts.AICallback;
import fr.umlv.yourobot.physics.raycasts.LureCallback;
import fr.umlv.yourobot.physics.raycasts.PlayerCallback;
import fr.umlv.yourobot.util.ElementType;

public class Lure extends Bonus {
	private int length;
	public Lure(float x, float y) {
		super(x, y);
		type = ElementType.LURE;
		this.length = (int) Math.round(Math.random()*13+2);
	}

	@Override
	public void drawIcon(int x, int y, Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/lure.png"));	
		g.drawImage(img, null, (int)x, (int)y);
	}

	@Override
	public ArrayList<Element> run(final RobotWorld world, final HumanRobot robot) {
		System.out.println("use bonus");
		LureRobot lureRobot = new LureRobot(robot.getPosition().x+10, robot.getPosition().y);
		world.addLureRobot(lureRobot);
		final LureCallback c = new LureCallback(world, robot);
		long start = System.nanoTime();
		while ((System.nanoTime()-start)/1000000<(length*1000)){
			for (int i=0 ; i<world.getRobots().size()-1 ; i++){
				c.raycast(world.getRobots().get(i));
				//world.addBonus(new Lure(robot.getPosition().x+10, robot.getPosition().y));
			}
			length--;
		}
		//world.delLureRobot(world.getRobots().size()-1);
		return null;
	}

	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		super.draw("lure.png", g, api);
		Graphics gr = g.create();
		gr.setColor(Color.WHITE);
		gr.setFont(new Font ("Sans", Font.BOLD, 15));
		gr.drawString(length + "s", (int) bodyElem.getPosition().x, (int)bodyElem.getPosition().y);
		return this;
	}
}
