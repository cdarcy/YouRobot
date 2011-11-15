package fr.umlv.yourobot.elements.robots;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.Marshaller.Listener;

import org.jbox2d.common.Vec2;

import fr.umlv.yourobot.RobotWorld;
import fr.umlv.yourobot.physics.collisions.AICollisionListener;
import fr.umlv.yourobot.physics.collisions.PlayerCollisionListener;
import fr.umlv.yourobot.util.ElementData;
import fr.umlv.yourobot.util.KeyController;
import fr.umlv.yourobot.util.ElementData.ElementType;

public class HumanRobot extends Robot {
	
	public HumanRobot(String pName, RobotWorld world, String[] k, float x, float y) {
		super(pName, world,  x, y);
		controller = new KeyController(this, k);
		bodyElem.setUserData(new ElementData(100, ElementType.PLAYER_ROBOT, this));
		world.getJBoxWorld().setContactListener(new PlayerCollisionListener(world));
	}
	
	public void draw(Graphics2D g) throws IOException{
		Vec2 pos = this.bodyElem.getPosition();
		g.setColor(Color.WHITE);
		g.fillOval((int)pos.x, (int)pos.y, ROBOT_SIZE, ROBOT_SIZE);
		if(img == null)
			img = ImageIO.read(new File("images/robot.png"));	

		g.drawImage(img, null, (int)pos.x, (int)pos.y);
	}

}
