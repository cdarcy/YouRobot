package fr.umlv.yourobot.elements.bonus;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robots.HumanRobot;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class Lure extends Bonus {

	public Lure(RobotWorld world, float x, float y) {
		super(x, y);
		bodyElem.setUserData(new ElementData(100, ElementType.LURE, this));
	}

	@Override
	public Element draw(Graphics2D g) throws IOException {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void drawIcon(int x, int y, Graphics2D g) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Element> run(RobotWorld world, HumanRobot robot) {
		// TODO Auto-generated method stub
		return null;
	}



}
