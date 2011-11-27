package fr.umlv.yourobot.elements.bonus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.elements.robots.LureRobot;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.physics.raycasts.LureCallback;
import fr.umlv.yourobot.util.ElementType;

public class Lure extends Bonus {
	private HumanRobot robot;
	private final static int length = (int) Math.round(Math.random()*13+2);
	private static int timeleft = length;
	public Lure(float x, float y) {
		super(x, y);
		type = ElementType.LURE;
	}

	@Override
	public void drawIcon(int x, int y, Graphics2D g) throws IOException {
		if(img == null)
			img = ImageIO.read(new File("images/lure.png"));	
		g.drawImage(img, null, (int)x, (int)y);
	}

	@Override
	public ArrayList<Element> run(final RobotWorld world, final HumanRobot robot) {
		final LureRobot lureRobot = new LureRobot(robot.getPosition().x+10, robot.getPosition().y, this);
		world.addStaticElement(lureRobot);
		final LureCallback c = new LureCallback(world, lureRobot);
		this.robot=robot;
		new Thread(new Runnable() {

			@Override
			public void run() {
				long start = System.nanoTime();
				ArrayList<Element> list = world.getListByType(ElementType.COMPUTER_ROBOT);
				while(((System.nanoTime()-start)/1000000)<(length*1000)){
					timeleft = (int) ((length*1000)-(System.nanoTime()-start)/1000000);
					for (Element enemy : list){
						c.raycast(enemy);
					}
				}
				world.removeElement(lureRobot);
			}
		}).start();

		return null;
	}

	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		super.draw("lure.png", g, api);
		Graphics gr = g.create();
		gr.setColor(Color.WHITE);
		gr.setFont(new Font ("Sans", Font.BOLD, 15));
		gr.drawString(length + "s", (int) bodyElem.getPosition().x, (int)bodyElem.getPosition().y);
		return this;
	}

	public int getTimeleft() {
		return timeleft;
	}
}
