package fr.umlv.yourobot.elements.robots;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class ComputerRobot extends Robot {

	public ComputerRobot(String pName, RobotWorld world, float x, float y) {
		super(pName, world, x, y);
		bodyElem.setUserData(new ElementData(pName, 100, ElementType.COMPUTER_ROBOT, this));
	}
	@Override
	public void draw(Graphics2D g) throws IOException{
		Vec2 pos = this.bodyElem.getPosition();
		if(img == null)
			img = ImageIO.read(new File("images/robot_ia.png"));	

		g.drawImage(img, null, (int)pos.x, (int)pos.y);
	}
}
