package fr.umlv.yourobot.elements.robots;

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
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.physics.raycasts.PlayerCallback;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;
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
	
	public Element draw(Graphics2D g) throws IOException{
		Vec2 pos = this.bodyElem.getPosition();
		if(img == null)
			img = ImageIO.read(new File("images/robot_game.png"));	
		  	Graphics2D gImg = (Graphics2D) img.getGraphics();
		    AffineTransform at = new AffineTransform();
		    // rotate 45 degrees around image center
		    at.rotate(direction * Math.PI / 180.0, img.getWidth() / 2.0, img
		        .getHeight() / 2.0);
		    // instantiate and apply affine transformation filter
		    BufferedImageOp bio;
		    bio = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		    BufferedImage destinationBI = bio.filter(img, null);
		  //  System.out.println(bodyElem.getLinearVelocity().normalize());
		g.drawImage(destinationBI, null, (int)pos.x, (int)pos.y);
		return this;
	}

	public void setBonus(Bonus b) {
		if(b == null || currentBonus == b)
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

	public float getLife() {
		return life;
	}

	public Bonus getBonus() {
		return currentBonus;
	}
	

	public void clearBonus() {
		currentBonus = null;
	}
}
