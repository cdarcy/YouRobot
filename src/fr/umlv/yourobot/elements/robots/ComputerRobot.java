package fr.umlv.yourobot.elements.robots;

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
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class ComputerRobot extends Robot {
	private RobotWorld world;
	public ComputerRobot(RobotWorld world, float x, float y) {
		super(x, y);
		type = ElementType.COMPUTER_ROBOT;
		this.world = world;
	}
	public Element draw(Graphics2D g) throws IOException{
		Vec2 pos = this.bodyElem.getPosition();
		if(img == null)
			img = ImageIO.read(new File("images/robot_IA.png"));	
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
}
