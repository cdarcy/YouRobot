package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.IOException;

import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;

public class LureRobot extends Robot {

	public LureRobot(float x, float y) {
		super(x, y);
	}

	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		return super.draw(this, "robot_game.png", g, api);
	}
}
