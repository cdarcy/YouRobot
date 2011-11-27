package fr.umlv.yourobot.elements.robots;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import org.jbox2d.common.MathUtils;

import fr.umlv.yourobot.RobotGame;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Lure;
import fr.umlv.yourobot.graphics.GameDrawAPI;
import fr.umlv.yourobot.physics.raycasts.LureCallback;
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

		Graphics gr = g.create();
		gr.setColor(Color.WHITE);
		gr.setFont(new Font ("Sans", Font.BOLD, 15));
		gr.drawString(lure.getTimeleft()/1000+ "s", (int) getPosition().x, (int)getPosition().y);
		return super.draw(this, "robot_game.png", g, api);
	}
	
	
}
