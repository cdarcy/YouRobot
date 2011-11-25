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
import fr.umlv.yourobot.elements.robots.Robot;
import fr.umlv.yourobot.graphics.DrawAPI;
import fr.umlv.yourobot.physics.raycasts.LureCallback;
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
		new Thread(new Runnable() {

			@Override
			public void run() {
				long start = System.nanoTime();
				/*try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				System.out.println("test");

				while(((System.nanoTime()-start)/1000000)<(length*1000)){
					for (Element enemy : world.getListByType(ElementType.COMPUTER_ROBOT)){
						c.raycast(enemy);
					}
				}
				/*try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
			}
		}).start();
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
