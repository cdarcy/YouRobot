package fr.umlv.yourobot.elements.bonus;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.util.ElementType;

public class Lure extends Bonus {

	public Lure(float x, float y) {
		super(x, y);
		type = ElementType.LURE;
	}

	@Override
	public void drawIcon(int x, int y, Graphics2D g) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Element> run(RobotWorld world, HumanRobot robot) {
		world.addBonus(new Lure(robot.getPosition().x+10, robot.getPosition().y));
		return null;
	}

	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		super.draw("lure.png", g, api);
		return this;
	}



}
