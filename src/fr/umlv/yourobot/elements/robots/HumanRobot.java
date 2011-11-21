package fr.umlv.yourobot.elements.robots;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.elements.DrawAPI;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.physics.raycasts.PlayerCallback;
import fr.umlv.yourobot.util.ElementType;
import fr.umlv.yourobot.util.KeyController;
import fr.umlv.zen.KeyboardEvent;

public class HumanRobot extends Robot {
	private String name;
	private float life;
	private Bonus currentBonus = null;
	protected KeyController controller;
	
	public HumanRobot(RobotWorld world, String name, String[] k, float x, float y) {
		super(x, y);
		this.name = name;
		controller = new KeyController(world, this, k);
		type = ElementType.PLAYER_ROBOT;
	}
	

	public void setBonus(Bonus b) {
		if(currentBonus == b)
			return;
		currentBonus = b;
	}
	
	public ArrayList<Element> runBonus(RobotWorld world) {
		if(currentBonus == null)
			return null;
		return currentBonus.run(world, this);
	}


	public void control(Graphics2D g, KeyboardEvent event){
		controller.control(event);
	}

	
	public void controlMenu(Graphics2D g, KeyboardEvent event, RobotWorld world) throws IOException{
		controller.controlMenu(event, g, world);			
	}
	
	
	public float getLife() {
		return life;
	}

	public Bonus getBonus() {
		return currentBonus;
	}
	

	public void clearBonus() {
		currentBonus = null;
	}

	@Override
	public Element draw(Graphics2D g, DrawAPI api) throws IOException {
		super.draw(this, "robot_game.png", g, api);
		return this;
	}
}
