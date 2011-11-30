package fr.umlv.yourobot.elements.robots;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Lure;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.util.ElementType;

public class LureRobot extends Robot {
	private Lure lure;
	
	public LureRobot(float x, float y, Lure lure) {
		super(x, y);
		this.lure = lure;
		type = ElementType.LURE_ROBOT;
	}

	@Override
	public Element draw(Graphics2D g, GameDrawAPI api) throws IOException {
		g.setColor(Color.WHITE);
		g.setFont(new Font ("Sans", Font.BOLD, 15));
		g.drawString(lure.getTimeleft()/1000+ "s", (int) getPosition().x, (int)getPosition().y);
		return super.draw(this, "robot_game.png", g, api);
	}

	@Override
	public void run(RobotGame world) {
		
	}
}
