package fr.umlv.yourobot.elements.robots;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.util.ElementType;

public class ComputerRobot extends Robot {
	private RobotWorld world;
	public ComputerRobot(RobotWorld world, float x, float y) {
		super(x, y);
		type = ElementType.COMPUTER_ROBOT;
		this.world = world;
	}
	public Element draw(Graphics2D g, DrawAPI api) throws IOException{
		super.draw(this, "robot_IA.png", g, api);
		return this;
	}
	
}
